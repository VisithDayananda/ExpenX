package com.expenx.expenx.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaveesh on 2017-04-10.
 */

public class Note {

    public String pushId;
    public String title;
    public double amount;
    public String description;
    public String noteImage;

    public Note(String title, double amount, String description, String noteImage) {
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.noteImage = noteImage;
    }

    public Note() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("amount", amount);
        result.put("description", description);
        result.put("noteImage", noteImage);

        return result;
    }
}
