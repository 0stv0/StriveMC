package strivemc.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventsInit implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e)
    {
        Player v = e.getPlayer();
        if (v.getKiller() instanceof Player k)
            Bukkit.getPluginManager().callEvent(new PlayerKillEvent(k, v));
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();

        Location from = e.getFrom();
        Location to   = e.getTo();

        if (
            from.getX() != to.getX() ||
            from.getY() != to.getY() ||
            from.getZ() != to.getZ()
        )
            Bukkit.getPluginManager().callEvent(new PlayerTravelEvent(p, e.getFrom(), e.getTo()));
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack is = e.getItem();
        if (is == null)
            return;

        Material mat = is.getType();
        ItemMeta im  = is.getItemMeta();
        if (im == null)
            return;
        String title = null;
        if (im.hasDisplayName())
            title = im.getDisplayName();

        ItemInteractEvent event = new ItemInteractEvent(p, mat, title, im.getLore(), im.getItemFlags(), im.getEnchants(), e.getAction());

        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }
}
