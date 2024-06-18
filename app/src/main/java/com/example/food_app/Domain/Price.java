package com.example.food_app.Domain;

import androidx.annotation.NonNull;

public class Price {
    private String id;
    private String Value;

    public Price() {
    }

    public Price(String id, String value) {
        this.id = id;
        this.Value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getvalue() {
        return Value;
    }

    public void setvalue(String value) {
        this.Value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return Value;
    }
}
