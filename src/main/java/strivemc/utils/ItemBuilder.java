package strivemc.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack is;
    private ItemMeta im;

    public ItemBuilder(Material mat, int amount)
    {
        this.is = new ItemStack(mat, amount);
        this.im = this.is.getItemMeta();
    }
    public ItemBuilder(Material mat)
    {
        this.is = new ItemStack(mat);
        this.im = this.is.getItemMeta();
    }
    public ItemBuilder setTitle(String title)
    {
        this.im.setDisplayName(ChatUtil.fixColor(title));
        return this;
    }
    public ItemBuilder setLore(List<String> lore)
    {
        this.im.setLore(ChatUtil.fixColor(lore));
        return this;
    }
    public ItemBuilder setLore(String... lore)
    {
        this.im.setLore(ChatUtil.fixColor(Arrays.asList(lore)));
        return this;
    }
    public ItemBuilder addEnchant(Enchantment ench, int lvl)
    {
        this.im.addEnchant(ench, lvl, true);
        return this;
    }
    public ItemBuilder addFlags(ItemFlag... flags)
    {
        this.im.addItemFlags(flags);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean unbreakable)
    {
        this.im.setUnbreakable(unbreakable);
        return this;
    }
    public ItemStack build()
    {
        this.is.setItemMeta(this.im);
        return this.is;
    }
}
