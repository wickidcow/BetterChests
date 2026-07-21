package me.mmmjjkx.betterChests.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mmmjjkx.betterChests.utils.LegacyText;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/** Repairs legacy BetterChests names when old item stacks become world entities. */
public final class LegacyItemTextListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        ItemStack stack = event.getEntity().getItemStack();
        SlimefunItem slimefunItem = SlimefunItem.getByItem(stack);
        if (slimefunItem == null || !slimefunItem.getId().startsWith("BC_")) {
            return;
        }

        ItemMeta meta = stack.getItemMeta();
        boolean changed = false;

        Component displayName = meta.displayName();
        Component repairedName = LegacyText.repair(displayName);
        if (repairedName != displayName && !repairedName.equals(displayName)) {
            meta.displayName(repairedName);
            changed = true;
        }

        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            List<Component> repairedLore = new ArrayList<>(lore.size());
            boolean loreChanged = false;
            for (Component line : lore) {
                Component repaired = LegacyText.repair(line);
                repairedLore.add(repaired);
                loreChanged |= repaired != line && !repaired.equals(line);
            }
            if (loreChanged) {
                meta.lore(repairedLore);
                changed = true;
            }
        }

        if (changed) {
            stack.setItemMeta(meta);
            event.getEntity().setItemStack(stack);
        }
    }
}
