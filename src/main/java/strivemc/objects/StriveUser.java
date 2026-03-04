package strivemc.objects;

import strivemc.StriveMC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StriveUser {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private long playtime;

    public StriveUser(UUID uuid)
    {
        this.uuid     = uuid;
        this.kills    = 0;
        this.deaths   = 0;
        this.playtime = 0;
        this.insert();
    }
    public StriveUser(ResultSet rs) throws SQLException
    {
        this.uuid     = UUID.fromString(rs.getString("uuid"));
        this.kills    = rs.getInt("kills");
        this.deaths   = rs.getInt("deaths");
        this.playtime = rs.getLong("playtime");
    }
    private void insert()
    {
        StriveMC.sqLite.connect();
        StriveMC.sqLite.execute("INSERT INTO strive_users (uuid, kills, deaths, playtime) VALUES (?, ?, ?, ?)",
            this.uuid.toString(),
            this.kills,
            this.deaths,
            this.playtime
        );
    }
    public void save()
    {
        StriveMC.sqLite.connect();
        StriveMC.sqLite.execute("UPDATE strive_users SET kills = ?, deaths = ?, playtime = ? WHERE uuid = ?",
            this.kills,
            this.deaths,
            this.playtime,
            this.uuid.toString()
        );
    }
    public UUID getUuid()
    {
        return this.uuid;
    }
    public int getKills()
    {
        return this.kills;
    }
    public int getDeaths()
    {
        return this.deaths;
    }
    public long getPlaytime()
    {
        return this.playtime;
    }
    public void addKill()
    {
        this.kills += 1;
    }
    public void addDeath()
    {
        this.deaths += 1;
    }
    public void addPlaytime(long time)
    {
        this.playtime += time;
    }
}
