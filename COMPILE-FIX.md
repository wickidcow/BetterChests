# BetterChests 2.0.7 compilation fix

Replace this repository file:

`src/main/java/me/mmmjjkx/betterChests/utils/LegacyText.java`

The only source correction is the Adventure import:

```java
import net.kyori.adventure.text.format.TextDecoration;
```

`TextDecoration` is part of Adventure's `text.format` package, not the root `text` package.
