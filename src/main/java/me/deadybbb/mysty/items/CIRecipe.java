package me.deadybbb.mysty.items;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CIRecipe {
    private final String name;
    private String[] rows;
    private Map<Character, RecipeChoice> ingredients = new HashMap<>();

    public CIRecipe(String name) {
        this.name = name;
    }

    public void recipe(@NotNull final String... shape) {
        Preconditions.checkArgument(shape != null, "Must provide a shape");
        Preconditions.checkArgument(shape.length > 0 && shape.length < 4, "Crafting recipes should be 1, 2 or 3 rows, not ", shape.length);

        int lastLen = -1;
        for (String row : shape) {
            Preconditions.checkArgument(row != null, "Shape cannot have null rows");
            Preconditions.checkArgument(row.length() > 0 && row.length() < 4, "Crafting rows should be 1, 2, or 3 characters, not ", row.length());

            Preconditions.checkArgument(lastLen == -1 || lastLen == row.length(), "Crafting recipes must be rectangular");
            lastLen = row.length();
        }
        this.rows = new String[shape.length];
        for (int i = 0; i < shape.length; i++) {
            this.rows[i] = shape[i];
        }

        // Remove character mappings for characters that no longer exist in the shape
        HashMap<Character, RecipeChoice> newIngredients = new HashMap<>();
        for (String row : shape) {
            for (char c : row.toCharArray()) {
                // SPIGOT-7770: Space in recipe shape must represent no ingredient
                if (c == ' ') {
                    continue;
                }

                newIngredients.put(c, ingredients.get(c));
            }
        }
        this.ingredients = newIngredients;
    }

    public void setIngredient(char key, @NotNull RecipeChoice ingredient) {
        Preconditions.checkArgument(key != ' ', "Space in recipe shape must represent no ingredient");
        Preconditions.checkArgument(ingredients.containsKey(key), "Symbol does not appear in the shape:", key);

        ingredients.put(key, ingredient.validate(false).clone()); // Paper
    }

    public String getName() {
        return name;
    }

    public String[] getShape() {
        return rows;
    }

    public Map<Character, RecipeChoice> getIngredients() {
        return ingredients;
    }
}
