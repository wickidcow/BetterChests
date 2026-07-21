# Changelog

## 2.0.6-26.2

- Fixed the remaining drawer cargo crash caused by Paper's `ItemStack(ItemStack)` copy constructor delegating to `source.clone()`.
- Removed all wrapper copying from the cargo slot-selection hot path and now compare candidates with Slimefun's wrapper-aware item comparator.
- Added a dedicated `ItemStackWrapper` reconstruction path using material, amount, and a cloned `ItemMeta` snapshot.
- Normal mutable Bukkit/Paper stacks continue to use `clone()` so their complete native data components remain intact.
- Updated regression checks to reject both direct wrapper cloning and Paper's wrapper-unsafe copy constructor.

## 2.0.5-26.2

- Fixed the GitHub Actions build failure caused by initializing Paper 26.2 registries inside a plain Maven test JVM.
- Reworked the immutable `ItemStackWrapper` regression test into a server-independent source validation test.
- Kept the 2.0.4 drawer cargo runtime fix unchanged.

## 2.0.4-26.2

- Fixed drawer cargo insertion crashing when Slimefun supplied an immutable `ItemStackWrapper`.
- Replaced virtual `ItemStack#clone()` calls on cargo-provided stacks with Bukkit's mutable copy constructor.
- Hardened drawer data snapshots and legacy drawer migration against immutable stack implementations.
- Kept the legacy drawer `addItem` mutation hook best-effort while making its returned remainder authoritative.

## 2.0.2-26.2 — Cargo compatibility repair

- Restored cargo insertion and withdrawal for Infinity-style storage units.
- Dynamic cargo routing now identifies insertion by the non-null candidate item and withdrawal by a null candidate item.
- Supports both standard Slimefun cargo semantics and maintained forks that historically supplied the opposite `ItemTransportFlow` enum value.
- Keeps the standard input/output mapping for integrations that use the one-argument transport API.

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
