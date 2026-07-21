# Drawer cargo ItemStackWrapper fix

Slimefun may pass an immutable `ItemStackWrapper` to dynamic cargo slot callbacks.
Calling `clone()`, `setAmount`, or other mutators on that wrapper throws
`UnsupportedOperationException`.

Version 2.0.4 converts arbitrary incoming stacks through Bukkit's
`new ItemStack(source)` copy constructor before changing amounts or storing them.
This specifically fixes the crash in `SimpleDrawer.one(...)` and also hardens the
remaining drawer cargo, persistence, break-drop, and migration copy paths.
