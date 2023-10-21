/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anhha
 */
public class DBConnect {
    // Connect 1
    protected java.sql.Connection getConnect() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "admin";
        String pass = "123";
        java.sql.Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
    
    // Connect 2
    protected java.sql.Connection getConnect2() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "EAUT_Student1";
        String pass = "admin123a";
        java.sql.Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
    
    // Connect 3
    protected java.sql.Connection getConnect3() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "admin";
        String pass = "123";
        java.sql.Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
    
    protected void closeConnect(java.sql.Connection conn) throws IOException, SQLException {
        conn.close();
    }
}
