package com.accgroupproject.realestate;  // Package declaration for the RealEstate class.

import org.springframework.data.annotation.Id;  // Importing Id annotation for marking the identifier field.
import org.springframework.data.mongodb.core.mapping.Document;  // Importing Document annotation for mapping to MongoDB document.

import java.util.List;  // Importing List for handling a collection of image URLs.

@Document(collection = "acc_real_estate_prod")  // Indicates the MongoDB collection to which this class is mapped.
public class RealEstate {

    @Id  // Marks the field as the identifier.
    private String id;  // Unique identifier for a RealEstate object.
    private String city;  // The city where the real estate is located.
    private String country;  // The country where the real estate is located.
    private String type;  // The type of real estate (e.g., apartment, house).
    private String website;  // The website associated with the real estate.
    private String website_url;  // The URL of the real estate website.
    private String price;  // The price of the real estate.
    private String bed;  // The number of bedrooms in the real estate.
    private String bath;  // The number of bathrooms in the real estate.
    private String title;  // The title or name of the real estate listing.
    private String desc;  // A description of the real estate listing.
    private int numberOfVisits;  // The number of visits to the real estate listing.

    private String email;  // The email associated with the real estate listing.
    private String ph_number;  // The phone number associated with the real estate listing.

    private List<String> img_urls;  // List to store image URLs.

    // Getters and setters for the fields.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPh_number() {
        return ph_number;
    }

    public void setPh_number(String ph_number) {
        this.ph_number = ph_number;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public List<String> getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(List<String> img_urls) {
        this.img_urls = img_urls;
    }
}
