package strivemc.serializers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {

    public static String fromLoc(Location loc)
    {
        return loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ();
    }
    public static Location toLoc(String data)
    {
        String[] parts = data.split(";");
        World world    = Bukkit.getWorld(parts[0]);
        int x          = Integer.parseInt(parts[1]);
        int y          = Integer.parseInt(parts[2]);
        int z          = Integer.parseInt(parts[3]);
        return new Location(world, x, y, z);
    }
}
