package dao;

import control.ConDB;
import models.ventas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ventasDao {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
  
    public List<ventas> listar() {
        List<ventas> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try {
            con = ConDB.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ventas v = new ventas();
                v.setIdVen(rs.getInt("id_ven"));
                v.setTipo(rs.getString("Tipo"));
                v.setFecha(rs.getTimestamp("fecha"));
                v.setIdUsuario(rs.getInt("id_usu"));
                v.setIdCliente(rs.getInt("id_Cliente"));
                v.setTotal(rs.getDouble("total"));
                v.setEstado(rs.getString("estado"));
                v.setObservaciones(rs.getString("observaciones"));
                lista.add(v);
            }
        } catch (Exception e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }
    
    
    public boolean agregar(ventas v) {
        String sql = "INSERT INTO ventas (Tipo, fecha, id_usu, id_Cliente, total, estado, observaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = ConDB.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, new java.sql.Timestamp(v.getFecha().getTime()));
            ps.setInt(3, v.getIdUsuario());
            ps.setInt(4, v.getIdCliente());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getEstado());
            ps.setString(7, v.getObservaciones());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar venta: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean actualizar(ventas v) {
        String sql = "UPDATE ventas SET Tipo=?, fecha=?, id_usu=?, id_Cliente=?, total=?, estado=?, observaciones=? "
                   + "WHERE id_ven=?";
        try {
            con = ConDB.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, new java.sql.Timestamp(v.getFecha().getTime()));
            ps.setInt(3, v.getIdUsuario());
            ps.setInt(4, v.getIdCliente());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getEstado());
            ps.setString(7, v.getObservaciones());
            ps.setInt(8, v.getIdVen());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar venta: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ventas WHERE id_ven=?";
        try {
            con = ConDB.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
            return false;
        }
    }
}