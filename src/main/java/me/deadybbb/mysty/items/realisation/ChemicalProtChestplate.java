package me.deadybbb.mysty.items.realisation;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;

public class ChemicalProtChestplate extends CustomItem {
    public ChemicalProtChestplate(PluginProvider plugin) {
        super(plugin, Material.LEATHER_CHESTPLATE, "Верх скафандра", "chemicalprotchestplate");
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        item.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey("mysty", "gagarin_chest"));
        item.setData(DataComponentTypes.EQUIPPABLE, Equippable.equippable(EquipmentSlot.CHEST).assetId(new NamespacedKey("mysty", "gagarin_chest")).build());
    }

    @Override
    protected ShapedRecipe createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("L L", "YHY", "LML");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.LEATHER_CHESTPLATE);
        recipe.setIngredient('Y', Material.YELLOW_DYE);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
        return recipe;
    }
}