package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDao {

    private List<User> data = new ArrayList<>();
    private static UserDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private UserDaoMem() {
    }

    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        data.add(user);
    }

    @Override
    public User find(int userId) {
        return data.stream().filter(t -> t.getUserId() == userId).findFirst().orElse(null);
    }

    @Override
    public void remove(int userId) {
        data.remove(find(userId));
    }

    @Override
    public List<User> getAll() {
        return data;
    }
}
