package me.mmmjjkx.betterChests.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.RadioactiveItem;
import me.mmmjjkx.betterChests.BCGroups;
import me.mmmjjkx.betterChests.BetterChests;
import me.mmmjjkx.betterChests.items.cargo.P2PTransfer;
import me.mmmjjkx.betterChests.items.chests.OnlyInputChest;
import me.mmmjjkx.betterChests.items.chests.OnlyOutputChest;
import me.mmmjjkx.betterChests.items.chests.SimpleChest;
import me.mmmjjkx.betterChests.items.chests.SimpleDrawer;
import me.mmmjjkx.betterChests.items.chests.ie.IEStorageUnit;
import me.mmmjjkx.betterChests.items.machines.ChestDisassembler;
import me.mmmjjkx.betterChests.items.tools.ChestColorer;
import me.mmmjjkx.betterChests.items.tools.LocationRecorder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BCItems {
    public static final SlimefunItem TIP = new SlimefunItem(
            BCGroups.STORAGES,
            new SlimefunItemStack("BC_TTTTTTTTTTTTTTTTTTTTIP_ITEM", BCItemStacks.TIP),
            RecipeType.NULL,
            new ItemStack[9]);

    // Tools & machines
    public static final LocationRecorder LOCATION_RECORDER = new LocationRecorder(
            new SlimefunItemStack("BC_LOCATION_RECORDER", BCItemStacks.LOCATION_RECORDER),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, SlimefunItems.SYNTHETIC_SAPPHIRE.getItem().getItem(), null,
                    null, SlimefunItems.GPS_TRANSMITTER_3.getItem().getItem(), null,
                    null, SlimefunItems.GPS_MARKER_TOOL.getItem().getItem(), null
            });
    public static final ChestDisassembler CHEST_DISASSEMBLER = new ChestDisassembler(
            new SlimefunItemStack("BC_CHEST_DISASSEMBLER", BCItemStacks.CHEST_DISASSEMBLER),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.IRON_AXE), null,
                    new ItemStack(Material.IRON_INGOT), new ItemStack(Material.GLASS), new ItemStack(Material.IRON_INGOT),
                    null, new ItemStack(Material.REDSTONE), null
            });
    public static final ChestColorer CHEST_COLORER = new ChestColorer(
            new SlimefunItemStack("BC_CHEST_COLORER", BCItemStacks.CHEST_COLOR_CHANGER),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.GLASS), null,
                    new ItemStack(Material.WHITE_DYE), new ItemStack(Material.STICK), new ItemStack(Material.BLUE_DYE),
                    new ItemStack(Material.RED_DYE), new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.YELLOW_DYE)
            });

    // Materials
    public static final SlimefunItem GEAR_WHEEL = new SlimefunItem(
            BCGroups.MATERIALS,
            new SlimefunItemStack("BC_GEAR_WHEEL", BCItemStacks.GEAR_WHEEL),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.IRON_INGOT), null,
                    new ItemStack(Material.IRON_INGOT), new ItemStack(Material.REDSTONE), new ItemStack(Material.IRON_INGOT),
                    null, new ItemStack(Material.IRON_INGOT), null
            });

    public static final SlimefunItem TIGHTLY_BLISTERING_INGOT = new RadioactiveItem(
            BCGroups.MATERIALS,
            Radioactivity.VERY_HIGH,
            new SlimefunItemStack("BC_TIGHTLY_BLISTERING_INGOT", BCItemStacks.TIGHTLY_BLISTERING_INGOT),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem(), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(),
                    SlimefunItems.BLISTERING_INGOT_3.getItem().getItem(), SlimefunItems.BLISTERING_INGOT_3.getItem().getItem(), SlimefunItems.BLISTERING_INGOT_3.getItem().getItem(),
                    SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem(), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem()
            });

    public static final SlimefunItem TIGHTLY_BLISTERING_PLATE = new RadioactiveItem(
            BCGroups.MATERIALS,
            Radioactivity.HIGH,
            new SlimefunItemStack("BC_TIGHTLY_BLISTERING_PLATE", BCItemStacks.TIGHTLY_BLISTERING_PLATE),
            RecipeType.COMPRESSOR,
            new ItemStack[]{
                    TIGHTLY_BLISTERING_INGOT.getItem().clone().add(4)
            });

    // Storages
    public static final SimpleChest CHEST_27 = new SimpleChest(
            27,
            new SlimefunItemStack("BC_CHEST_27", Material.GLASS, "&b&lSimple Chest"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, null, null,
                    new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.CHEST), new ItemStack(Material.OAK_PLANKS),
                    null, null, null
            });
    public static final OnlyInputChest CHEST_INPUT_27 = new OnlyInputChest(
            27,
            new SlimefunItemStack("BC_CHEST_INPUT_27", Material.GLASS, "&b&lSimple Chest &c&l(Input Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.HOPPER), null,
                    null, CHEST_27.getItem().clone(), null,
                    null, new ItemStack(Material.REDSTONE), null
            });
    public static final OnlyOutputChest CHEST_OUTPUT_27 = new OnlyOutputChest(
            27,
            new SlimefunItemStack("BC_CHEST_OUTPUT_27", Material.GLASS, "&b&lSimple Chest &c&l(Output Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.REDSTONE), null,
                    null, CHEST_27.getItem().clone(), null,
                    null, new ItemStack(Material.HOPPER), null
            });
    public static final SimpleChest CHEST_36 = new SimpleChest(
            36,
            new SlimefunItemStack("BC_CHEST_36", Material.GLASS, "&b&lBig Chest"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, null, null,
                    new ItemStack(Material.OAK_PLANKS), CHEST_27.getItem().clone(), new ItemStack(Material.OAK_PLANKS),
                    null, new ItemStack(Material.IRON_INGOT), null
            });
    public static final OnlyInputChest CHEST_INPUT_36 = new OnlyInputChest(
            36,
            new SlimefunItemStack("BC_CHEST_INPUT_36", Material.GLASS, "&b&lBig Chest &c&l(Input Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.HOPPER), null,
                    null, CHEST_36.getItem().clone(), null,
                    null, new ItemStack(Material.REDSTONE), null
            });
    public static final OnlyOutputChest CHEST_OUTPUT_36 = new OnlyOutputChest(
            36,
            new SlimefunItemStack("BC_CHEST_OUTPUT_36", Material.GLASS, "&b&lBig Chest &c&l(Output Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.REDSTONE), null,
                    null, CHEST_36.getItem().clone(), null,
                    null, new ItemStack(Material.HOPPER), null
            });
    public static final SimpleChest CHEST_45 = new SimpleChest(
            45,
            new SlimefunItemStack("BC_CHEST_45", Material.GLASS, "&b&lBigger Chest"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, null, null,
                    new ItemStack(Material.OAK_PLANKS), CHEST_36.getItem().clone(), new ItemStack(Material.OAK_PLANKS),
                    null, SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), null
            });
    public static final OnlyInputChest CHEST_INPUT_45 = new OnlyInputChest(
            45,
            new SlimefunItemStack("BC_CHEST_INPUT_45", Material.GLASS, "&b&lBigger Chest &c&l(Input Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.HOPPER), null,
                    null, CHEST_45.getItem().clone(), null,
                    null, new ItemStack(Material.REDSTONE), null
            });
    public static final OnlyOutputChest CHEST_OUTPUT_45 = new OnlyOutputChest(
            45,
            new SlimefunItemStack("BC_CHEST_OUTPUT_45", Material.GLASS, "&b&lBigger Chest &c&l(Output Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.REDSTONE), null,
                    null, CHEST_45.getItem().clone(), null,
                    null, new ItemStack(Material.HOPPER), null
            });
    public static final SimpleChest CHEST_54 = new SimpleChest(
            54,
            new SlimefunItemStack("BC_CHEST_54", Material.GLASS, "&b&lThe Biggest Chest"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, null, null,
                    new ItemStack(Material.OAK_PLANKS), CHEST_45.getItem().clone(), new ItemStack(Material.OAK_PLANKS),
                    null, SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem().clone(), null
            });
    public static final OnlyInputChest CHEST_INPUT_54 = new OnlyInputChest(
            54,
            new SlimefunItemStack("BC_CHEST_INPUT_54", Material.GLASS, "&b&lThe Biggest Chest &c&l(Input Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.HOPPER), null,
                    null, CHEST_54.getItem().clone(), null,
                    null, new ItemStack(Material.REDSTONE), null
            });
    public static final OnlyOutputChest CHEST_OUTPUT_54 = new OnlyOutputChest(
            54,
            new SlimefunItemStack("BC_CHEST_OUTPUT_54", Material.GLASS, "&b&lThe Biggest Chest &c&l(Output Only)"),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    null, new ItemStack(Material.REDSTONE), null,
                    null, CHEST_54.getItem().clone(), null,
                    null, new ItemStack(Material.HOPPER), null
            });

    // Drawers
    public static final SimpleDrawer DRAWER_1 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_1", BCItemStacks.DRAWER_1),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    new ItemStack(Material.OAK_LOG), new ItemStack(Material.OAK_PLANKS), new ItemStack(Material.OAK_LOG),
                    new ItemStack(Material.OAK_LOG), new ItemStack(Material.CHEST), new ItemStack(Material.OAK_LOG),
                    new ItemStack(Material.OAK_LOG), new ItemStack(Material.LEVER), new ItemStack(Material.OAK_LOG)
            }, 1024);
    public static final SimpleDrawer DRAWER_2 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_2", BCItemStacks.DRAWER_2),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    new ItemStack(Material.OAK_LOG), new ItemStack(Material.CHEST), new ItemStack(Material.OAK_LOG),
                    new ItemStack(Material.CHEST), DRAWER_1.getItem().clone(), new ItemStack(Material.CHEST),
                    new ItemStack(Material.OAK_LOG), new ItemStack(Material.LEVER), new ItemStack(Material.OAK_LOG)
            }, 4096);
    public static final SimpleDrawer DRAWER_3 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_3", BCItemStacks.DRAWER_3),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    new ItemStack(Material.IRON_INGOT), new ItemStack(Material.CHEST), new ItemStack(Material.IRON_INGOT),
                    new ItemStack(Material.CHEST), DRAWER_2.getItem().clone(), new ItemStack(Material.CHEST),
                    new ItemStack(Material.IRON_INGOT), new ItemStack(Material.LEVER), new ItemStack(Material.IRON_INGOT)
            }, 16384);
    public static final SimpleDrawer DRAWER_4 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_4", BCItemStacks.DRAWER_4),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND),
                    CHEST_36.getItem().clone(), DRAWER_3.getItem().clone(), CHEST_36.getItem().clone(),
                    new ItemStack(Material.DIAMOND), new ItemStack(Material.LEVER), new ItemStack(Material.DIAMOND)
            }, 262_000);
    public static final SimpleDrawer DRAWER_5 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_5", BCItemStacks.DRAWER_5),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem(),
                    CHEST_45.getItem().clone(), DRAWER_4.getItem().clone(), CHEST_45.getItem().clone(),
                    SlimefunItems.SYNTHETIC_SAPPHIRE.getItem().getItem(), new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SYNTHETIC_SAPPHIRE.getItem().getItem()
            }, 1_000_000);
    public static final SimpleDrawer DRAWER_6 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_6", BCItemStacks.DRAWER_6),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_5.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(), new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem()
            }, 4_000_000);
    public static final SimpleDrawer DRAWER_7 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_7", BCItemStacks.DRAWER_7),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(), SlimefunItems.BLISTERING_INGOT.getItem().getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_6.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(), new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem()
            }, 16_000_000);
    public static final SimpleDrawer DRAWER_8 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_8", BCItemStacks.DRAWER_8),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.BLISTERING_INGOT_2.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_7.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem(), new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem()
            }, 64_000_000);
    public static final SimpleDrawer DRAWER_9 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_9", BCItemStacks.DRAWER_9),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.BLISTERING_INGOT_3.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_8.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem().clone(), SlimefunItems.REINFORCED_PLATE.getItem().getItem()
            }, 256_000_000);
    public static final SimpleDrawer DRAWER_10 = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_10", BCItemStacks.DRAWER_10),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), TIGHTLY_BLISTERING_INGOT.getItem().clone(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_9.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem().clone(), SlimefunItems.REINFORCED_PLATE.getItem().getItem()
            }, 1_000_000_000);
    public static final SimpleDrawer DRAWER_MAX = new SimpleDrawer(
            new SlimefunItemStack("BC_DRAWER_MAX", BCItemStacks.DRAWER_MAX),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), TIGHTLY_BLISTERING_PLATE.getItem().clone(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    CHEST_54.getItem().clone(), DRAWER_10.getItem().clone(), CHEST_54.getItem().clone(),
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem().clone(), SlimefunItems.REINFORCED_PLATE.getItem().getItem()
            }, 2_140_000_000);

    // IE Storage units
    public static final IEStorageUnit IE_STORAGE_UNIT_1 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_1", BCItemStacks.IE_STORAGE_UNIT_1),
            32_000,
            new ItemStack[]{
                    SlimefunItems.COPPER_INGOT.getItem().getItem(), SlimefunItems.COPPER_INGOT.getItem().getItem(), SlimefunItems.COPPER_INGOT.getItem().getItem(),
                    GEAR_WHEEL.getItem().clone(), new ItemStack(Material.CHEST), GEAR_WHEEL.getItem().clone(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), new ItemStack(Material.OAK_LOG), SlimefunItems.SILICON.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_2 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_2", BCItemStacks.IE_STORAGE_UNIT_2),
            128_000,
            new ItemStack[]{
                    SlimefunItems.ALUMINUM_BRONZE_INGOT.getItem().getItem(), SlimefunItems.ALUMINUM_BRONZE_INGOT.getItem().getItem(), SlimefunItems.ALUMINUM_BRONZE_INGOT.getItem().getItem(),
                    GEAR_WHEEL.getItem().clone(), IE_STORAGE_UNIT_1.getItem().clone(), GEAR_WHEEL.getItem().clone(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.SYNTHETIC_EMERALD.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_3 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_3", BCItemStacks.IE_STORAGE_UNIT_3),
            1_000_000,
            new ItemStack[]{
                    SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(),
                    GEAR_WHEEL.getItem().clone(), IE_STORAGE_UNIT_2.getItem().clone(), GEAR_WHEEL.getItem().clone(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_4 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_4", BCItemStacks.IE_STORAGE_UNIT_4),
            4_000_000,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.getItem().getItem(),
                    SlimefunItems.REDSTONE_ALLOY.getItem().getItem(), IE_STORAGE_UNIT_3.getItem().clone(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.SYNTHETIC_DIAMOND.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_5 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_5", BCItemStacks.IE_STORAGE_UNIT_5),
            64_000_000,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    SlimefunItems.REDSTONE_ALLOY.getItem().getItem(), IE_STORAGE_UNIT_4.getItem().clone(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.ANDROID_MEMORY_CORE.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_6 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_6", BCItemStacks.IE_STORAGE_UNIT_6),
            256_000_000,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.BLISTERING_INGOT.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    SlimefunItems.REDSTONE_ALLOY.getItem().getItem(), IE_STORAGE_UNIT_5.getItem().clone(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.ANDROID_MEMORY_CORE.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_7 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_7", BCItemStacks.IE_STORAGE_UNIT_7),
            1_000_000_000,
            new ItemStack[]{
                    SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.BLISTERING_INGOT_2.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(),
                    SlimefunItems.REDSTONE_ALLOY.getItem().getItem(), IE_STORAGE_UNIT_6.getItem().clone(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.ANDROID_MEMORY_CORE.getItem().getItem()
            });

    public static final IEStorageUnit IE_STORAGE_UNIT_8 = new IEStorageUnit(
            new SlimefunItemStack("BC_IE_STORAGE_UNIT_8", BCItemStacks.IE_STORAGE_UNIT_8),
            2_140_000_000,
            new ItemStack[]{
                    TIGHTLY_BLISTERING_INGOT.getItem().clone(), SlimefunItems.BLISTERING_INGOT_3.getItem().getItem(), TIGHTLY_BLISTERING_INGOT.getItem().clone(),
                    SlimefunItems.REDSTONE_ALLOY.getItem().getItem(), IE_STORAGE_UNIT_7.getItem().clone(), SlimefunItems.REDSTONE_ALLOY.getItem().getItem(),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.REINFORCED_PLATE.getItem().getItem(), SlimefunItems.ANDROID_MEMORY_CORE.getItem().getItem()
            });
    
    /**
     * Dev-16 constructed this unfinished item but never registered it. Keep an
     * inert compatibility instance so existing binary structure remains stable.
     */
    public static final P2PTransfer POINT_TO_POINT_TRANSFER = new P2PTransfer(
            new SlimefunItemStack("BC_POINT_TO_POINT_TRANSFER", BCItemStacks.POINT_TO_POINT_TRANSFER),
            RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[]{
                    new ItemStack(Material.IRON_BARS), SlimefunItems.ALUMINUM_INGOT.getItem().getItem(), new ItemStack(Material.IRON_BARS),
                    new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.CHEST), new ItemStack(Material.REDSTONE_BLOCK),
                    SlimefunItems.ELECTRIC_MOTOR.getItem().getItem(), SlimefunItems.HARDENED_METAL_INGOT.getItem().getItem(), SlimefunItems.SYNTHETIC_SAPPHIRE.getItem().getItem()
            });

    private BCItems() {
    }

    public static void registerItems() {
        TIP.register(BetterChests.INSTANCE);

        TIGHTLY_BLISTERING_INGOT.register(BetterChests.INSTANCE);
        TIGHTLY_BLISTERING_PLATE.register(BetterChests.INSTANCE);
        GEAR_WHEEL.register(BetterChests.INSTANCE);

        CHEST_DISASSEMBLER.register(BetterChests.INSTANCE);
        LOCATION_RECORDER.register(BetterChests.INSTANCE);
        CHEST_COLORER.register(BetterChests.INSTANCE);

        CHEST_27.register(BetterChests.INSTANCE);
        CHEST_36.register(BetterChests.INSTANCE);
        CHEST_45.register(BetterChests.INSTANCE);
        CHEST_54.register(BetterChests.INSTANCE);

        CHEST_INPUT_27.register(BetterChests.INSTANCE);
        CHEST_INPUT_36.register(BetterChests.INSTANCE);
        CHEST_INPUT_45.register(BetterChests.INSTANCE);
        CHEST_INPUT_54.register(BetterChests.INSTANCE);

        CHEST_OUTPUT_27.register(BetterChests.INSTANCE);
        CHEST_OUTPUT_36.register(BetterChests.INSTANCE);
        CHEST_OUTPUT_45.register(BetterChests.INSTANCE);
        CHEST_OUTPUT_54.register(BetterChests.INSTANCE);

        DRAWER_1.register(BetterChests.INSTANCE);
        DRAWER_2.register(BetterChests.INSTANCE);
        DRAWER_3.register(BetterChests.INSTANCE);
        DRAWER_4.register(BetterChests.INSTANCE);
        DRAWER_5.register(BetterChests.INSTANCE);
        DRAWER_6.register(BetterChests.INSTANCE);
        DRAWER_7.register(BetterChests.INSTANCE);
        DRAWER_8.register(BetterChests.INSTANCE);
        DRAWER_9.register(BetterChests.INSTANCE);
        DRAWER_10.register(BetterChests.INSTANCE);
        DRAWER_MAX.register(BetterChests.INSTANCE);

        IE_STORAGE_UNIT_1.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_2.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_3.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_4.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_5.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_6.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_7.register(BetterChests.INSTANCE);
        IE_STORAGE_UNIT_8.register(BetterChests.INSTANCE);
    }
}
