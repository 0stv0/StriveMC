package strivemc.utils;

import org.bukkit.entity.Player;
import strivemc.objects.MultiMap;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownUtil {

    private static final Map<String, MultiMap<UUID, Date>> cooldowns = new ConcurrentHashMap<>();

    public static void set(Player p, String cooldown, int seconds)
    {
        long exp     = System.currentTimeMillis() + (seconds * 1000L);
        Date expDate = new Date(exp);

        MultiMap<UUID, Date> map = cooldowns.get(cooldown) == null ? new MultiMap<>() : cooldowns.get(cooldown);
        map.set(p.getUniqueId(), List.of(new Date(), expDate));
        cooldowns.put(cooldown, map);
    }
    public static boolean has(Player p, String cooldown)
    {
        MultiMap<UUID, Date> map = cooldowns.get(cooldown);
        if (map == null)
            return false;

        List<Date> dates = map.get(p.getUniqueId());
        if (dates == null)
            return false;
        if (dates.size() != 2)
        {
            map.del(p.getUniqueId());
            cooldowns.put(cooldown, map);
            return false;
        }
        Date expDate = dates.getLast();
        if (expDate.before(new Date()))
        {
            map.del(p.getUniqueId());
            cooldowns.put(cooldown, map);
            return false;
        }
        return true;
    }
    public static int secondsLeft(Player p, String cooldown)
    {
        if (!has(p, cooldown))
            return 0;
        MultiMap<UUID, Date> map = cooldowns.get(cooldown);
        List<Date> dates         = map.get(p.getUniqueId());

        Date expDate = dates.getLast();
        long now     = System.currentTimeMillis();
        long expire  = expDate.getTime();

        long diff = expire - now;
        if (diff <= 0)
            return 0;

        return (int) Math.ceil(diff / 1000.0);
    }
    public static Date getIssued(Player p, String cooldown)
    {
        if (!has(p, cooldown))
            return null;
        MultiMap<UUID, Date> map = cooldowns.get(cooldown);
        List<Date> dates         = map.get(p.getUniqueId());
        return dates.getFirst();
    }
}
