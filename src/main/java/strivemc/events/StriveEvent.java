package strivemc.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class StriveEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public StriveEvent()
    {
        super(false);
    }
    public StriveEvent(boolean async)
    {
        super(async);
    }
    public static HandlerList getHandlerList()
    {
        return handlerList;
    }
    @Override
    public @NonNull HandlerList getHandlers()
    {
        return handlerList;
    }
}
