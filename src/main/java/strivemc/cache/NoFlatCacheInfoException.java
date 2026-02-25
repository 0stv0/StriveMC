package strivemc.cache;

public class NoFlatCacheInfoException extends RuntimeException {

    public NoFlatCacheInfoException(String className)
    {
        super("Class " + className + " is missing @FlatCacheInfo");
    }
}
