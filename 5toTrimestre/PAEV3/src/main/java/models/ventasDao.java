/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ventasDao {
    
    conexion cn = new conexion();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    public List<ventas> listar() throws ClassNotFoundException {
        List<ventas> lista = new ArrayList<>();
        String sql = "SELECT * from ventas";
        usuariosDao uDao = new usuariosDao();
        clientesDao cDao = new clientesDao();
        
        try{
            conn = cn.conexion();
            if (conn == null) {
                System.out.println("Error: Conexión fallida a la base de datos.");
                return lista;
            }
            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                ventas obj = new ventas();
                obj.setId_ven(rs.getInt("id_ven"));
                obj.setId_usu(rs.getInt("id_usu"));
                obj.setId_Cliente(rs.getInt("id_Cliente"));
                obj.setTipo(rs.getString("tipo"));
                obj.setTotal(rs.getBigDecimal("total"));
                obj.setFecha(rs.getString("fecha"));
                obj.setEstado(rs.getString("estado"));
                obj.setObservaciones(rs.getString("observaciones"));
                
                usuarios u = uDao.obtenerPorId(obj.getId_usu());
                clientes c = cDao.obtenerPorId(obj.getId_Cliente());
                
                if (u != null) {
                    obj.setVendedorNombre(u.getNombres() + " " + u.getApellidos());
                } else {
                    obj.setVendedorNombre("DESCONOCIDO");
                }
                
                if (c != null) {
                    obj.setClienteNombre(c.getNombre());
                } else {
                    obj.setClienteNombre("DESCONOCIDO");
                }
                
                
                
                lista.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            //cerrarTodo();
        }
        
        return lista;
        
        
        
        
    
    }
}
