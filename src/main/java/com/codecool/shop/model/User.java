package com.codecool.shop.model;

import java.util.UUID;

public class User {
    private UUID userId = UUID.randomUUID();
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
