package com.codecool.shop.controller.apis;

import com.codecool.shop.SQLConnection;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = {"change-cart/"})
public class CartUpdateApi extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Cookie[] cookies = req.getCookies();
        Integer userID = Integer.parseInt(cookies[0].getValue());
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            JSONObject json = new JSONObject(line);
            Integer productId = json.getInt("productID");
            Integer newValue = json.getInt("newValue");

            try {
                updateCart(productId, newValue, userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCart(int productID,int newValue,int userID) throws SQLException {
        SQLConnection connection = SQLConnection.getConnection();
        Connection sql = connection.getSql();
        if ( newValue == 0){
            removeProduct(productID,userID,sql);
        }else{
            Statement statement = sql.createStatement();
            statement.execute("UPDATE cart SET quantity = " + newValue + " WHERE product_id = " + productID + " AND user_id = " + userID + ";");
        }
    }

    private void removeProduct(int productID,int userID,Connection sql) throws SQLException {
        Statement statement = sql.createStatement();
        statement.execute("DELETE FROM cart WHERE user_id = " + userID + " AND product_id = " + productID + ";");
    }
}