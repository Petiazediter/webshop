package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    void add(User user);
    User find(int userId);
    void remove(int userId);

    List<User> getAll();
}
