package me.deadybbb.mysty.items;

import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CIB {
    private final Material material;
    private final NamespacedKey key;
    private final String name;
    private List<CIRecipe> recipes = new ArrayList<>();
    private Map<String, NamespacedKey> models = new HashMap<>();

    public CIB(PluginProvider plugin, Material material, String key, String name) {
        this.material = material;
        this.name = name;
        this.key = new NamespacedKey(plugin, key);
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public void addRecipe(CIRecipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(String name) {
        this.recipes = recipes.stream().filter(r -> !r.getName().equals(name)).toList();
    }

    public List<CIRecipe> getRecipes() {
        return recipes;
    }

    public void addModelKey(String name, NamespacedKey key) {
        models.put(name, key);
    }

    public void removeModelKey(String name) {
        models.remove(name);
    }

    public void getModelKey(String name) {
        models.get(name);
    }
}
