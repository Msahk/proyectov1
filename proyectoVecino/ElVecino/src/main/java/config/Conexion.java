package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection conn;
    String url = "jdbc:mysql://localhost:3306/elvecino";
    String user = "root";
    String pass = "";

    public Connection conexion() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver MySQL:");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
        return conn;
    }
}


/*package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    Connection conn;
    String url ="jdbc:mysql://localhost:3306/elvecino";
    String user = "root";
    String pass = "";
    
    public Connection conexion() {
    try {
        conn = DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
        e.printStackTrace();  // Mostrar el error de conexión
    }
    return conn;
}
}*/