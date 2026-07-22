# Drawer cargo rollback and duplicate-drop fix

## Cargo item loss

Slimefun removes the source stack before trying destinations. If no destination accepts the stack, `CargoNetworkTask` restores the remainder into the original source slot.

The previous drawer adapter immediately refilled its virtual output slot inside `onItemStackChange`. That made the original slot appear occupied, so Slimefun could not restore the undelivered stack. On networks configured to delete cargo overflow, the stack was lost.

The drawer now:

1. Deducts only the amount actually removed from the virtual output slot.
2. Leaves the slot empty during the remainder of the cargo route.
3. Absorbs any stack Slimefun returns to that slot back into persistent drawer storage.
4. Rebuilds the virtual output mirror on the next server tick.

This also supports partial delivery: only the delivered amount remains deducted.

## Duplicate drawer drops

Slimefun invokes `BlockBreakHandler` first and appends `SlimefunItem#getDrops()` afterward. The previous handler created a portable drawer drop, and the inherited `getDrops()` then appended a second plain drawer.

The break handler now stages one block-specific portable drawer. When Slimefun performs its normal post-handler `getDrops()` call, the drawer returns that staged item exactly once and then resumes normal default drop behavior. Filled drawer contents remain attached to the single dropped item.
