package strivemc.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Mongo extends Database {

    private final String uri, db;
    private MongoClient client;
    private MongoDatabase database;

    public Mongo(String uri, String db)
    {
        super();
        this.uri = uri;
        this.db  = db;
    }
    @Override
    public boolean connect()
    {
        if (this.client != null)
            return false;
        try
        {
            CodecRegistry pojoc = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            this.client   = MongoClients.create(this.uri);
            this.database = this.client.getDatabase(this.db).withCodecRegistry(pojoc);

            return this.ping();
        }
        catch (MongoException e)
        {
            return false;
        }
    }
    @Override
    public boolean disconnect()
    {
        if (this.client == null)
            return false;

        this.client.close();
        return true;
    }
    @Override
    public boolean ping()
    {
        try
        {
            this.database.runCommand(new Document("ping", 1));
            return true;
        }
        catch (MongoException e)
        {
            return false;
        }
    }
    public MongoCollection<Document> getCollection(String name)
    {
        return this.database.getCollection(name);
    }
    public <T> MongoCollection<T> getCollection(String name, Class<T> clazz)
    {
        return this.database.getCollection(name, clazz);
    }
}
