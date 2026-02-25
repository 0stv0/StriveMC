package strivemc.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Async {

    public static <T> void runAsync(Plugin plugin, Supplier<T> background, Consumer<T> result)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            T data = background.get();
            Bukkit.getScheduler().runTask(plugin, () -> result.accept(data));
        });
    }
    public static <T> CompletableFuture<T> supplyAsync(Plugin plugin, Supplier<T> background)
    {
        CompletableFuture<T> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
        {
            T data = background.get();
            future.complete(data);
        });
        return future;
    }
    public static void fireForget(Plugin plugin, Runnable task)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
}
