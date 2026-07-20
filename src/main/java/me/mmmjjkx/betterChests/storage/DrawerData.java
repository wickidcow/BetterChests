package me.mmmjjkx.betterChests.storage;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Immutable snapshot of one drawer's stored item and count.
 */
public record DrawerData(@Nullable ItemStack item, long count) {

    public DrawerData {
        if (count < 0) {
            count = 0;
        }

        if (item == null || item.getType() == Material.AIR || count == 0) {
            item = null;
            count = 0;
        } else {
            item = item.clone();
            item.setAmount(1);
        }
    }

    public static DrawerData empty() {
        return new DrawerData(null, 0);
    }

    public boolean isEmpty() {
        return item == null || count == 0;
    }

    @Override
    public @Nullable ItemStack item() {
        return item == null ? null : item.clone();
    }
}
