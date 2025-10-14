package me.deadybbb.mysty.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BlockItemDataProperties;
import io.papermc.paper.datacomponent.item.BundleContents;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.ItemContainerContents;
import me.deadybbb.ybmj.PluginProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.block.Container;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftRegistry {
    public static ItemStack respirator;
    public static ItemStack filter;
    public static ItemStack chemicalProtHelmet;
    public static ItemStack chemicalProtChestplate;
    public static ItemStack chemicalProtLeggings;
    public static ItemStack chemicalProtBoots;

    private static CustomModelData cmd = CustomModelData.customModelData().addString("mysty").build();

    public static void registerItems(PluginProvider plugin) {
        respirator = registerRespirator(plugin);
        filter = registerFilter(plugin);
        chemicalProtHelmet = registerChemicalProtHelmet(plugin);
        chemicalProtChestplate = registerChemicalProtChestplate(plugin);
        chemicalProtLeggings = registerChemicalProtLeggings(plugin);
        chemicalProtBoots = registerChemicalProtBoots(plugin);
    }

    public static ItemStack registerRespirator(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "respirator");

        ItemStack item = ItemStack.of(Material.CARVED_PUMPKIN);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Респиратор"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);
        item.setData(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents().build());

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SCS", "GPG", "WWW");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('C', Material.CHAIN);
        recipe.setIngredient('G', Material.GRAY_DYE);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);
        recipe.setIngredient('W', Material.GRAY_WOOL);

        plugin.getServer().addRecipe(recipe);
        return item;
    }

    public static ItemStack registerFilter(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "filter");

        ItemStack item = ItemStack.of(Material.STICK);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Фильтр"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" I ", "IPI", " I ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('P', Material.PAPER);

        plugin.getServer().addRecipe(recipe);
        return item;
    }

    public static ItemStack registerChemicalProtHelmet(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "chemicalprothelmet");

        ItemStack item = ItemStack.of(Material.CARVED_PUMPKIN);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Головной комплект химзащиты"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("LRL", "YHY", "L L");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('R', respirator);
        recipe.setIngredient('H', Material.LEATHER_HELMET);
        recipe.setIngredient('Y', Material.YELLOW_DYE);

        plugin.getServer().addRecipe(recipe);
        return item;
    }

    public static ItemStack registerChemicalProtChestplate(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "chemicalprotchestplate");

        ItemStack item = ItemStack.of(Material.LEATHER_CHESTPLATE);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Верх скафандра"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("L L", "YHY", "LML");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.LEATHER_CHESTPLATE);
        recipe.setIngredient('Y', Material.YELLOW_DYE);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);

        plugin.getServer().addRecipe(recipe);
        return item;
    }

    public static ItemStack registerChemicalProtLeggings(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "chemicalprotleggings");

        ItemStack item = ItemStack.of(Material.LEATHER_LEGGINGS);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Низ скафандра"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("LML", "YHY", "L L");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.LEATHER_LEGGINGS);
        recipe.setIngredient('Y', Material.YELLOW_DYE);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);

        plugin.getServer().addRecipe(recipe);
        return item;
    }

    public static ItemStack registerChemicalProtBoots(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "chemicalprotboots");

        ItemStack item = ItemStack.of(Material.CHAINMAIL_BOOTS);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Ботинки скафандра"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, cmd);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("M M", "YHY", "L L");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.LEATHER_BOOTS);
        recipe.setIngredient('Y', Material.YELLOW_DYE);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);

        plugin.getServer().addRecipe(recipe);
        return item;
    }
}
