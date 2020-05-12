package com.codecool.shop.controller;

import com.codecool.shop.SQLConnection;
import com.codecool.shop.controller.apis.CartCreator;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getAll());


        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        Cookie cookies[] = req.getCookies();
        if ( cookies != null){
            String userID = cookies[0].getValue();
            if (userID != null || !userID.equals("")){

                try {
                    context.setVariable("cart", CartCreator.createCart(Integer.parseInt(userID)));
                } catch (SQLException e) {
                }

                engine.process("product/index.html", context, resp.getWriter());
                return;
            }
        }

        engine.process("product/login.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            if ( email == null && username != null){
                doLogin(req,resp);
            }else{
                doRegister(req);
            }
            doGet(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doLogin(HttpServletRequest req,HttpServletResponse resp) throws SQLException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        SQLConnection connection = SQLConnection.getConnection();
        Connection sql = connection.getSql();

        String md5Code = MD5Converter.getMd5(password);
        Statement statement = sql.createStatement();
        System.out.println(md5Code);
        ResultSet results = statement.executeQuery("SELECT * FROM users WHERE username = '"+username+"';");
        while (results.next()){
            if ( results.getString("password").equals(md5Code)) {
                Cookie cookie = new Cookie("userID", Integer.toString(results.getInt("id")));
                resp.addCookie(cookie);
            }
        }
        results.close();
        statement.close();
    }

    protected void doRegister(HttpServletRequest req) throws SQLException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        SQLConnection connection = SQLConnection.getConnection();
        Connection sql = connection.getSql();
        Statement statement = sql.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "' OR email = '" + email + "';");

        int size =0;
        if (results != null) {
            while (results.next()) {
                size++;
            }
        }

        if ( size == 0) {
            statement.executeQuery("INSERT  INTO users (username,password,email) VALUES('" + username + "', '" + MD5Converter.getMd5(password) + "','" + email + "');");
        }
    }

}
