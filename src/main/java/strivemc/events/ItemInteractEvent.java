package strivemc.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemInteractEvent extends StriveEvent {

    private final Player player;
    private final Material type;
    private final String title;
    private final List<String> lore;
    private final Set<ItemFlag> flags;
    private final Map<Enchantment, Integer> enchants;
    private final Action action;
    private boolean cancelled;

    public ItemInteractEvent(
        Player player,
        Material type,
        String title,
        List<String> lore,
        Set<ItemFlag> flags,
        Map<Enchantment, Integer> enchants,
        Action action
    )
    {
        this.player   = player;
        this.type     = type;
        this.title    = title;
        this.lore     = lore;
        this.flags    = flags;
        this.enchants = enchants;
        this.action   = action;
    }
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }
    public boolean isCancelled()
    {
        return this.cancelled;
    }
    public Material getType()
    {
        return this.type;
    }
    public String getTitle()
    {
        return this.title;
    }
    public List<String> getLore()
    {
        return this.lore;
    }
    public Set<ItemFlag> getFlags()
    {
        return this.flags;
    }
    public Map<Enchantment, Integer> getEnchants()
    {
        return this.enchants;
    }
    public Action getAction()
    {
        return this.action;
    }
    public Player getPlayer()
    {
        return this.player;
    }
}
