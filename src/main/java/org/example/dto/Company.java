package org.example.dto;

import com.google.gson.Gson;

public class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
