package me.mmmjjkx.betterChests.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Build-safe regression checks for the Slimefun ItemStackWrapper cargo fix.
 *
 * Paper 26.2's Material/ItemStack implementation needs a live server registry,
 * so constructing a Bukkit ItemStack in a plain Surefire JVM fails before the
 * actual copy behavior can be tested. These checks validate the production
 * implementation without bootstrapping a Paper server.
 */
class MutableItemStacksTest {

    @Test
    void mutableCopyAvoidsVirtualCloneCalls() throws IOException {
        Path utilityPath = Path.of(
            "src", "main", "java", "me", "mmmjjkx", "betterChests",
            "utils", "MutableItemStacks.java"
        );
        String utilitySource = Files.readString(utilityPath);

        assertTrue(utilitySource.contains("return new ItemStack(source);"),
            "MutableItemStacks.copy must use Bukkit's copy constructor");
        assertFalse(utilitySource.contains("source.clone()"),
            "Do not call clone() on a possible Slimefun ItemStackWrapper");
    }

    @Test
    void drawerCargoPathDoesNotCloneIncomingStacks() throws IOException {
        Path drawerPath = Path.of(
            "src", "main", "java", "me", "mmmjjkx", "betterChests",
            "items", "chests", "SimpleDrawer.java"
        );
        String drawerSource = Files.readString(drawerPath);

        assertFalse(drawerSource.contains(".clone()"),
            "SimpleDrawer cargo paths must not clone arbitrary ItemStack implementations");
        assertTrue(drawerSource.contains("MutableItemStacks"),
            "SimpleDrawer should route mutable copies through MutableItemStacks");
    }
}
