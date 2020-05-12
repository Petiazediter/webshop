package com.codecool.shop.controller.apis;

import com.codecool.shop.SQLConnection;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"add-to-cart/"})
public class CartHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = req.getReader();
            Integer productID = Integer.parseInt(reader.readLine());

            SQLConnection connection = SQLConnection.getConnection();
            Connection sql = connection.getSql();
            Cookie[] cookies = req.getCookies();
            Integer userID = Integer.parseInt(cookies[0].getValue());
            Statement statement = sql.createStatement();
            ResultSet results = statement.executeQuery("SELECT COUNT(product_id) AS count_of_products FROM cart WHERE product_id = " + productID + " AND user_id = " + userID + ";");
            if (results != null){
            while (results.next()){
                Integer count = results.getInt("count_of_products");
                System.out.println("COUNT: " + count);
                if ( count.equals(0)){
                    statement.execute("INSERT INTO cart (user_id,product_id,quantity) VALUES(" + userID + ", " + productID + ", 1);");
                    System.out.println("INSERT");
                } else {
                    System.out.println("UPDATE");
                    statement.execute("UPDATE cart SET quantity = quantity + 1 WHERE product_id = " + productID + " AND user_id = " + userID + ";");
                }
                break;
            }}else{
                statement.execute("INSERT INTO cart (user_id,product_id,quantity) VALUES(" + userID + ", " + productID + ", 1);");
                System.out.println("INSERT");
            }

            ProductDao products = ProductDaoMem.getInstance();
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            JSONObject json = new JSONObject();
            json.put("name",products.find(productID).getName());

            out.print(json);
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
