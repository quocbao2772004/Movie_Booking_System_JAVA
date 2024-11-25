package com.mycompany.database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class CinemaManager {
    private final MongoCollection<Document> collection;
    public CinemaManager()
    {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("cinema");
    }
    public void addCinema(String name, List<String>showHours)
    {
        Document cinema = new Document("cinema", name)
                .append("time", showHours);
        collection.insertOne(cinema);
    }
    public void deleteCinema(String name)
    {
        Document query = new Document("cinema", name);
        collection.deleteOne(query);
    }
    public List<Cinema> getAllCinemas() {
        List<Cinema> Cinemas = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Cinema cinema = new Cinema(
                    doc.getString("name"),
                    (List<String>) doc.get("time")   
                );
                Cinemas.add(cinema);
            }
        }
        return Cinemas;
    }
}
