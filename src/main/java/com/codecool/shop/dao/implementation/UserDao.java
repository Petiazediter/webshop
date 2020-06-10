package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.User;

import java.util.UUID;

public interface UserDao {
    void add(User user);
    User find(int userId);
}
