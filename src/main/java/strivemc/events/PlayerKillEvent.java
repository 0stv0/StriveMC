package strivemc.events;

import org.bukkit.entity.Player;

public class PlayerKillEvent extends StriveEvent {

    private final Player killer;
    private final Player victim;

    public PlayerKillEvent(Player killer, Player victim)
    {
        this.killer = killer;
        this.victim = victim;
    }
    public Player getKiller()
    {
        return this.killer;
    }
    public Player getVictim()
    {
        return this.victim;
    }
}
