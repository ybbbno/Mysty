package me.deadybbb.mysty.items.instances;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;

public class ChemicalProtHelmet extends CustomItem {
    public ChemicalProtHelmet(PluginProvider plugin) {
        super(plugin, Material.STICK, "Головной комплект термозащиты", "haz_mask");
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        item.setData(DataComponentTypes.EQUIPPABLE, Equippable.equippable(EquipmentSlot.HEAD).build());
    }

    @Override
    protected ShapedRecipe createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("LML", "YHY", "L L");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('H', Material.LEATHER_HELMET);
        recipe.setIngredient('Y', Material.ORANGE_DYE);
        return recipe;
    }
}