package me.mmmjjkx.betterChests.storage;

import me.mmmjjkx.betterChests.BetterChests;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** Maintains the three lightweight display entities used by each drawer. */
public final class DrawerDisplayManager {

    private static final ItemStack EMPTY_ICON = new ItemStack(Material.BARRIER);
    private static final Transformation ITEM_TRANSFORMATION = new Transformation(
            new Vector3f(), new AxisAngle4f(), new Vector3f(0.39F, 0.39F, 0.39F), new AxisAngle4f());

    private DrawerDisplayManager() {
    }

    public static void update(Block block, DrawerData data) {
        if (!block.getChunk().isLoaded()) {
            return;
        }

        Map<Role, Entity> displays = findOrCreate(block);
        ItemDisplay itemDisplay = (ItemDisplay) displays.get(Role.ITEM);
        TextDisplay nameDisplay = (TextDisplay) displays.get(Role.NAME);
        TextDisplay countDisplay = (TextDisplay) displays.get(Role.COUNT);

        if (data == null || data.isEmpty()) {
            itemDisplay.setItemStack(EMPTY_ICON.clone());
            nameDisplay.text(Component.text("Empty"));
            countDisplay.text(Component.text("0"));
        } else {
            ItemStack item = data.item();
            if (item == null) {
                itemDisplay.setItemStack(EMPTY_ICON.clone());
                nameDisplay.text(Component.text("Empty"));
                countDisplay.text(Component.text("0"));
            } else {
                item.setAmount(1);
                itemDisplay.setItemStack(item);
                nameDisplay.text(getItemName(item));
                countDisplay.text(Component.text(Long.toString(data.count())));
            }
        }
    }

    public static void repair(Block block) {
        update(block, DrawerStorage.read(block));
    }

    public static void remove(Block block) {
        String owner = ownerId(block);
        Location center = block.getLocation().add(0.5, 0.5, 0.5);
        for (Entity entity : block.getWorld().getNearbyEntities(center, 1.35, 1.35, 1.35)) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String entityOwner = pdc.get(ownerKey(), PersistentDataType.STRING);
            if (owner.equals(entityOwner)) {
                entity.remove();
            }
        }
    }

    public static Component getItemName(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return Component.text("Empty");
        }
        // Paper supplies a safe display-name component for both custom and vanilla items.
        // Do not index TranslatableComponent arguments: vanilla components may have none.
        return item.displayName();
    }

    private static Map<Role, Entity> findOrCreate(Block block) {
        String owner = ownerId(block);
        Location center = block.getLocation().add(0.5, 0.5, 0.5);
        Map<Role, Entity> found = new EnumMap<>(Role.class);
        List<Entity> duplicates = new ArrayList<>();

        for (Entity entity : block.getWorld().getNearbyEntities(center, 1.35, 1.35, 1.35)) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String entityOwner = pdc.get(ownerKey(), PersistentDataType.STRING);
            String roleName = pdc.get(roleKey(), PersistentDataType.STRING);
            if (!owner.equals(entityOwner) || roleName == null) {
                continue;
            }

            Role role = Role.from(roleName);
            if (role == null || !role.matches(entity)) {
                duplicates.add(entity);
                continue;
            }

            Entity previous = found.putIfAbsent(role, entity);
            if (previous != null) {
                duplicates.add(entity);
            }
        }

        duplicates.forEach(Entity::remove);

        BlockFace facing = getFacing(block);
        Location base = frontLocation(block.getLocation(), facing);
        float yaw = yaw(facing);

        found.computeIfAbsent(Role.ITEM, ignored -> spawnItem(block.getWorld(), base, owner, yaw));
        found.computeIfAbsent(Role.NAME, ignored -> spawnText(
                block.getWorld(), base.clone().add(0, 0.2, 0.001), owner, Role.NAME, "Empty", yaw));
        found.computeIfAbsent(Role.COUNT, ignored -> spawnText(
                block.getWorld(), base.clone().add(0, -0.5, -0.001), owner, Role.COUNT, "0", yaw));

        // A drawer can be rotated by another plugin or by future migration code.
        for (Entity entity : found.values()) {
            entity.setRotation(yaw, 0);
        }

        return found;
    }

    private static ItemDisplay spawnItem(World world, Location location, String owner, float yaw) {
        return world.spawn(location, ItemDisplay.class, display -> {
            display.setItemStack(EMPTY_ICON.clone());
            display.setTransformation(ITEM_TRANSFORMATION);
            display.setPersistent(true);
            display.setInvulnerable(true);
            display.setGravity(false);
            display.setRotation(yaw, 0);
            tag(display, owner, Role.ITEM);
        });
    }

    private static TextDisplay spawnText(
            World world,
            Location location,
            String owner,
            Role role,
            String initialText,
            float yaw
    ) {
        return world.spawn(location, TextDisplay.class, display -> {
            display.text(Component.text(initialText));
            display.setPersistent(true);
            display.setInvulnerable(true);
            display.setGravity(false);
            display.setVisibleByDefault(true);
            display.setAlignment(TextDisplay.TextAlignment.CENTER);
            display.setRotation(yaw, 0);
            tag(display, owner, role);
        });
    }

    private static void tag(Entity entity, String owner, Role role) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(ownerKey(), PersistentDataType.STRING, owner);
        pdc.set(roleKey(), PersistentDataType.STRING, role.name());
    }

    private static BlockFace getFacing(Block block) {
        BlockData data = block.getBlockData();
        if (data instanceof Directional directional) {
            BlockFace face = directional.getFacing();
            if (face == BlockFace.NORTH || face == BlockFace.SOUTH
                    || face == BlockFace.EAST || face == BlockFace.WEST) {
                return face;
            }
        }
        return BlockFace.NORTH;
    }

    private static Location frontLocation(Location blockLocation, BlockFace facing) {
        return switch (facing) {
            case SOUTH -> blockLocation.clone().add(0.5, 0.5, 1.002);
            case WEST -> blockLocation.clone().add(-0.002, 0.5, 0.5);
            case EAST -> blockLocation.clone().add(1.002, 0.5, 0.5);
            default -> blockLocation.clone().add(0.5, 0.5, -0.002);
        };
    }

    private static float yaw(BlockFace facing) {
        return switch (facing) {
            case SOUTH -> 0F;
            case WEST -> 90F;
            case EAST -> -90F;
            default -> 180F;
        };
    }

    private static String ownerId(Block block) {
        return block.getWorld().getUID() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
    }

    private static NamespacedKey ownerKey() {
        return new NamespacedKey(BetterChests.INSTANCE, "drawer_display_owner_v2");
    }

    private static NamespacedKey roleKey() {
        return new NamespacedKey(BetterChests.INSTANCE, "drawer_display_role_v2");
    }

    private enum Role {
        ITEM,
        NAME,
        COUNT;

        static Role from(String value) {
            try {
                return Role.valueOf(value);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }

        boolean matches(Entity entity) {
            return this == ITEM ? entity instanceof ItemDisplay : entity instanceof TextDisplay;
        }
    }
}
