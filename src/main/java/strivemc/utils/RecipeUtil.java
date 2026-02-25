package strivemc.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class RecipeUtil {

    private static final char[] CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

    public static void register(Plugin plugin, String id, Map<Integer, Material> ingredients, ItemStack result)
    {
        NamespacedKey key = new NamespacedKey(plugin, id);
        if (plugin.getServer().getRecipe(key) != null)
            return;
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        char[] mask         = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ingredients.forEach((Integer slot, Material type) -> {
            recipe.setIngredient(CHARS[slot], type);
            mask[slot] = CHARS[slot];
        });
        recipe.shape(
            new String(mask, 0, 3),
            new String(mask, 3, 3),
            new String(mask, 6, 3)
        );
        plugin.getServer().addRecipe(recipe);
    }
}
