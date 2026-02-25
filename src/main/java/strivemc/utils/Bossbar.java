package strivemc.utils;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Bossbar {

    public static void send(Plugin plugin, Player player, String title, BarColor color, BarStyle style, double progress, int seconds)
    {
        BossBar bar = plugin.getServer().createBossBar(title, color, style);
        bar.setProgress(progress);
        bar.addPlayer(player);
        Async.fireForget(plugin, () -> {
            try
            {
                Thread.sleep(seconds * 1000L);
                bar.removePlayer(player);
                bar.removeAll();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
