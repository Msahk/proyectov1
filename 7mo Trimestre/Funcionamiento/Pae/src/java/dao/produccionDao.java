package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import control.ConDB;
import modelo.produccion;

public class produccionDao {

    // 🔹 LISTAR todas las producciones
    public List<produccion> listar() {
        List<produccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM produccion ORDER BY fecha_hora DESC";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produccion p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getString("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en listar(): " + e.getMessage());
        }

        return lista;
    }

    // 🔹 AGREGAR nueva producción
    public int agregar(produccion p) {
        String sql = "INSERT INTO produccion (estado, usuario, fecha_hora) VALUES (?, ?, ?)";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getEstado());
            ps.setString(2, p.getUsuario());
            ps.setTimestamp(3, new java.sql.Timestamp(p.getFecha_hora().getTime()));

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet gk = ps.getGeneratedKeys()) {
                    if (gk.next()) {
                        return gk.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en agregar(): " + e.getMessage());
        }

        return -1;
    }

    // 🔹 ACTUALIZAR producción existente
    public boolean actualizar(produccion p) {
        String sql = "UPDATE produccion SET estado=?, usuario=?, fecha_hora=?, fecha_aceptacion=?, fecha_finalizacion=? WHERE id_proc=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getEstado());
            ps.setString(2, p.getUsuario());
            ps.setTimestamp(3, p.getFecha_hora() != null ? new java.sql.Timestamp(p.getFecha_hora().getTime()) : null);
            ps.setTimestamp(4, p.getFecha_aceptacion() != null ? new java.sql.Timestamp(p.getFecha_aceptacion().getTime()) : null);
            ps.setTimestamp(5, p.getFecha_finalizacion() != null ? new java.sql.Timestamp(p.getFecha_finalizacion().getTime()) : null);
            ps.setInt(6, p.getId_proc());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error en actualizar(): " + e.getMessage());
        }

        return false;
    }

    // 🔹 OBTENER por ID
    public produccion obtenerPorId(int id) {
        produccion p = null;
        String sql = "SELECT * FROM produccion WHERE id_proc=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new produccion();
                    p.setId_proc(rs.getInt("id_proc"));
                    p.setEstado(rs.getString("estado"));
                    p.setUsuario(rs.getString("usuario"));
                    p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                    p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                    p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en obtenerPorId(): " + e.getMessage());
        }

        return p;
    }

    // 🔹 ELIMINAR producción
    public void eliminar(produccion p) {
        String sql = "DELETE FROM produccion WHERE id_proc=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId_proc());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producción eliminada exitosamente"));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar producción"));
        }
    }

    // 🔹 CAMBIAR estado con trazabilidad automática
    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "";
        java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

        switch (nuevoEstado) {
            case "Aceptada":
                sql = "UPDATE produccion SET estado=?, fecha_aceptacion=? WHERE id_proc=?";
                break;
            case "Finalizada":
                sql = "UPDATE produccion SET estado=?, fecha_finalizacion=? WHERE id_proc=?";
                break;
            default: // vuelve a Pendiente o similar
                sql = "UPDATE produccion SET estado=? WHERE id_proc=?";
                break;
        }

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            if (!nuevoEstado.equalsIgnoreCase("Pendiente")) {
                ps.setTimestamp(2, now);
                ps.setInt(3, id);
            } else {
                ps.setInt(2, id);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error en cambiarEstado(): " + e.getMessage());
        }

        return false;
    }
    
    // 🟢 Actualizar fecha de aceptación manualmente
public boolean actualizarFechaAceptacion(int idProc, java.sql.Timestamp fecha) {
    String sql = "UPDATE produccion SET fecha_aceptacion = ?, estado = 'Aceptada' WHERE id_proc = ?";
    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setTimestamp(1, fecha);
        ps.setInt(2, idProc);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("❌ Error en actualizarFechaAceptacion(): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// 🟢 Actualizar fecha de finalización manualmente
public boolean actualizarFechaFinalizacion(int idProc, java.sql.Timestamp fecha) {
    String sql = "UPDATE produccion SET fecha_finalizacion = ?, estado = 'Finalizada' WHERE id_proc = ?";
    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setTimestamp(1, fecha);
        ps.setInt(2, idProc);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("❌ Error en actualizarFechaFinalizacion(): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// Obtener producción asociada a una venta
public produccion obtenerPorVenta(int idVenta) {
    produccion p = null;
    String sql = "SELECT p.* FROM produccion p " +
                 "JOIN venta_produccion vp ON p.id_proc = vp.idProduccion " +
                 "WHERE vp.idVenta = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idVenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getString("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
            }
        }
    } catch (SQLException e) {
        System.out.println("❌ Error en obtenerPorVenta(): " + e.getMessage());
    }
    return p;
}


}
