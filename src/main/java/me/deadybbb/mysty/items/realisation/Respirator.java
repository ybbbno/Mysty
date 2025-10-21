package me.deadybbb.mysty.items.realisation;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Equippable;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class Respirator extends CustomItem {
    private static final NamespacedKey MODEL_0 = new NamespacedKey("mysty", "resp");
    private static final NamespacedKey MODEL_1 = new NamespacedKey("mysty", "resp1");
    private static final NamespacedKey MODEL_2 = new NamespacedKey("mysty", "resp2");

    public Respirator(PluginProvider plugin) {
        super(plugin, Material.BUNDLE, "Респиратор", "resp");
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        item.setData(DataComponentTypes.EQUIPPABLE, Equippable.equippable(EquipmentSlot.HEAD).build());
        item.setData(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP);
        item.setData(DataComponentTypes.ITEM_MODEL, MODEL_2);
    }

    @Override
    protected ShapedRecipe createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("SCS", "GPG", "WWW");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('C', Material.CHAIN);
        recipe.setIngredient('G', Material.GRAY_DYE);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);
        recipe.setIngredient('W', Material.GRAY_WOOL);
        return recipe;
    }

    public static NamespacedKey getModel0() {
        return MODEL_0;
    }

    public static NamespacedKey getModel1() {
        return MODEL_1;
    }

    public static NamespacedKey getModel2() {
        return MODEL_2;
    }
}
