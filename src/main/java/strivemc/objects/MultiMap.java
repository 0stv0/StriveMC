package strivemc.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiMap<T, K> {

    private final Map<T, List<K>> items;

    public MultiMap()
    {
        this.items = new ConcurrentHashMap<>();
    }
    public Map<T, List<K>> getInternal()
    {
        return this.items;
    }
    public void add(T key, K value)
    {
        List<K> list = this.items.get(key) == null ? new ArrayList<>() : this.items.get(key);
        list.add(value);
        this.set(key, list);
    }
    public void set(T key, List<K> list)
    {
        this.items.put(key, list);
    }
    public void del(T key)
    {
        this.items.remove(key);
    }
    public List<K> get(T key)
    {
        return this.items.get(key);
    }
    public void clear()
    {
        this.items.clear();
    }
}
