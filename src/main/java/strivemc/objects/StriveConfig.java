package strivemc.objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class StriveConfig {

    private final File f;
    private final YamlConfiguration y;
    private final Function<YamlConfiguration, ?> initial;

    public StriveConfig(Plugin plugin, String fileName, Function<YamlConfiguration, ?> initial)
    {
        this.f = new File(plugin.getDataFolder(), fileName + ".yml");
        this.y = YamlConfiguration.loadConfiguration(this.f);

        this.initial = initial;
    }
    public void load()
    {
        try
        {
            if (!this.f.exists())
            {
                this.initial.apply(this.y);
                this.y.save(this.f);
            }
            else
            {
                this.y.load(this.f);
            }
        }
        catch (IOException | InvalidConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void reload()
    {
        try
        {
            this.y.load(this.f);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }
    public String getString(String path)
    {
        return this.y.getString(path);
    }
    public int getInt(String path)
    {
        return this.y.getInt(path);
    }
    public boolean getBoolean(String path)
    {
        return this.y.getBoolean(path);
    }
    public List<String> getStringList(String path)
    {
        return this.y.getStringList(path);
    }
    public List<Byte> getByteList(String path)
    {
        return this.y.getByteList(path);
    }
    public List<Boolean> getBooleanList(String path)
    {
        return this.y.getBooleanList(path);
    }
    public double getDouble(String path)
    {
        return this.y.getDouble(path);
    }
    public List<Float> getFloatList(String path)
    {
        return this.y.getFloatList(path);
    }
    public ConfigurationSection getConfigurationSection(String path)
    {
        return this.y.getConfigurationSection(path);
    }
    public Object get(String path)
    {
        return this.y.get(path);
    }
}
