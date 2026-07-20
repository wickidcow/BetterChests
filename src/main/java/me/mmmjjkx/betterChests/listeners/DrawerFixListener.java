package me.mmmjjkx.betterChests.listeners;

import me.mmmjjkx.betterChests.BetterChests;
import me.mmmjjkx.betterChests.items.chests.SimpleDrawer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.world.ChunkLoadEvent;

/** Repairs drawer visuals and prevents the backing barrel GUI from opening. */
public final class DrawerFixListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getLocation() == null) {
            return;
        }

        Block block = event.getInventory().getLocation().getBlock();
        if (SimpleDrawer.isDrawer(block)) {
            event.setCancelled(true);
            SimpleDrawer.repair(block);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Delay one tick so Slimefun's block data for this chunk is available.
        Bukkit.getScheduler().runTask(BetterChests.INSTANCE, () -> {
            if (!event.getChunk().isLoaded()) {
                return;
            }

            for (BlockState state : event.getChunk().getTileEntities()) {
                Block block = state.getBlock();
                if (SimpleDrawer.isDrawer(block)) {
                    SimpleDrawer.repair(block);
                }
            }
        });
    }
}
