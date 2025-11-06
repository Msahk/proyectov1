package dao;

import control.ConDB;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.venta_recetas;

public class venta_recetasDao {

    PreparedStatement ps;
    ResultSet rs;

    // ✅ INSERT actualizado con precio y subtotal
    public boolean agregar(venta_recetas vr) {
        String sql = "INSERT INTO venta_recetas (id_venta, id_receta, cantidad, precio, subtotal) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, vr.getIdVenta());
            ps.setInt(2, vr.getIdReceta());
            ps.setInt(3, vr.getCantidad());
            ps.setDouble(4, vr.getPrecio());
            ps.setDouble(5, vr.getSubtotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error en agregar venta_recetas(): " + e.getMessage());
        }
        return false;
    }

    // ✅ SELECT actualizado para mostrar precio y subtotal
    public List<venta_recetas> listarPorVenta(int idVenta) {
        List<venta_recetas> lista = new ArrayList<>();
        String sql = "SELECT vr.*, r.nombre AS nombreReceta "
                   + "FROM venta_recetas vr "
                   + "INNER JOIN recetas r ON vr.id_receta = r.id_rec "
                   + "WHERE vr.id_venta = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                venta_recetas obj = new venta_recetas();
                obj.setIdVentaReceta(rs.getInt("id_venta_receta"));
                obj.setIdVenta(rs.getInt("id_venta"));
                obj.setIdReceta(rs.getInt("id_receta"));
                obj.setCantidad(rs.getInt("cantidad"));
                obj.setPrecio(rs.getDouble("precio"));
                obj.setSubtotal(rs.getDouble("subtotal"));
                obj.setNombreReceta(rs.getString("nombreReceta"));
                lista.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en listarPorVenta(): " + e.getMessage());
        }
        return lista;
    }

    public void eliminar(venta_recetas vr) {
        String sql = "DELETE FROM venta_recetas WHERE id_venta_receta = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, vr.getIdVentaReceta());
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Receta eliminada de la venta"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando receta"));
            System.out.println("❌ Error en eliminar venta_recetas(): " + e.getMessage());
        }
    }

    public void eliminarPorVenta(int idVenta) {
        String sql = "DELETE FROM venta_recetas WHERE id_venta = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idVenta);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error en eliminarPorVenta(): " + e.getMessage());
        }
    }
}
