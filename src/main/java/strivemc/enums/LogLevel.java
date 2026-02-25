package strivemc.enums;

public enum LogLevel {

    INFO(""),
    WARNING(""),
    DEBUG("");

    private final String tag;

    LogLevel(String tag)
    {
        this.tag = tag;
    }
    public String getTag()
    {
        return this.tag;
    }
}
