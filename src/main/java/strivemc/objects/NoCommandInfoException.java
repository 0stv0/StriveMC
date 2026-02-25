package strivemc.objects;

public class NoCommandInfoException extends RuntimeException {

    public NoCommandInfoException(String className)
    {
        super("Class " + className + " is missing @CommandInfo");
    }
}
