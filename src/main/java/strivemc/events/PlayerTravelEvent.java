package strivemc.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerTravelEvent extends StriveEvent {

    private final Player player;
    private final Location from;
    private final Location to;

    public PlayerTravelEvent(Player player, Location from, Location to)
    {
        this.player = player;
        this.from   = from;
        this.to     = to;
    }
    public Player getPlayer()
    {
        return this.player;
    }
    public Location getFrom()
    {
        return this.from;
    }
    public Location getTo()
    {
        return this.to;
    }
    public double getDistance()
    {
        double dx = this.to.getX() - this.from.getX();
        double dy = this.to.getY() - this.from.getY();
        double dz = this.to.getZ() - this.from.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
