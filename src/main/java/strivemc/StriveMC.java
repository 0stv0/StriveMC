package strivemc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import strivemc.events.EventsInit;
import strivemc.objects.StriveConfig;

public final class StriveMC extends JavaPlugin {

    public static StriveConfig settings;

    @Override
    public void onEnable()
    {
        this.loadConfigs();
        this.loadListeners();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "StriveMC enabled.");
    }
    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "StriveMC disabled.");
    }
    private void loadConfigs()
    {
        settings = new StriveConfig(
            this,
            "settings",
            (y) -> {
                y.set("Settings.cmd.no_perm", "&cYou are not allowed to use this command! &8(&7{PERM}&8)");
                y.set("Settings.cmd.only_player", "&cThis command is player only!");
                return null;
            }
        );
        settings.load();
    }
    private void loadListeners()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EventsInit(), this);
    }
}
