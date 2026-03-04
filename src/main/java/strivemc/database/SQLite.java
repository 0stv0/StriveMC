package strivemc.database;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLite extends Database {

    private final String name;
    private final File file;
    private Connection con;

    public SQLite(Plugin plugin, String name)
    {
        this.name = name;
        this.file = new File(plugin.getDataFolder(), this.name);
        try
        {
            if (!this.file.exists())
                this.file.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean connect()
    {
        if (this.con != null)
            return false;
        try
        {
            String url = "jdbc:sqlite:" + this.file.getAbsolutePath();
            this.con = DriverManager.getConnection(url);
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    @Override
    public boolean disconnect()
    {
        if (this.con == null)
            return false;
        try
        {
            this.con.close();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    @Override
    public boolean ping()
    {
        try
        {
            return this.con.isValid(1);
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    @FunctionalInterface
    public interface Handler
    {
        void handle(ResultSet rs) throws SQLException;
    }
    public boolean query(String sql, MySQL.Handler handler, Object... values)
    {
        try (PreparedStatement sp = this.con.prepareStatement(sql))
        {
            for (int i = 0; i < values.length; i++)
                sp.setObject(i + 1, values[i]);
            try (ResultSet rs = sp.executeQuery())
            {
                while (rs.next())
                    handler.handle(rs);
            }
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    public boolean execute(String sql, Object... values)
    {
        try (PreparedStatement sp = this.con.prepareStatement(sql))
        {
            for (int i = 0; i < values.length; i++)
                sp.setObject(i + 1, values[i]);
            sp.execute();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
}
