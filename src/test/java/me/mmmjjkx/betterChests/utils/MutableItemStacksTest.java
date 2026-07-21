package me.mmmjjkx.betterChests.utils;

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MutableItemStacksTest {

    @Test
    void copyWithAmountSupportsSlimefunItemStackWrapper() {
        ItemStack source = new ItemStack(Material.DIAMOND, 32);
        ItemStack immutable = ItemStackWrapper.forceWrap(source);

        assertThrows(UnsupportedOperationException.class, immutable::clone);

        ItemStack result = MutableItemStacks.copyWithAmount(immutable, 1);

        assertNotSame(immutable, result);
        assertEquals(Material.DIAMOND, result.getType());
        assertEquals(1, result.getAmount());
        assertEquals(32, immutable.getAmount());
    }
}
