# Legacy Item Text Fix — 2.0.7-26.2

## Symptoms

- The IE storage `Quick Actions` item showed literal strings such as `&bLeft Click`.
- BetterChests items could show raw `&` color codes while dropped in the world, but appear correct after pickup because Slimefun packet translation only affected the inventory view.

## Fix

- `ItemStackBuilder` now writes Adventure display-name and lore components rather than raw legacy strings.
- Both ampersand (`&a`) and section-sign (`§a`) input are supported.
- Existing BetterChests item stacks are repaired during `ItemSpawnEvent` when their visible text still contains legacy codes.
- Correctly formatted components are not rewritten.
