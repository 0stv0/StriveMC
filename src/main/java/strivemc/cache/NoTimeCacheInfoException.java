package strivemc.cache;

public class NoTimeCacheInfoException extends RuntimeException {

    public NoTimeCacheInfoException(String className)
    {
        super("Class " + className + " is missing @TimeCacheInfo");
    }
}
