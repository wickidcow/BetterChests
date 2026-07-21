package me.mmmjjkx.betterChests.utils;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Creates ordinary mutable Bukkit item stacks from arbitrary ItemStack
 * implementations. Slimefun may pass immutable ItemStackWrapper instances to
 * cargo callbacks; invoking clone() or any setter on those wrappers throws.
 */
public final class MutableItemStacks {

    private MutableItemStacks() {
    }

    /**
     * Uses Bukkit's copy constructor instead of virtual ItemStack#clone().
     * This preserves the source stack's item data while returning a normal,
     * mutable ItemStack even when the source is a Slimefun wrapper.
     */
    public static @NotNull ItemStack copy(@NotNull ItemStack source) {
        return new ItemStack(source);
    }

    public static @NotNull ItemStack copyWithAmount(@NotNull ItemStack source, int amount) {
        ItemStack copy = copy(source);
        copy.setAmount(amount);
        return copy;
    }
}
