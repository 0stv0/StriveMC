package strivemc.utils;

import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;

public class Cron {

    public static void schedule(Plugin plugin, int hour, int minute, Runnable task)
    {
        Async.fireForget(plugin, () -> {
            while (plugin.isEnabled())
            {
                if (isTime(hour, minute))
                {
                    task.run();
                    try
                    {
                        Thread.sleep(61000L);
                    }
                    catch (InterruptedException e)
                    {
                        break;
                    }
                }
                try
                {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
        });
    }
    private static boolean isTime(int hour, int minute)
    {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() == hour && now.getMinute() == minute;
    }
}
