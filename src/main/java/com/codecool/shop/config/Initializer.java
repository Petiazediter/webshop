package com.codecool.shop.config;

import com.codecool.shop.SQLConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            SQLConnection connection = SQLConnection.getConnection();
            System.out.println("Connection created.");

            ProductDao productDataStore = ProductDaoMem.getInstance();
            ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

            Connection sql = connection.getSql();
            Statement statement = sql.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM suppliers;");
            System.out.println("Elkezdödött");
            while (results.next()){
                String name = results.getString("name");
                String description = results.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplierDataStore.add(supplier);
                supplier.setId(results.getInt("id"));
                supplier.setImageLink(results.getString("image"));
                System.out.println(name);
            }

            statement.close();
            results.close();

            Statement categoryStatement = sql.createStatement();
            ResultSet categoryResults = categoryStatement.executeQuery("SELECT * FROM categories");
            System.out.println("Elkezdődött 2");
            while (categoryResults.next()){
                int Id = categoryResults.getInt("id");
                String name = categoryResults.getString("name");
                String desc = categoryResults.getString("description");
                String department = categoryResults.getString("department");
                ProductCategory productCategory = new ProductCategory(name,department,desc);
                productCategory.setId(Id);
                System.out.println(name);
                productCategoryDataStore.add(productCategory);
            }
            categoryResults.close();
            categoryStatement.close();

            Statement productStatement = sql.createStatement();
            ResultSet productResults = productStatement.executeQuery("SELECT * FROM products");
            while (productResults.next()){
                int Id = productResults.getInt("id");
                String name = productResults.getString("name");

                String description = productResults.getString("description");
                float price = productResults.getFloat("price");
                System.out.println(Float.toString(price));
                int supplierID = productResults.getInt("supplier");
                int categoryId = productResults.getInt("category");

                ProductCategory category = productCategoryDataStore.find(categoryId);
                if (category == null){
                    System.out.println("Nincs találat. : " + Integer.toString(categoryId));
                }
                Supplier supplier = supplierDataStore.find(supplierID);

                Product product = new Product(name,price,"USD",description,category,supplier);
                product.setId(Id);
                System.out.println(name);
                productDataStore.add(product);

            }
            productResults.close();
            productStatement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
