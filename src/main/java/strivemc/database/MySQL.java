package strivemc.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class MySQL extends Database {

    private HikariDataSource dataSource;
    private final String host, user, data, pass;
    private final int port;

    public MySQL(String host, int port, String user, String pass, String data)
    {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.data = data;
    }
    @Override
    public boolean connect()
    {
        if (this.dataSource != null && !this.dataSource.isClosed())
            return false;
        try
        {
            String URL          = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.data;
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(URL);
            config.setUsername(this.user);
            config.setPassword(this.pass);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(5000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");

            this.dataSource = new HikariDataSource(config);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @Override
    public boolean disconnect()
    {
        if (this.dataSource == null || this.dataSource.isClosed())
            return false;
        this.dataSource.close();
        return true;
    }
    private Connection getCon()
    {
        try
        {
            return this.dataSource.getConnection();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean ping()
    {
        try (Connection con = this.getCon())
        {
            return con.isValid(1);
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
    public boolean query(String sql, Handler handler, Object... values)
    {
        try (Connection con = this.getCon(); PreparedStatement sp = con.prepareStatement(sql))
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
        try (Connection con = this.getCon(); PreparedStatement sp = con.prepareStatement(sql))
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
