package com.mimota.util;

import com.mimota.util.common.Database;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

/**
 * Created by yi.dai on 2018/6/6.
 */
public class MongoUtil {
    private static MongoClient mongoClient = null;

    public static String dbName = "supermall"; // 这里以后写库名

    static {
        mongoInit();
    }

    private static void mongoInit() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder().connectionsPerHost(150).cursorFinalizerEnabled(true).readPreference(getReadPreference());

        mongoClient = new MongoClient(new MongoClientURI(Database.Aliyun_MONGO_URL.getUrl(), builder));
    }

    // 用于获取database
    public static MongoDatabase getDatabases() {
        return getDatabases(dbName);
    }

    public static MongoDatabase getDatabases(String dbname) {
        MongoDatabase database = mongoClient.getDatabase(dbname);

        return database;
    }

    // 用于获取mongodb ——> database -> collection
    public static MongoCollection<Document> getCollection(String collectionName) {
        return getCollection(dbName, collectionName);
    }

    public static MongoCollection<Document> getDeviceCollection() {
        return getCollection("device");
    }

    public static MongoCollection<Document> getCollection(String dbname, String collectionName) {
        MongoCollection<Document> collection = getDatabases(dbname).getCollection(collectionName);

        return collection;
    }

    // 获取mongo client
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    private static ReadPreference getReadPreference() {
        ReadPreference readPrefer;
        readPrefer = ReadPreference.primaryPreferred();
        return readPrefer;
    }

    public static void main(String[] args) {
        MongoDatabase database = MongoUtil.getDatabases("supermall");
        System.out.println(database.toString());

        MongoIterable<String> it = database.listCollectionNames();
        while(it.iterator().hasNext()){
            System.out.println(it.toString());
        }
        MongoClient client = MongoUtil.getMongoClient();
        System.out.println(client.toString());
    }
}
