package org.example.dto;

import com.google.gson.Gson;

public class User {
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    public User
            (String name,String username, String email,Address address,String phone,String website,Company company){
        this.name=name;
        this.username=username;
        this.email=email;
        this.address=address;
        this.phone=phone;
        this.website=website;
        this.company=company;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Address getAddress() {
        return address;
    }

    public Company getCompany() {
        return company;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

