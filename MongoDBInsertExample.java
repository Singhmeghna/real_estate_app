package db_utils;  // Package declaration for the MongoDBInsertExample class.

import com.mongodb.ConnectionString;  // Importing ConnectionString for managing the MongoDB connection.
import com.mongodb.MongoClientSettings;  // Importing MongoClientSettings for configuring the MongoDB client.
import com.mongodb.ServerApi;  // Importing ServerApi for specifying the MongoDB server API.
import com.mongodb.ServerApiVersion;  // Importing ServerApiVersion for specifying the version of the MongoDB server API.
import com.mongodb.client.*;  // Importing MongoClient, MongoDatabase, and MongoCollection for MongoDB interactions.

import java.util.List;  // Importing List for handling a collection of strings.
import java.util.Random;  // Importing Random for generating random values.

import org.bson.Document;  // Importing Document for representing MongoDB documents.

public class MongoDBInsertExample {

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://acc_real_estate:U3RulWV4my1egCRp@cluster0.tdacs3z.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
//        mongoDBInsertExample.insertRecord(mHouseDetails, connectionString);
    }

    // Overloaded method for inserting details into MongoDB using individual parameters.
    public void insertDetails(String city, String country, String type, String website, String price, String bed,
            String bath, String title, String desc, String id, String url, List<String> mList) {
        String connectionString = "mongodb+srv://acc_real_estate:U3RulWV4my1egCRp@cluster0.tdacs3z.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        MongoDBInsertExample mongoDBInsertExample = new MongoDBInsertExample();
        mongoDBInsertExample.insertRecord(city, country, type, website, price, bed, bath, title, desc, id, url, mList,
                connectionString);
    }

    // Main method for inserting a record into MongoDB.
    public void insertRecord(String city, String country, String type, String website, String price, String bed,
            String bath, String title, String desc, String id, String url, List<String> mList,
            String connectionString) {
        // Connect to MongoDB
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Access the database
            MongoDatabase database = mongoClient.getDatabase("acc_real_estate_db");
            // Access the collection
            MongoCollection<Document> collection = database.getCollection("acc_real_estate_prod");

            // Check if the document already exists based on a unique identifier
            Document existingDocument = collection.find(new Document("img_urls", mList)).first();
            if (existingDocument != null) {
                System.out.println("Document with ID already exists. Not inserting.");
            } else {
                String ph_number = "", email = "";
                try {
                    // Validate and set default values if necessary
                    if (bed == null || bed.isEmpty()) {
                        bed = "1";
                    }

                    if (bath == null || bath.isEmpty()) {
                        bath = "1";
                    }

                    if (price == null || price.isEmpty()) {
                        // Generate a random value between 2300 and 15986
                        int randomPrice = new Random().nextInt(13686) + 2300;
                        price = String.valueOf(randomPrice);
                    }

                    if (email == null || email.isEmpty()) {
                        // Generate a random email with a gmail domain
                        email = generateRandomEmail();
                    }

                    if (ph_number == null || ph_number.isEmpty()) {
                        // Generate a random mobile number based on the country (assuming country is a variable)
                        ph_number = generateRandomPhoneNumber(country);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                int numberOfVisits = new Random().nextInt(53) + 1;
                // Create the document with validated and default values
                Document newDocument = new Document("_id", id).append("city", city).append("country", country)
                        .append("type", type).append("website", website).append("website_url", url)
                        .append("price", price).append("ph_number", ph_number).append("email", email).append("bed", bed)
                        .append("bath", bath).append("title", title).append("desc", desc)
                        .append("numberOfVisits", numberOfVisits).append("id", id).append("img_urls", mList);
                // Insert the document into the collection
                collection.insertOne(newDocument);
                System.out.println("Document inserted successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Generate a random email address.
    private static String generateRandomEmail() {
        String randomUsername = "user" + new Random().nextInt(1000); // Change as needed
        return randomUsername + "@gmail.com";
    }

    // Generate a random phone number based on the country.
    private static String generateRandomPhoneNumber(String country) {
        // Generate a random mobile number based on the country (add more cases as needed)
        switch (country) {
            case "US":
                return "+1-" + (new Random().nextInt(900000000) + 1000000000); // Assuming US format
            case "Canada":
                return "+1-" + (new Random().nextInt(900000000) + 1000000000); // Assuming Canada format
            case "Dubai":
                return "+971-" + (new Random().nextInt(900000000) + 1000000000); // Assuming Dubai format
            default:
                return "+1-" + (new Random().nextInt(900000000) + 1000000000); // Default to US format
        }
    }
}
