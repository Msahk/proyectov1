package dao;

import control.ConDB;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.venta_produccion;

public class venta_produccionDao {

    PreparedStatement ps;
    ResultSet rs;

    public boolean agregar(venta_produccion vp) {
        String sql = "INSERT INTO venta_produccion (id_venta, id_produccion, cantidad) VALUES (?, ?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, vp.getIdVenta());
            ps.setInt(2, vp.getIdProduccion());
            ps.setInt(3, vp.getCantidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar venta_produccion(): " + e.getMessage());
        }
        return false;
    }

    public List<venta_produccion> listarPorVenta(int idVenta) {
        List<venta_produccion> lista = new ArrayList<>();
        String sql = "SELECT vp.*, p.id_pro, p.estado AS estadoProduccion "
                + "FROM venta_produccion vp "
                + "INNER JOIN produccion p ON vp.id_produccion = p.id_pro "
                + "WHERE vp.id_venta = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                venta_produccion obj = new venta_produccion();
                obj.setIdVenProd(rs.getInt("id_ven_prod"));
                obj.setIdVenta(rs.getInt("id_venta"));
                obj.setIdProduccion(rs.getInt("id_produccion"));
                obj.setCantidad(rs.getInt("cantidad"));
                obj.setNombreProduccion(rs.getString("estadoProduccion"));
                lista.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("Error en listarPorVenta(): " + e.getMessage());
        }
        return lista;
    }

    public void eliminar(venta_produccion vp) {
        String sql = "DELETE FROM venta_produccion WHERE id_ven_prod = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, vp.getIdVenProd());
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producción eliminada de la venta"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando producción"));
            System.out.println("Error en eliminar venta_produccion(): " + e.getMessage());
        }
    }

    public void eliminarPorVenta(int idVenta) {
        String sql = "DELETE FROM venta_produccion WHERE id_venta = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idVenta);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en eliminarPorVenta(): " + e.getMessage());
        }
    }

    public int obtenerIdVentaPorProduccion(int idProduccion) {
        int idVenta = 0;
        String sql = "SELECT id_venta FROM venta_produccion WHERE id_produccion = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idVenta = rs.getInt("id_venta");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idVenta;
    }
    
    public boolean existePorVenta(int idVenta) {
    String sql = "SELECT COUNT(*) FROM venta_produccion WHERE id_venta = ?";
    try (PreparedStatement ps = ConDB.conectar().prepareStatement(sql)) {
        ps.setInt(1, idVenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // ✅ Devuelve true si ya hay una producción asociada
            }
        }
    } catch (SQLException e) {
        System.out.println("❌ Error en existePorVenta(): " + e.getMessage());
    }
    return false;
}


}