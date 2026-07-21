package me.mmmjjkx.betterChests.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Build-safe regression checks for Slimefun's immutable ItemStackWrapper.
 *
 * Paper's item registry is unavailable in a plain Surefire JVM, so these
 * checks validate the production source without constructing Bukkit items.
 */
class MutableItemStacksTest {

    @Test
    void wrapperCopyNeverUsesItemStackCloneOrCopyConstructor() throws IOException {
        String source = Files.readString(Path.of(
            "src", "main", "java", "me", "mmmjjkx", "betterChests",
            "utils", "MutableItemStacks.java"
        ));

        assertTrue(source.contains("source instanceof ItemStackWrapper"),
            "The immutable Slimefun wrapper must have a dedicated branch");
        assertTrue(source.contains("ItemStack.of(source.getType(), Math.max(1, amount))"),
            "Wrappers must be reconstructed as a normal Paper stack");
        assertTrue(source.contains("copy.setItemMeta(meta.clone())"),
            "The wrapper's cached metadata must be copied independently");
        assertFalse(source.contains("new ItemStack(source)"),
            "Paper's ItemStack copy constructor calls source.clone()");
    }

    @Test
    void cargoSlotProbeComparesWrapperWithoutCopyingIt() throws IOException {
        String source = Files.readString(Path.of(
            "src", "main", "java", "me", "mmmjjkx", "betterChests",
            "items", "chests", "SimpleDrawer.java"
        ));

        assertTrue(source.contains("SlimefunUtils.isItemSimilar(stored, item, true, false)"),
            "Drawer cargo probing must use Slimefun's wrapper-aware comparator");
        assertFalse(source.contains("ItemStack candidate = one(item);\n            return (stored == null"),
            "The cargo probe must not copy the immutable wrapper");
    }
}
