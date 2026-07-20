package me.mmmjjkx.betterChests.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class ItemStackBuilder {
    private final ItemStack itemStack;

    public ItemStackBuilder(Material material, Consumer<ItemMeta> metaConsumer) {
        itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        metaConsumer.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStackBuilder(ItemStack itemStack, Consumer<ItemMeta> metaConsumer) {
        this.itemStack = itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        metaConsumer.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStackBuilder(Material material, String displayName, String... lore) {
        itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Stream.of(lore).toList());
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStackBuilder(Material material, String displayName, List<String> lore) {
        this.itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
}
