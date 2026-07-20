package me.mmmjjkx.betterChests.integrations;

import io.github.schntgaispock.slimehud.SlimeHUD;
import io.github.schntgaispock.slimehud.util.HudBuilder;
import me.mmmjjkx.betterChests.items.chests.SimpleDrawer;
import me.mmmjjkx.betterChests.items.chests.ie.IEStorageCache;
import me.mmmjjkx.betterChests.items.chests.ie.IEStorageUnit;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/** Optional integration isolated from the main class so missing SlimeHUD classes are harmless. */
public final class SlimeHudIntegration {

    private SlimeHudIntegration() {
    }

    public static void register() {
        SlimeHUD.getHudController().registerCustomHandler(SimpleDrawer.class, request -> {
            SimpleDrawer drawer = (SimpleDrawer) request.getSlimefunItem();
            Location location = request.getLocation();
            ItemStack item = drawer.getStoringItem(location);
            String itemName = LegacyComponentSerializer.legacyAmpersand()
                    .serialize(SimpleDrawer.getItemName(item));
            return "&7| &f" + itemName + " &7| &f"
                    + HudBuilder.getAbbreviatedNumber(drawer.getStoringItemCount(location))
                    + "/" + HudBuilder.getAbbreviatedNumber(drawer.getCapacity());
        });

        SlimeHUD.getHudController().registerCustomHandler(IEStorageUnit.class, request -> {
            IEStorageUnit unit = (IEStorageUnit) request.getSlimefunItem();
            Location location = request.getLocation();
            IEStorageCache cache = unit.getCache(location);
            long stored = cache == null ? 0 : cache.getStored();
            String itemName = LegacyComponentSerializer.legacyAmpersand()
                    .serialize(SimpleDrawer.getItemName(unit.getDisplayingItem(location.getBlock())));
            return "&7| &f" + itemName + " &7| &f"
                    + HudBuilder.getAbbreviatedNumber(stored)
                    + "/" + HudBuilder.getAbbreviatedNumber(unit.getCapacity());
        });
    }
}
