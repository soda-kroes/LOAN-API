package com.java.year3.loan_api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgrestConnection {

    private String PG_CLASS = "org.postgresql.Driver";


    // ---------PROD-------------
    private String PG_DB_URL = "jdbc:postgresql://localhost:5432/loan";
    private String PG_USER = "postgres";
    private String PG_PASS = "sodaIT@168";


    Connection sqsv_conn = null;

    public ResultSet selectAll(String query){
        ResultSet result = null;
        System.out.println(query);
        try {
            Class.forName(PG_CLASS);
            sqsv_conn = DriverManager.getConnection(PG_DB_URL, PG_USER, PG_PASS);
            PreparedStatement sqsv_stat = sqsv_conn.prepareStatement(query);
            result = sqsv_stat.executeQuery();
            sqsv_conn.close();

        }catch (Exception e){
            System.out.println(e);
        }
        return  result;
    }

    public ResultSet create(String query){
        ResultSet result = null;
        System.out.println(query);
        try {
            Class.forName(PG_CLASS);
            sqsv_conn = DriverManager.getConnection(PG_DB_URL, PG_USER, PG_PASS);
            PreparedStatement sqsv_stat = sqsv_conn.prepareStatement(query);
            result = sqsv_stat.executeQuery();
            sqsv_conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return  result;
    }

    public ResultSet delete(String query){
        ResultSet result = null;
        System.out.println(query);
        try {
            Class.forName(PG_CLASS);
            sqsv_conn = DriverManager.getConnection(PG_DB_URL, PG_USER, PG_PASS);
            PreparedStatement sqsv_stat = sqsv_conn.prepareStatement(query);
            result = sqsv_stat.executeQuery();
            sqsv_conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return  result;
    }

    public ResultSet query(String query){
        ResultSet result = null;
        System.out.println(query);
        try {
            Class.forName(PG_CLASS);
            sqsv_conn = DriverManager.getConnection(PG_DB_URL, PG_USER, PG_PASS);
            PreparedStatement sqsv_stat = sqsv_conn.prepareStatement(query);
            result = sqsv_stat.executeQuery();
            sqsv_conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return  result;
    }
}
