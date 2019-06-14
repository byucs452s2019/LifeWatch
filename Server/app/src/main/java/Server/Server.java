package Server;

import Database.*;
import Database.Model.ActivityModel;
import Database.Model.MotionModel;
import Database.Model.UserModel;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyucn on 5/23/19.
 */

public class Server {

    public static void main(String[] argvs) {
        MongoClient mongoClient = MongoClients.create();

        MongoDatabase database = mongoClient.getDatabase("DocDB");
        MongoCollection<Document> collection = database.getCollection("users");

        Document doc = new Document("username", "MongoDB")
                .append("password", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
    }
}
