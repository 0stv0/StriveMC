package strivemc.cache;

import org.bukkit.plugin.Plugin;
import strivemc.objects.MultiMap;
import strivemc.utils.Async;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimeCache<T,K> {

    protected final MultiMap<T, K> cache = new MultiMap<>();
    protected final Map<T, Date> last    = new ConcurrentHashMap<>();
    private final long ttl;
    private final long interval;
    private final Plugin plugin;

    public TimeCache(Plugin plugin)
    {
        if (!this.getClass().isAnnotationPresent(TimeCacheInfo.class))
            throw new NoTimeCacheInfoException(this.getClass().getName());
        TimeCacheInfo info = this.getClass().getAnnotation(TimeCacheInfo.class);

        this.ttl      = info.ttl();
        this.interval = info.interval();
        this.plugin   = plugin;

        this.startThread();
    }
    public void add(T key, K value)
    {
        this.cache.add(key, value);
        this.last.put(key, new Date());
    }
    public void set(T key, List<K> value)
    {
        this.cache.set(key, value);
        this.last.put(key, new Date());
    }
    public List<K> get(T key)
    {
        if (this.cache.get(key) != null)
            this.last.put(key, new Date());
        return this.cache.get(key);
    }
    private void startThread()
    {
        Async.fireForget(this.plugin, () ->
        {
            while (true)
            {
                try
                {
                    Thread.sleep(this.interval);
                    this.cleanup();
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
        });
    }
    private void cleanup()
    {
        long now = System.currentTimeMillis();
        this.last.forEach((T key, Date date) -> {
            if (now - date.getTime() > this.ttl)
            {
                this.cache.del(key);
                this.last.remove(key);
            }
        });
    }
}
