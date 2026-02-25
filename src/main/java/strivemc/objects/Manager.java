package strivemc.objects;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class Manager<T> {

    private final List<T> items;
    private final Function<T, ?> index;
    private final Function<?, List<T>> loader;

    public Manager(Function<T, ?> index, Function<?, List<T>> loader)
    {
        this.items  = new CopyOnWriteArrayList<>();
        this.index  = index;
        this.loader = loader;
    }
    public List<T> getAll()
    {
        return this.items;
    }
    public T get(Object index)
    {
        for (T item : this.items)
            if (this.index.apply(item).equals(index)) return item;
        return null;
    }
    public void add(T item)
    {
        this.items.add(item);
    }
    public void del(T item)
    {
        this.items.remove(item);
    }
    public Manager<T> load()
    {
        List<T> loaded = this.loader.apply(null);
        if (loaded != null)
        {
            this.items.clear();
            this.items.addAll(loaded);
        }
        return this;
    }
}
