
package dao;

import control.ConDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dashboardDao {
  
    PreparedStatement ps;
    ResultSet rs;
    
    public int obtenerConteoUsuarios() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from usuarios";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    
    
    public int obtenerConteoVentas() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from ventas";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    //ToDO LO QUE SEA PRODUCCIONES
    
    public int obtenerConteoProduccion() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from produccion";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoProduccionesPendientes() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from produccion where estado = 'Pendiente'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoProduccionesAceptada() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from produccion where estado = 'Aceptada'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion aceptada");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoProduccionesFinalizada() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from produccion where estado = 'Finalizada'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion finalizada");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    
    //TODO LO QUE SEA VENTAS
    
    
    public int conteoVentasPagoPendiente() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from ventas where estado = 'Pago pendiente'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoVentasPagoCompleto() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from ventas where estado = 'Pago completo'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoVentasProcesando() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from ventas where estado = 'Procesando'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    public int conteoVentasCompletado() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from ventas where estado = 'Completada'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
    
    
    //TODO LO DE RECETAS
    
    
    public int conteoRecetasTotales() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from recetas";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
            
    public int conteoRecetasInactivas() {
        int conteo = 0;
        
        try {
            String sql = "select count(*) as conteo from recetas where estado = 'Inactivo'";
            ps = ConDB.conectar().prepareStatement(sql);
            
            rs = ps.executeQuery();
                while(rs.next()) {
                    conteo = rs.getInt("conteo");
                }
        } catch (SQLException e) {
                System.out.println("Error al obtener el conteo de la produccion pendiente");
                e.printStackTrace();
        }
        
        return conteo;
    }
}
