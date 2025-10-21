package me.deadybbb.mysty.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.Equippable;
import me.deadybbb.ybmj.PluginProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Objects;

public abstract class CustomItem {
    protected final PluginProvider plugin;
    protected final String name;
    protected final Material material;
    protected final NamespacedKey key;
    protected final NamespacedKey DEFAULT_MODEL;
    protected ItemStack item;
    protected static final CustomModelData CMD = CustomModelData.customModelData().addString("mysty").build();

    public CustomItem(PluginProvider plugin, Material material, String name, String key) {
        this.plugin = plugin;
        this.name = name;
        this.material = material;
        this.key = new NamespacedKey(plugin, key);
        DEFAULT_MODEL = new NamespacedKey("mysty", key);
    }

    public ItemStack register() {
        item = ItemStack.of(material);
        configureItem();
        plugin.getServer().addRecipe(createRecipe());
        return item;
    }

    protected void configureItem() {
        item.setData(DataComponentTypes.ITEM_NAME, Component.text(name));
        item.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
        item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CMD);
        item.setData(DataComponentTypes.ITEM_MODEL, DEFAULT_MODEL);
    }

    protected abstract Recipe createRecipe();

    public ItemStack getItem() {
        return item;
    }

    public boolean isItemEqual(ItemStack other) {
        if (other == null || item == null) return false;

        if (other.getType() != item.getType()) return false;

        Component otherName = other.getData(DataComponentTypes.ITEM_NAME);
        Component thisName = item.getData(DataComponentTypes.ITEM_NAME);
        if (!Objects.equals(otherName, thisName)) return false;

        CustomModelData otherCmd = other.getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        CustomModelData thisCmd = item.getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (!Objects.equals(otherCmd, thisCmd)) return false;

        return true;
    }
}
