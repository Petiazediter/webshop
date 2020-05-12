package com.codecool.shop.controller.apis;

import com.codecool.shop.SQLConnection;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;


public class CartCreator {
    public static Map<Product, Integer> createCart(int userID) throws SQLException {
        Map<Product, Integer> userCart = new HashMap<>();
            ProductDao products = ProductDaoMem.getInstance();
            SQLConnection connection = SQLConnection.getConnection();
            Connection sql = connection.getSql();

            Statement statement = sql.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM cart WHERE user_id = " + userID + ";");
            while ( results.next()){
                Product product = products.find(results.getInt("product_id"));
                Integer quantity = results.getInt("quantity");
                userCart.put(product,quantity);
            }
        return userCart;
    }
}
