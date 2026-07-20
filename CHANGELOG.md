# Changelog

## 2.0.0-26.2 — Albion modernization

### Drawers
- Replaced temporary entity/metadata-only storage with persistent Slimefun block data.
- Added portable contents stored on the dropped drawer item.
- Added migration from recoverable Dev-16 display/entity data.
- Rewrote deposits, withdrawals, external insertion, and extraction with exact remainder accounting.
- Replaced unsafe item-name parsing with Paper Adventure display names.
- Added tagged, repairable item/name/count display entities.
- Added per-location locking and `long` capacity/count arithmetic.
- Removed invalid oversized ItemStack drops.

### Infinity-style storage
- Replaced legacy-only item serialization with Paper byte serialization plus legacy fallback.
- Fixed empty leftovers-array crashes.
- Fixed cargo input/output slot direction.
- Added null-safe cache reconstruction, break handling, placement restoration, and corrupted-value clamping.

### Other fixes
- Made chest breaking null-safe so contents are not skipped when a menu is unavailable.
- Hardened the Chest Colorer against malformed or short lore.
- Fixed Location Recorder coordinate updates, missing worlds, permissions, and missing inventories.
- Reworked language-file creation, completion, fallback, comments, and list colorization.
- Removed Lombok and the upstream auto-updater.
- Made SlimeHUD optional and isolated API linkage failures.
- Added Java 25 Maven/CI build configuration and reproducible build scripts.

### Compatibility
- Preserves existing BetterChests plugin name, data folder, `BC_*` item IDs, and registered recipes.
- Replaced the unregistered Dev-16 P2P implementation with an inert compatibility shell; the incomplete Item Transfer Stick remains excluded. Decompiled reference text is under `docs/legacy-dev16-decompiled/`.
