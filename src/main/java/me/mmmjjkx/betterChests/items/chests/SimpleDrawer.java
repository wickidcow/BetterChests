package me.mmmjjkx.betterChests.items.chests;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import it.unimi.dsi.fastutil.Pair;
import me.mmmjjkx.betterChests.BCGroups;
import me.mmmjjkx.betterChests.BetterChests;
import me.mmmjjkx.betterChests.storage.DrawerData;
import me.mmmjjkx.betterChests.storage.DrawerDisplayManager;
import me.mmmjjkx.betterChests.storage.DrawerStorage;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A single-item bulk drawer.
 *
 * <p>This implementation is restart-safe, uses long arithmetic throughout,
 * stores portable contents in PDC, and never drops an invalid multi-million
 * item stack when the block is broken.</p>
 */
public class SimpleDrawer extends SlimefunItem implements NotHopperable {

    private static final Map<String, Object> LOCKS = new ConcurrentHashMap<>();

    private final long capacity;

    public SimpleDrawer(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, long capacity) {
        super(BCGroups.STORAGES, item, recipeType, recipe);
        if (capacity < 1) {
            throw new IllegalArgumentException("Drawer capacity must be positive");
        }
        this.capacity = capacity;

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent event) {
                Block block = event.getBlockPlaced();
                DrawerData portable = DrawerStorage.loadFromPortableItem(event.getItemInHand());
                DrawerData restored = clamp(portable);
                DrawerStorage.write(block, restored);

                // Slimefun and Paper finish their placement bookkeeping after this
                // handler. Rebuild the display on the following server tick.
                Bukkit.getScheduler().runTask(BetterChests.INSTANCE, () -> {
                    if (block.getType() != Material.AIR) {
                        DrawerDisplayManager.update(block, DrawerStorage.read(block));
                    }
                });
            }
        });

        addItemHandler((BlockUseHandler) event -> {
            event.cancel();

            Optional<Block> clickedBlock = event.getClickedBlock();
            if (clickedBlock.isEmpty()) {
                return;
            }

            Block block = clickedBlock.get();
            Player player = event.getPlayer();
            if (!Slimefun.getProtectionManager().hasPermission(player, block.getLocation(), Interaction.INTERACT_BLOCK)) {
                player.sendMessage("§cYou do not have permission to interact with this block.");
                return;
            }

            synchronized (lock(block.getLocation())) {
                ItemStack hand = player.getInventory().getItemInMainHand();
                if (hand.getType() == Material.AIR || hand.getAmount() < 1) {
                    withdrawToPlayer(block, player);
                } else {
                    depositFromPlayer(block, player, hand);
                }
            }
        });

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(
                    @NotNull BlockBreakEvent event,
                    @NotNull ItemStack tool,
                    @NotNull List<ItemStack> drops
            ) {
                Block block = event.getBlock();
                synchronized (lock(block.getLocation())) {
                    DrawerData data = DrawerStorage.read(block);
                    ItemStack portableDrop = findOrCreateDrawerDrop(drops);
                    DrawerStorage.saveToPortableItem(portableDrop, data);
                    DrawerDisplayManager.remove(block);
                    LOCKS.remove(locationKey(block.getLocation()));
                }
            }
        });
    }

    public long getCapacity() {
        return capacity;
    }

    /** Safe display name used by SlimeHUD and display entities. */
    public static Component getItemName(@Nullable ItemStack item) {
        return DrawerDisplayManager.getItemName(item);
    }

    /** Returns true when the block is one of this addon's drawers. */
    public static boolean isDrawer(Block block) {
        return BlockStorage.check(block) instanceof SimpleDrawer;
    }

    /** Rebuilds a drawer's visual entities and migrates old Dev-16 data if found. */
    public static void repair(Block block) {
        if (BlockStorage.check(block) instanceof SimpleDrawer) {
            DrawerDisplayManager.repair(block);
        }
    }

    /** Removes only this addon's tagged display entities. */
    public static void removeDisplays(Block block) {
        DrawerDisplayManager.remove(block);
    }

    private void withdrawToPlayer(Block block, Player player) {
        DrawerData data = DrawerStorage.read(block);
        ItemStack stored = data.item();
        if (stored == null || data.count() <= 0) {
            player.sendMessage("§cThere is no item in this drawer.");
            DrawerDisplayManager.update(block, DrawerData.empty());
            return;
        }

        int requested = (int) Math.min(data.count(), stored.getMaxStackSize());
        ItemStack outgoing = stored.clone();
        outgoing.setAmount(requested);

        PlayerInventory inventory = player.getInventory();
        Map<Integer, ItemStack> leftovers = inventory.addItem(outgoing);
        int remaining = leftovers.values().stream().mapToInt(ItemStack::getAmount).sum();
        int accepted = requested - remaining;
        if (accepted <= 0) {
            player.sendMessage("§cYour inventory is full.");
            return;
        }

        DrawerData updated = new DrawerData(stored, data.count() - accepted);
        DrawerStorage.write(block, updated);
        DrawerDisplayManager.update(block, updated);
        player.updateInventory();
    }

    private void depositFromPlayer(Block block, Player player, ItemStack hand) {
        DrawerData data = DrawerStorage.read(block);
        ItemStack stored = data.item();
        ItemStack candidate = one(hand);

        if (stored != null && !stored.isSimilar(candidate)) {
            player.sendMessage("§cThe drawer already contains another item.");
            return;
        }

        long free = capacity - data.count();
        if (free <= 0) {
            player.sendMessage("§cThis drawer is full.");
            return;
        }

        int accepted = (int) Math.min(free, hand.getAmount());
        if (accepted <= 0) {
            player.sendMessage("§cThis drawer is full.");
            return;
        }

        DrawerData updated = new DrawerData(stored == null ? candidate : stored, data.count() + accepted);
        DrawerStorage.write(block, updated);
        DrawerDisplayManager.update(block, updated);

        int newAmount = hand.getAmount() - accepted;
        if (newAmount <= 0) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        } else {
            hand.setAmount(newAmount);
        }
        player.updateInventory();
    }

    private DrawerData clamp(DrawerData data) {
        if (data == null || data.isEmpty()) {
            return DrawerData.empty();
        }
        return new DrawerData(data.item(), Math.min(data.count(), capacity));
    }

    private ItemStack findOrCreateDrawerDrop(List<ItemStack> drops) {
        ItemStack template = getItem();
        for (ItemStack drop : drops) {
            if (drop != null && drop.isSimilar(template)) {
                return drop;
            }
        }

        ItemStack added = template.clone();
        added.setAmount(1);
        drops.add(added);
        return added;
    }

    /**
     * Adds as many items as possible and mutates {@code item} to the remainder.
     * The returned integer is the remaining amount, preserving the Dev-16 hook API.
     */
    public Pair<Boolean, Integer> addItem(Location barrelLoc, ItemStack item) {
        if (item == null || item.getType() == Material.AIR || item.getAmount() < 1) {
            return Pair.of(false, 0);
        }

        Block block = barrelLoc.getBlock();
        synchronized (lock(barrelLoc)) {
            DrawerData data = DrawerStorage.read(block);
            ItemStack stored = data.item();
            ItemStack candidate = one(item);
            if (stored != null && !stored.isSimilar(candidate)) {
                return Pair.of(false, item.getAmount());
            }

            long free = capacity - data.count();
            if (free <= 0) {
                return Pair.of(false, item.getAmount());
            }

            int accepted = (int) Math.min(free, item.getAmount());
            DrawerData updated = new DrawerData(stored == null ? candidate : stored, data.count() + accepted);
            DrawerStorage.write(block, updated);
            DrawerDisplayManager.update(block, updated);

            int remainder = item.getAmount() - accepted;
            if (remainder <= 0) {
                item.setType(Material.AIR);
            } else {
                item.setAmount(remainder);
            }
            return Pair.of(accepted > 0, Math.max(0, remainder));
        }
    }

    /** Returns the stored item with amount one, or null when empty. */
    public @Nullable ItemStack getStoringItem(Location barrelLoc) {
        return DrawerStorage.read(barrelLoc.getBlock()).item();
    }

    /** Returns the exact long count. */
    public long getStoringItemCount(Location barrelLoc) {
        return DrawerStorage.read(barrelLoc.getBlock()).count();
    }

    /** Returns a count safe for legacy APIs that only accept an int. */
    public int getStoringItemCountSafely(Location barrelLoc) {
        return (int) Math.min(getStoringItemCount(barrelLoc), Integer.MAX_VALUE - 1L);
    }

    /**
     * Removes exactly {@code count} items, returning null if insufficient stock exists.
     */
    public @Nullable ItemStack takeItem(Location barrelLoc, int count) {
        if (count < 1) {
            return null;
        }

        Block block = barrelLoc.getBlock();
        synchronized (lock(barrelLoc)) {
            DrawerData data = DrawerStorage.read(block);
            ItemStack stored = data.item();
            if (stored == null || data.count() < count) {
                return null;
            }

            ItemStack result = stored.clone();
            result.setAmount(count);
            DrawerData updated = new DrawerData(stored, data.count() - count);
            DrawerStorage.write(block, updated);
            DrawerDisplayManager.update(block, updated);
            return result;
        }
    }

    private static ItemStack one(ItemStack item) {
        ItemStack copy = item.clone();
        copy.setAmount(1);
        return copy;
    }

    private static Object lock(Location location) {
        return LOCKS.computeIfAbsent(locationKey(location), ignored -> new Object());
    }

    private static String locationKey(Location location) {
        return (location.getWorld() == null ? "unknown" : location.getWorld().getUID())
                + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
    }
}
