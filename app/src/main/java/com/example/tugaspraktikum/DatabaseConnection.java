package com.example.tugaspraktikum;

import android.os.StrictMode;

import java.sql.Connection;

public class DatabaseConnection {
    Connection con;
    String ip,database,username,password;

    public void connectionClass(){
        ip = "127.0.0.1";
        database = "android";
        username = "root";
        password = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;

    }
}
