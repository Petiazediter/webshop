package com.codecool.shop;

import org.postgresql.ds.PGSimpleDataSource;


import java.sql.*;

public class SQLConnection {
    private static SQLConnection connection;
    private PGSimpleDataSource sql;

    private SQLConnection() throws SQLException {
        sql = new PGSimpleDataSource();
        sql.setDatabaseName(System.getenv("PSQL_DB_NAME"));
        sql.setUser(System.getenv("PSQL_USER_NAME"));
        sql.setPassword(System.getenv("PSQL_PASSWORD"));
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
