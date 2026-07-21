# Drawer Cargo Wrapper Fix — 2.0.6-26.2

Slimefun passes an immutable `ItemStackWrapper` to
`getSlotsAccessedByItemTransport` while probing cargo insertion slots. The
wrapper deliberately throws from `clone()` and all mutation methods.

Paper 26.2's `ItemStack(ItemStack)` copy constructor is not a workaround: it
internally delegates to `source.clone()`, so 2.0.4/2.0.5 could still trigger the
wrapper exception.

Version 2.0.6 fixes both layers:

1. `SimpleDrawer.canAcceptWholeCargoStack` no longer copies the cargo probe. It
   compares the stored item and wrapper directly with
   `SlimefunUtils.isItemSimilar(..., checkAmount=false)`.
2. `MutableItemStacks.copy` detects `ItemStackWrapper`. Normal Paper stacks use
   `clone()` to preserve every native component; wrappers are rebuilt as a new
   mutable Paper stack using their material, amount, and cloned cached
   `ItemMeta`.
3. Build-safe regression checks reject both `new ItemStack(source)` and wrapper
   copying in the cargo probe.

The IE storage-unit cargo implementation is unchanged.
