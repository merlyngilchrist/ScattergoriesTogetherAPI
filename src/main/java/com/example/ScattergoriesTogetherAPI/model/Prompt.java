package com.example.ScattergoriesTogetherAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prompts")  // Ensure this matches the name of your collection in MongoDB
public class Prompt {

    @Id
    private String _id;  // MongoDB uses _id as the default identifier field

    private String category;

    // Constructors, getters, setters

    public Prompt(String _id, String category) {
        this._id = _id;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
