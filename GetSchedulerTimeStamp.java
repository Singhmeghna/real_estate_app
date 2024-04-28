package accproject.scheduler.db;  // Package declaration for the GetSchedulerTimeStamp class.

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;  // Importing MongoDB related classes.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.bson.Document;  // Importing BSON Document class.

public class GetSchedulerTimeStamp {
    static String connectionString = "mongodb+srv://acc_real_estate:U3RulWV4my1egCRp@cluster0.tdacs3z.mongodb.net/?retryWrites=true&w=majority";

    public static void main(String[] args) {
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();  // Creating ServerApi instance.
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();  // Building MongoClientSettings.
        GetSchedulerTimeStamp mongoDBInsertExample = new GetSchedulerTimeStamp();  // Creating an instance of GetSchedulerTimeStamp.
        // mongoDBInsertExample.insertRecord(mHouseDetails, connectionString); // This line is commented out.
        mongoDBInsertExample.saveQueryToMongo("toronto");  // Saving query "toronto" to MongoDB.
        System.out.println(mongoDBInsertExample.getTimestampFromMongo("toronto"));  // Printing timestamp for query "toronto".
    }

    public static void saveQueryToMongo(String query) {
        // Connect to MongoDB
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {  // Creating a MongoClient instance.
            MongoDatabase database = mongoClient.getDatabase("acc_real_estate_db");  // Accessing "acc_real_estate_db" database.
            MongoCollection<Document> collection = database.getCollection("scheduler_prod");  // Accessing "scheduler_prod" collection.

            // Search for the query in the collection
            Document existingDocument = collection.find(new Document("query", query)).first();  // Searching for the query in the collection.

            if (existingDocument != null) {
                // If the query exists, update the timestamp
                Document update = new Document("$set", new Document("timestamp", getCurrentTimestamp()));  // Creating an update document.
                collection.updateOne(existingDocument, update);  // Updating the existing document with the new timestamp.
                System.out.println("Timestamp updated for existing query.");
            } else {
                // If the query does not exist, create a new document with the query and timestamp
                Document newDocument = new Document().append("query", query).append("timestamp", getCurrentTimestamp());  // Creating a new document.
                collection.insertOne(newDocument);  // Inserting the new document into the collection.
                System.out.println("New query saved to MongoDB!");
            }
        }
    }

    public static String getTimestampFromMongo(String query) {
        // Connect to MongoDB
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {  // Creating a MongoClient instance.
            MongoDatabase database = mongoClient.getDatabase("acc_real_estate_db");  // Accessing "acc_real_estate_db" database.
            MongoCollection<Document> collection = database.getCollection("scheduler_prod");  // Accessing "scheduler_prod" collection.

            // Search for the query in the collection
            Document result = collection.find(new Document("query", query)).first();  // Searching for the query in the collection.

            if (result != null) {
                // Retrieve and return the timestamp
                return result.getString("timestamp");  // Returning the timestamp from the found document.
            } else {
                return null;
            }
        }
    }

    private static String getCurrentTimestamp() {
        // Get the current timestamp in a formatted string
        LocalDateTime now = LocalDateTime.now();  // Getting the current date and time.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d'th' yyyy 'at' h:mm a", Locale.ENGLISH);  // Creating a formatter.
        return now.format(formatter);  // Formatting the timestamp and returning it as a string.
    }
}
