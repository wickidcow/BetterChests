package me.mmmjjkx.betterChests.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Server-independent regression checks for drawer cargo rollback and drops.
 */
class SimpleDrawerSafetyTest {

    private static String drawerSource() throws IOException {
        return Files.readString(Path.of(
            "src", "main", "java", "me", "mmmjjkx", "betterChests",
            "items", "chests", "SimpleDrawer.java"
        ));
    }

    @Test
    void cargoWithdrawalDoesNotImmediatelyRefillSourceSlot() throws IOException {
        String source = drawerSource();

        assertTrue(source.contains("return next;"),
            "The real post-withdrawal slot state must be preserved for cargo rollback");
        assertTrue(source.contains("restoreCargoRollback(block, next)"),
            "Undelivered cargo must be restored to persistent drawer storage");
        assertTrue(source.contains("scheduleCargoOutputSync(block)"),
            "The virtual output mirror must be rebuilt after cargo routing finishes");
        assertFalse(source.contains("return replacement;"),
            "The output callback must not immediately refill the source slot");
    }

    @Test
    void drawerBreakHandlerIsTheOnlyDrawerDropSource() throws IOException {
        String source = drawerSource();

        assertTrue(source.contains("drops.removeIf(SimpleDrawer.this::isItem)"),
            "The break handler must remove accidental duplicate drawer drops");
        assertTrue(source.contains("pendingBreakDrop.set(portableDrop)"),
            "The break handler must stage one portable drawer drop");
        assertTrue(source.contains("public @NotNull Collection<ItemStack> getDrops()"),
            "Drawers must override Slimefun's post-handler drop call");
        assertTrue(source.contains("consumePendingBreakDrop()"),
            "The staged portable drawer must be consumed exactly once");
        assertTrue(source.contains("portable == null ? super.getDrops() : List.of(portable)"),
            "Normal drop behavior must remain available outside a handled break");
    }
}
