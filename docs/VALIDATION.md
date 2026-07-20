# Validation and release notes

## Target

- Paper API: `26.2.build.62-beta`
- Runtime/build JDK: 25
- Slimefun addon API: upstream published `RC-37` API, intended for a binary-compatible Slimefun 4/United fork
- SlimeHUD: optional, compile-time `v1.2.7`

## Checks performed for the supplied binary

- Recompiled every rewritten class against an exact-shape compatibility harness.
- Loaded the rewritten classes with a Java 25.0.2 runtime.
- Verified JAR ZIP integrity and duplicate entries.
- Verified `plugin.yml`, language/config resources, and main-class presence.
- Checked that no compatibility-harness classes were packaged.
- Audited the rewritten storage paths for null handling, leftovers accounting, count overflow, persistence, and portable break/place behavior.
- Replaced the unregistered Dev-16 P2P ticker with an inert compatibility shell and removed the incomplete transfer-stick class from the binary.

## Environment limitation

The release workspace could not resolve PaperMC/JitPack Maven hosts, so a clean full Maven build and a live Paper + Slimefun server boot were not possible here. The repository and GitHub Actions workflow require JDK 25 and perform the normal full Maven build when internet access is available. The supplied binary is based on the uploaded Dev-16 JAR with the audited classes and resources replaced; it passed Java 25 class-loading and structural checks.

## Required staging checks before production

1. Back up the server, affected worlds, and Slimefun block-storage data.
2. Start a copied/staging server with only one BetterChests JAR installed.
3. Check startup for `BetterChests`, `Slimefun`, `NoSuchMethodError`, or `ClassNotFoundException` messages.
4. Test a newly placed drawer with vanilla and Slimefun items.
5. Restart while the drawer is filled, then verify the item and exact count.
6. Break and replace a low-value filled drawer; verify portable contents.
7. Test full-inventory withdrawal and partially full drawer deposits.
8. Test IE-style storage insertion, extraction, cargo input/output, restart, break, and replacement.
9. Confirm old drawers migrate before allowing players to use high-value storage.

Do not use `/reload`, PlugMan, or another hot-reload method for this migration.
