package com.example.food_app.Domain;

import androidx.annotation.NonNull;

public class Location {
    private String id;
    private String loc;

    @NonNull
    @Override
    public String toString() {
        return loc;
    }



    // constructeur de la classe Location sans paramètres
    public Location() {
    }

    public Location(String id, String loc) {
        this.id = id;
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
