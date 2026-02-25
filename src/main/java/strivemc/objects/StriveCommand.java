package strivemc.objects;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import strivemc.utils.ChatUtil;

import java.util.List;

public abstract class StriveCommand implements CommandExecutor {

    public abstract boolean handler(CommandSender sender, String[] args);

    private final String perm;
    private final boolean onlyPlayer;

    public StriveCommand()
    {
        if (!this.getClass().isAnnotationPresent(CommandInfo.class))
            throw new NoCommandInfoException(this.getClass().getName());
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        this.perm        = info.perm();
        this.onlyPlayer  = info.onlyPlayer();
    }
    public boolean check(CommandSender sender, String[] args)
    {
        if (!(sender instanceof Player) && this.onlyPlayer)
        {
            sender.sendMessage("Command is player-only!");
            return true;
        }
        if (sender instanceof Player p)
            if (this.perm != null && !p.hasPermission(this.perm))
            {
                p.sendMessage(ChatUtil.fixColor("TODO CONFIG NO PERM MSG"));
                return true;
            }
        return this.handler(sender, args);
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args)
    {
        return this.check(sender, args);
    }
}
