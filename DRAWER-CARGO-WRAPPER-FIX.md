# Drawer cargo ItemStackWrapper fix

Slimefun may pass an immutable `ItemStackWrapper` to dynamic cargo slot callbacks.
Calling `clone()`, `setAmount`, or other mutators on that wrapper throws
`UnsupportedOperationException`.

Version 2.0.4 converts arbitrary incoming stacks through Bukkit's
`new ItemStack(source)` copy constructor before changing amounts or storing them.
This specifically fixes the crash in `SimpleDrawer.one(...)` and also hardens the
remaining drawer cargo, persistence, break-drop, and migration copy paths.

## CI test note

Paper 26.2 requires live registry access when constructing `Material`/`ItemStack`
objects. The Maven regression test therefore validates the source implementation
without initializing Bukkit item registries in Surefire. Runtime behavior is still
validated on a Paper server.
