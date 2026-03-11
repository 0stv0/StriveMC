package strivemc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import strivemc.StriveMC;
import strivemc.events.PlayerKillEvent;
import strivemc.objects.StriveUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StriveUserListener implements Listener {

    private static final Map<UUID, Date> times = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e)
    {
        Player p     = e.getPlayer();
        StriveUser u = StriveMC.userManager.get(p.getUniqueId());
        if (u == null)
        {
            u = new StriveUser(p.getUniqueId());
            StriveMC.userManager.add(u);
        }
        times.put(p.getUniqueId(), new Date());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e)
    {
        Player p     = e.getPlayer();
        StriveUser u = StriveMC.userManager.get(p.getUniqueId());
        if (u == null)
            return;
        if (times.get(p.getUniqueId()) == null)
            return;
        Date now     = new Date();
        long diff    = now.getTime() - times.get(p.getUniqueId()).getTime();
        long seconds = diff / 1000;
        u.addPlaytime(seconds);
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onKill(PlayerKillEvent e)
    {
        Player v = e.getVictim();
        Player k = e.getKiller();

        StriveUser uv = StriveMC.userManager.get(v.getUniqueId());
        StriveUser uk = StriveMC.userManager.get(k.getUniqueId());

        if (uv != null)
            uv.addDeath();
        if (uk != null)
            uk.addKill();
    }
}
