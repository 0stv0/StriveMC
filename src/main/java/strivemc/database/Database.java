package strivemc.database;

public abstract class Database {

    public abstract boolean connect();
    public abstract boolean disconnect();
    public abstract boolean ping();
}
