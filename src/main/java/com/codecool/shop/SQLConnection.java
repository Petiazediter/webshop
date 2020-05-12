package com.codecool.shop;

import org.postgresql.ds.PGSimpleDataSource;


import java.sql.*;

public class SQLConnection {
    private static SQLConnection connection;
    private PGSimpleDataSource sql;

    private SQLConnection() throws SQLException {
        sql = new PGSimpleDataSource();
        sql.setDatabaseName("codecoolshop");
        sql.setUser("peti");
        sql.setPassword("peter133");
        sql.getConnection().close();
    }

    public Connection getSql() throws SQLException {
        return sql.getConnection();
    }



    public static SQLConnection getConnection() throws SQLException {
        if (connection == null){
            connection = new SQLConnection();
        }
        return connection;
    }

}
