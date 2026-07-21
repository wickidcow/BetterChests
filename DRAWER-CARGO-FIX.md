# Drawer Cargo Fix — 2.0.4-26.2

This release changes only drawer cargo compatibility. The working IE storage cargo implementation is unchanged.

## Fixed

- Cargo nodes can now be placed against drawers instead of being blocked or treated as a deposited item.
- Cargo Output Nodes insert items into the drawer's persistent storage count.
- Cargo Input Nodes withdraw stacks from the drawer through a virtual output slot.
- Virtual output stacks are never dropped when the drawer is broken, preventing duplication.
- Any exceptional unprocessed input buffer is preserved on block break.
- Drawer cargo accepts only matching item types and refuses a stack when the full stack cannot fit.

## Testing

1. Sneak-place a Cargo Output Node against a drawer and send a matching item into it.
2. Verify the drawer display/count increases.
3. Sneak-place a Cargo Input Node against the drawer and route items to another container.
4. Verify the count decreases by exactly the amount transferred.
5. Restart and repeat.
6. Break a filled drawer and confirm no extra mirrored stack drops.
