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

	SequenceServer() {
		logger = LoggerFactory.getLogger(SequenceServer.class);
        MongoCollection<Document> sequences;
        String uri ="mongodb+srv://atlasAdmin:atlasAdmin@mission03.qud05.mongodb.net/test";

        //Add code Below Here

        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        sequences = database.getCollection("sequences");
        System.out.println("Database contains " + sequences.countDocuments() + " sequences");

    }

	//Fetch back inforamtion about samples
	public String sequence(Request req, Response res) {
		res.type("application/json");
		String name= req.params(":name");
		String json = "{"+name+":"+1+"}" ;
		res.type("application/json");
		return json;
	}



}
