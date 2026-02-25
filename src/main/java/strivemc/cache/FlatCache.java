package strivemc.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.Plugin;
import strivemc.objects.MultiMap;
import strivemc.utils.Async;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public class FlatCache<T, K> {

    protected final MultiMap<T, K> cache = new MultiMap<>();
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Plugin plugin;

    public FlatCache(Plugin plugin)
    {
        if (!this.getClass().isAnnotationPresent(FlatCacheInfo.class))
            throw new NoFlatCacheInfoException(this.getClass().getName());

        String name = this.getClass().getAnnotation(FlatCacheInfo.class).fileName();
        this.file   = new File(plugin.getDataFolder() + "/cache", name + ".json");
        this.plugin = plugin;

        if (!this.file.getParentFile().exists())
            this.file.getParentFile().mkdirs();
        this.load();
    }
    public void add(T key, K value)
    {
        this.cache.add(key, value);
    }
    public void set(T key, List<K> value)
    {
        this.cache.set(key, value);
    }
    public List<K> get(T key)
    {
        return this.cache.get(key);
    }
    public void save()
    {
        Async.fireForget(this.plugin, () -> {
            File temp = new File(this.file.getParentFile(), this.file.getName() + ".tmp");
            try
            {
                try (FileWriter writer = new FileWriter(temp))
                {
                    this.gson.toJson(this.cache.getInternal(), writer);
                }
                Path source = temp.toPath();
                Path target = this.file.toPath();
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
    private void load()
    {
        if (!this.file.exists())
            try
            {
                this.file.createNewFile();
                try (FileWriter writer = new FileWriter(this.file))
                {
                    writer.write("{}");
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        try (FileReader reader = new FileReader(this.file))
        {
            Map<Object, Object> rawData = this.gson.fromJson(reader, Map.class);
            if (rawData == null)
                return;
            rawData.forEach((Object key, Object value) -> {
                if (value instanceof List)
                {
                    List<Object> rawList = (List<Object>) value;
                    List<K> finalList    = new ArrayList<>();
                    for (Object item : rawList)
                        finalList.add((K) item);
                    this.cache.set((T) key, finalList);
                }
            });
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
