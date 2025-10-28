package me.deadybbb.mysty.items.instances;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class Filter extends CustomItem {
    public Filter(PluginProvider plugin) {
        super(plugin, Material.STICK, "Фильтр", "filter");
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        item.setData(DataComponentTypes.MAX_DAMAGE, 100);
        item.setData(DataComponentTypes.DAMAGE, 0);
    }

    @Override
    protected ShapedRecipe createRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" I ", "IPI", " I ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('P', Material.PAPER);
        return recipe;
    }
}
