# Migration from Dev-16

1. Stop Paper completely.
2. Back up the entire server. At minimum preserve all worlds, `plugins/Slimefun/`, and `plugins/BetterChests/`.
3. Remove the old BetterChests JAR. Leave only one JAR whose plugin name is `BetterChests`.
4. Install the new JAR and start the server normally.
5. Visit representative old drawers. The fork writes recoverable legacy item/count state into persistent block storage and recreates tagged display entities.
6. Test breaking and replacing one filled low-value drawer.
7. Keep the backup until all storage has survived at least one full restart.

Legacy state can only be recovered when the old block metadata or display entities still contain it. Data that was already erased before installing the fork cannot be reconstructed.
