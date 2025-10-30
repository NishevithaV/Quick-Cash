package com.example.quick_cash.Models;

public class User {
    private String id;
    private String name;
    private String email;
    private String type;

    public User() {}

    public User(String type, String email, String name) {
        this.type = type;
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
