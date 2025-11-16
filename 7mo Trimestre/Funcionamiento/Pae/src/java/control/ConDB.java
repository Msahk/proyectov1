package control;

import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConDB {
    static public Connection conectar(){
        
        Connection conn = null;
        
        try {
            Driver drv = new Driver();
            DriverManager.registerDriver(drv);
            
            String url = "jdbc:mysql://localhost:3306/pae?user=root&useSSL=false";
            conn = DriverManager.getConnection(url);           
            
        } catch (SQLException e) {
            System.out.println("Error en Conexión");
        }
        
        return conn;
    }
}