package strivemc.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SidebarUtil {

    public static void display(Player p, String title, String... lines)
    {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o   = sb.registerNewObjective("dummy", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatUtil.fixColor(title));

        for (int i = 0; i < lines.length; i++)
            o.getScore(lines[i]).setScore(lines.length - 1 - i);

        p.setScoreboard(sb);
    }
}
