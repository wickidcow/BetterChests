# BetterChests Albion Fork

A source-complete modernization of SfBetterChests for Paper 26.2 and Java 25.
It preserves the existing `BC_*` Slimefun item IDs so placed blocks and crafted
items remain recognizable after replacing Dev-16.

## Important fixes

- Drawer contents are stored in Slimefun's persistent block database.
- Existing Dev-16 drawers migrate from runtime metadata/display entities when recoverable.
- Filled drawers keep their contents in item PDC when broken and placed again.
- Breaking a drawer no longer creates an illegal item stack with millions of items.
- All drawer counts and capacity calculations use `long` arithmetic.
- Vanilla/custom item names no longer crash through unsafe translatable-component indexing.
- Missing/duplicate display entities are repaired and tagged to their owning drawer.
- Inventory withdrawal only subtracts items actually accepted by the player inventory.
- IE-style storage withdrawal no longer indexes an empty leftovers array.
- Portable IE units use Paper's migration-safe ItemStack byte serialization with Dev-16 fallback.
- The upstream auto-updater was removed so it cannot overwrite fork fixes.

## Player controls

- Right-click while holding an item: deposit matching items.
- Right-click with an empty main hand: withdraw up to one normal stack.
- The barrel inventory does not open; that is intentional.
- Vanilla hoppers do not access drawers; drawers remain `NotHopperable`.
- Slimefun Cargo Input and Output Nodes may be attached to drawers.
- Cargo-provided immutable item wrappers are converted to mutable Bukkit copies before storage.

## Upgrade procedure

1. Stop the server completely.
2. Back up the whole server, especially Slimefun block data and affected worlds.
3. Remove the old `BetterChests.jar`; do not run both jars together.
4. Place the new jar in `plugins/`.
5. Start the server and inspect startup errors before allowing players online.
6. Visit several old drawers and confirm their item/count displays and contents.
7. Test breaking and replacing a filled low-value drawer before upgrading production storage.

Migration can only recover legacy contents that still exist in runtime metadata or the
old display entities. A drawer whose Dev-16 metadata and entities were already lost
before this fork is installed cannot be reconstructed from nothing.

See [BUILDING.md](BUILDING.md) for compilation instructions, [docs/MIGRATION.md](docs/MIGRATION.md) for the upgrade checklist, and [docs/VALIDATION.md](docs/VALIDATION.md) for release validation and limitations.

## Dormant Dev-16 code

Dev-16 constructed a Point-to-Point Transfer object during static initialization but
never registered it as a usable Slimefun item. Its ticker contained destructive
source-slot and destination-location errors. This fork replaces it with an inert
compatibility shell and leaves it unregistered. The incomplete Item Transfer Stick is
excluded from builds. Decompiled reference text remains under
`docs/legacy-dev16-decompiled/` for future redesign work.
