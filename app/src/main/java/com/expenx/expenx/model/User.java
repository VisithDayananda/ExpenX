package com.expenx.expenx.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaveesh on 2017-04-10.
 */

@IgnoreExtraProperties
public class User {

    public String userUID;
    public String fname;
    public String lname;
    public String profileImage;
    public String defaultCurrency;
    public Reminder reminder;

    public User(String fname, String lname, String profileImage, String defaultCurrency, Reminder reminder) {
        this.fname = fname;
        this.lname = lname;
        this.profileImage = profileImage;
        this.defaultCurrency = defaultCurrency;
        this.reminder = reminder;
    }

    public User() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fname", fname);
        result.put("lname", lname);
        result.put("profileImage", profileImage);
        result.put("defaultCurrency", defaultCurrency);
        result.put("reminder", reminder);

        return result;
    }
}
