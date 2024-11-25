<<<<<<< HEAD

package com.mycompany.database;
import com.mycompany.movie.*;
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
        Document document = collection.find(query).first();
        if (document != null) {
            feedbacks.add(new Feedback(document.getString("user"), document.getString("movie"), document.getString("feedback"), document.getString("status")));
        }
        
        return feedbacks;
    }
}
=======
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
>>>>>>> eccd809701237b0b3c1ccddde85eb34f8ec4cc5a
