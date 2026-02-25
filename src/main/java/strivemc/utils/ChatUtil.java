package strivemc.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtil {

    public static String fixColor(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static List<String> fixColor(List<String> arr)
    {
        return arr.stream().map(ChatUtil::fixColor).toList();
    }
    public static void sendTitle(Player p, String title, String subtitle)
    {
        p.sendTitle(fixColor(title), fixColor(subtitle));
    }
    public static void sendActionbar(Player p, String content)
    {
        p.sendActionBar(fixColor(content));
    }
}
