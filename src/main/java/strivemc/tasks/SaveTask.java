package strivemc.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import strivemc.StriveMC;
import strivemc.objects.StriveUser;
import strivemc.utils.Async;

public class SaveTask extends BukkitRunnable {

    private final Plugin plugin;
    public SaveTask(Plugin plugin)
    {
        this.plugin = plugin;
        this.runTaskTimer(this.plugin, 0L, 20L * 120);
    }
    @Override
    public void run()
    {
        Async.fireForget(this.plugin, () ->
        {
            for (Player t : Bukkit.getOnlinePlayers())
            {
                if (t == null)
                    continue;
                StriveUser u = StriveMC.userManager.get(t.getUniqueId());
                if (u == null)
                    continue;
                u.save();
            }
        });
    }
}
