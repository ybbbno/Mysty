package me.deadybbb.mysty.items.instances;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;

public class ChemicalProtBoots extends CustomItem {
    public ChemicalProtBoots(PluginProvider plugin) {
        super(plugin, Material.LEATHER_BOOTS, "Ботинки термозащиты", "chemicalprotboots");
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        item.setData(DataComponentTypes.ITEM_MODEL, new NamespacedKey("mysty", "gagarin_boots"));
        item.setData(DataComponentTypes.EQUIPPABLE, Equippable.equippable(EquipmentSlot.FEET).assetId(new NamespacedKey("mysty", "gagarin_boots")).build());
    }


    @Override
    protected ShapedRecipe createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("M M", "YHY", "L L");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('H', Material.LEATHER_BOOTS);
        recipe.setIngredient('Y', Material.ORANGE_DYE);
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
        return recipe;
    }
}