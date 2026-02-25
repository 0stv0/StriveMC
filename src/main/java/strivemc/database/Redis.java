package strivemc.database;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.net.URI;
import java.net.URISyntaxException;

public class Redis extends Database {

    private final String url;
    private final Gson gson;
    private JedisPool pool;

    public Redis(String url)
    {
        super();
        this.gson = new Gson();
        this.url  = url;
    }
    @Override
    public boolean connect()
    {
        if (this.pool != null && !this.pool.isClosed())
            return false;
        try
        {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(32);

            URI uri = new URI(this.url);
            this.pool = new JedisPool(config, uri);

            return true;
        }
        catch (JedisException | URISyntaxException e)
        {
            return false;
        }
    }
    @Override
    public boolean disconnect()
    {
        if (this.pool == null || this.pool.isClosed())
            return false;
        this.pool.close();
        return true;
    }
    @Override
    public boolean ping()
    {
        if (this.pool == null || this.pool.isClosed())
            return false;
        try (Jedis jedis = this.pool.getResource())
        {
            return "PONG".equalsIgnoreCase(jedis.ping());
        }
        catch (JedisException e)
        {
            return false;
        }
    }
    public <T> T get(String key, Class<T> clazz)
    {
        try (Jedis jedis = this.pool.getResource())
        {
            String data = jedis.get(key);
            if (data == null)
                return null;
            return (T) switch (clazz.getSimpleName())
            {
                case "String" -> data;
                case "Integer", "int" -> Integer.valueOf(data);
                case "Long", "long" -> Long.valueOf(data);
                case "Double", "double" -> Double.valueOf(data);
                case "Float", "float" -> Float.valueOf(data);
                case "Boolean", "boolean" -> Boolean.valueOf(data);
                default -> gson.fromJson(data, clazz);
            };
        }
        catch (JedisException e)
        {
            return null;
        }
    }
    public boolean set(String key, Object value)
    {
        try (Jedis jedis = this.pool.getResource())
        {
            String toSave = switch (value.getClass().getSimpleName())
            {
                case "String", "Integer", "Long", "Double", "Float", "Boolean" -> String.valueOf(value);
                default -> gson.toJson(value);
            };
            jedis.set(key, toSave);
            return true;
        }
        catch (JedisException e)
        {
            return false;
        }
    }
    public boolean exists(String key)
    {
        try (Jedis jedis = this.pool.getResource())
        {
            return jedis.exists(key);
        }
        catch (JedisException e)
        {
            return false;
        }
    }
    public boolean del(String key)
    {
        try (Jedis jedis = this.pool.getResource())
        {
            jedis.del(key);
            return true;
        }
        catch (JedisException e)
        {
            return false;
        }
    }
    public boolean expire(String key, long seconds)
    {
        try (Jedis jedis = this.pool.getResource())
        {
            return jedis.expire(key, seconds) == 1;
        }
        catch (JedisException e)
        {
            return false;
        }
    }
}
