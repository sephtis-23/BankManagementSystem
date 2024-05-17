package org.elias;

import java.sql.*;

public class SqlConnection {
    String url = "jdbc:mysql://localhost:3306/bank";
    String user = "root";
    String password = "macropenis";

    public SqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(url, user, password);
        System.out.println("Connection successful!");


    }
}
