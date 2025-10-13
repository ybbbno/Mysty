package me.deadybbb.mysty.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BlockItemDataProperties;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.deadybbb.ybmj.PluginProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftRegistry {

    public static void registerItems(PluginProvider plugin) {
        registerRespirator(plugin);
    }

    public static void registerRespirator(PluginProvider plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "respirator");

        ItemStack item = ItemStack.of(Material.CARVED_PUMPKIN);
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Респиратор"));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.BLOCK_DATA, BlockItemDataProperties.blockItemStateProperties().build());

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SCS", "GPG", "WWW");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('C', Material.CHAIN);
        recipe.setIngredient('G', Material.GRAY_DYE);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);
        recipe.setIngredient('W', Material.GRAY_WOOL);

        plugin.getServer().addRecipe(recipe);
    }
}
