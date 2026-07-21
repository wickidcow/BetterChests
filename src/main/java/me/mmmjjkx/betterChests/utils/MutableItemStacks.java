package me.mmmjjkx.betterChests.utils;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Creates ordinary mutable Bukkit item stacks from arbitrary ItemStack
 * implementations. Slimefun passes immutable ItemStackWrapper instances to
 * cargo slot-selection callbacks; invoking clone(), the Bukkit copy
 * constructor, or a setter on those wrappers throws.
 */
public final class MutableItemStacks {

    private MutableItemStacks() {
    }

    /**
     * Returns an independent mutable stack.
     *
     * <p>Normal Bukkit/Paper stacks are cloned so all native data components
     * remain exact. Slimefun's ItemStackWrapper cannot be cloned and Paper's
     * ItemStack(ItemStack) constructor delegates to clone(), so wrappers are
     * rebuilt from the public type, amount, and cached ItemMeta snapshot.</p>
     */
    public static @NotNull ItemStack copy(@NotNull ItemStack source) {
        if (!(source instanceof ItemStackWrapper)) {
            return source.clone();
        }

        int amount = source.getAmount();
        ItemStack copy = ItemStack.of(source.getType(), Math.max(1, amount));

        if (source.hasItemMeta()) {
            ItemMeta meta = source.getItemMeta();
            copy.setItemMeta(meta.clone());
        }

        if (amount < 1) {
            copy.setAmount(amount);
        }

        return copy;
    }

    public static @NotNull ItemStack copyWithAmount(@NotNull ItemStack source, int amount) {
        ItemStack copy = copy(source);
        copy.setAmount(amount);
        return copy;
    }
}
