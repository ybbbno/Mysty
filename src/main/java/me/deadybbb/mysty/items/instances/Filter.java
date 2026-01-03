package me.deadybbb.mysty.items.instances;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.deadybbb.mysty.items.CustomItem;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class Filter extends CustomItem {
    private int max_durability;
    private int durability;

    public Filter(PluginProvider plugin) {
        super(plugin, Material.STICK, "Фильтр", "filter");
    }

    public boolean setMaxDurability(int max) {
        try {
            this.max_durability = max;
            int max_damage = (int) Math.ceil(max*3.125);
            item.setData(DataComponentTypes.MAX_DAMAGE, max_damage);
            return true;
        } catch (Exception ex) {
            plugin.logger.warning("Failed to set max durability for filter: "+ex);
            return false;
        }
    }

    public boolean setDurability(int durability) {
        try {
            if (durability > max_durability) {
                plugin.logger.warning("Set durability for filter is higher than maximum");
                return false;
            }

            this.durability = durability;
            int damage = (int) Math.ceil((max_durability-durability)*3.125);
            item.setData(DataComponentTypes.DAMAGE, damage);

            return true;
        } catch (Exception ex) {
            plugin.logger.warning("Failed to set durability for filter: "+ex);
            return false;
        }
    }

    @Override
    protected void configureItem() {
        super.configureItem();
        this.setMaxDurability(32);
        this.setDurability(32);
    }

    protected void configureItem(int max_durability) {
        super.configureItem();
        this.setMaxDurability(max_durability);
        this.setDurability(max_durability);
    }

    protected void configureItem(int max_durability, int durability) {
        super.configureItem();
        this.setMaxDurability(max_durability);
        this.setDurability(durability);
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
