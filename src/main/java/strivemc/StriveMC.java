package strivemc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import strivemc.database.SQLite;
import strivemc.events.EventsInit;
import strivemc.listeners.StriveUserListener;
import strivemc.objects.Manager;
import strivemc.objects.StriveConfig;
import strivemc.objects.StriveUser;
import strivemc.tasks.SaveTask;

import java.util.ArrayList;
import java.util.List;

public final class StriveMC extends JavaPlugin {

    public static StriveConfig settings;
    public static SQLite sqLite;
    public static Manager<StriveUser> userManager;

    @Override
    public void onEnable()
    {
        this.loadConfigs();
        this.loadDatabase();
        this.loadListeners();
        new SaveTask(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "StriveMC enabled.");
    }
    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "StriveMC disabled.");
    }
    private void loadDatabase()
    {
        sqLite = new SQLite(this, "users.db");
        sqLite.execute("CREATE TABLE IF NOT EXISTS strive_users (uuid TEXT, kills INTEGER, deaths INTEGER, playtime INTEGER)");
        userManager = new Manager<>(
            StriveUser::getUuid,
            () -> {
                List<StriveUser> users = new ArrayList<>();
                sqLite.query("SELECT * FROM strive_users", (rs) -> {
                    users.add(new StriveUser(rs));
                });
                return users;
            }
        ).load();
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
        pm.registerEvents(new StriveUserListener(), this);
    }
}
