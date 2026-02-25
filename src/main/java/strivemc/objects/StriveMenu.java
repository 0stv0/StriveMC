package strivemc.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import strivemc.utils.ChatUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class StriveMenu implements Listener {

    public abstract void preload(Inventory inv);
    public abstract void close(Player p);
    public abstract void click(InventoryClickEvent e);

    private final int slots;
    private final Inventory inv;
    private final String title;
    private final Map<Integer, Function<InventoryClickEvent, Boolean>> buttons;

    public StriveMenu(int slots, String title)
    {
        this.slots   = slots;
        this.title   = title;
        this.buttons = new ConcurrentHashMap<>();
        this.inv     = Bukkit.createInventory(null, this.slots, ChatUtil.fixColor(this.title));
    }
    public void createButton(int slot, Function<InventoryClickEvent, Boolean> button)
    {
        this.buttons.put(slot, button);
    }
    public InventoryView open(Player p)
    {
        this.preload(this.inv);
        return p.openInventory(this.inv);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e)
    {
        if (e.getClickedInventory() == null)
            return;
        if (e.getInventory().equals(this.inv))
        {
            this.click(e);
            if (this.buttons.get(e.getRawSlot()) != null)
            {
                boolean expectClose = this.buttons.get(e.getRawSlot()).apply(e);
                if (expectClose)
                    e.getWhoClicked().closeInventory();
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent e)
    {
        if (e.getInventory().equals(this.inv))
            this.close((Player) e.getPlayer());
    }
    public void load(Plugin plugin)
    {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(this, plugin);
    }
}
