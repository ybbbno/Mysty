package me.deadybbb.mysty.items;

import me.deadybbb.mysty.items.realisation.*;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftRegistry {
    private static final List<CustomItem> items = new ArrayList<>();

    public static void registerItems(PluginProvider plugin) {
        items.add(new Respirator(plugin));
        items.add(new Filter(plugin));
        items.add(new ChemicalProtHelmet(plugin));
        items.add(new ChemicalProtChestplate(plugin));
        items.add(new ChemicalProtLeggings(plugin));
        items.add(new ChemicalProtBoots(plugin));

        for (CustomItem item : items) {
            item.register();
        }
    }

    public static ItemStack getItem(String key) {
        return items.stream()
                .filter(item -> item.key.getKey().equals(key))
                .findFirst()
                .map(CustomItem::getItem)
                .orElse(null);
    }

    public static boolean isItemEqual(ItemStack item, String key) {
        CustomItem customItem = items.stream()
                .filter(i -> i.key.getKey().equals(key))
                .findFirst()
                .orElse(null);
        return customItem != null && customItem.isItemEqual(item);
    }
}
