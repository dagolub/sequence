package com.mongodb.tradecraft.sequence_server;

import spark.Request;
import spark.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ServerAddress;


import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;


public class SequenceServer {

    Logger logger;
    MongoCollection<Document> sequences;

    SequenceServer() {
        String URI ="mongodb+srv://atlasAdmin:atlasAdmin@mission03.qud05.mongodb.net/test";
        logger = LoggerFactory.getLogger(SequenceServer.class);
        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        sequences = database.getCollection("sequences");
        System.out.println("Database contains " + sequences.countDocuments() + " sequences");
    }

	//Fetch back inforamtion about samples
    public String sequence(Request req, Response res) throws InterruptedException {
        res.type("application/json");
        String name = req.params(":name");
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.upsert(true);
        options.returnDocument(ReturnDocument.AFTER);

        Document r=null;
        boolean done = false;
        int attempts = 5;
        while (done == false && attempts > 0) {
            try {
                r = sequences.findOneAndUpdate(eq("_id", name), inc("count", 1),
                        options);
                done = true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                attempts--;
                logger.info("Attempting " + attempts + " more times");
                Thread.sleep(2000);
            }
        }
        String json;
        if(done == true) {
            json = "{" + name + ":" + r.getInteger("count") + "}";
        } else { json = "{error:1}";
        }
        res.type("application/json");
        return json;
    }




}
