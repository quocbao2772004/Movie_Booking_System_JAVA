package com.mycompany.database;

import java.util.List;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class FeedbackDatabase {
    private final MongoCollection<Document> collection;

    public FeedbackDatabase() {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("feedbacks");
    }

    public void addFeedback(String movie, String user, String feedback, String status) {
        Document document = new Document("movie", movie)
                .append("user", user)
                .append("feedback", feedback)
                .append("status", status);
        collection.insertOne(document);
        
    }


    public List<Feedback> getFeedbacks(String movie) {
        List<Feedback> feedbacks = new ArrayList<>();
        Document query = new Document("movie", movie);
        try (var cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Feedback feedback = new Feedback(
                    doc.getString("movie"),
                    doc.getString("user"),
                    doc.getString("feedback"),
                    doc.getString("status")
                );
                feedbacks.add(feedback);
            }
        }
        
        return feedbacks;
    }
}
