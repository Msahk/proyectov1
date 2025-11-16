package dao;

import control.ConDB;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.historial;

public class historialDao {

    PreparedStatement ps;
    ResultSet rs;

    // 📋 Listar historial completo con nombre del insumo
    public List<historial> listar() {
        List<historial> lista = new ArrayList<>();
        String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                     "FROM historial h " +
                     "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                     "ORDER BY h.fecha DESC";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                historial h = new historial();
                h.setIdHist(rs.getInt("idHist"));
                h.setFecha(rs.getTimestamp("fecha"));
                h.setAccion(rs.getString("accion"));
                h.setNovedad(rs.getString("novedad"));
                h.setId_ins(rs.getInt("id_ins"));
                h.setId_detalle(rs.getInt("id_detalle"));
                h.setNombre_insumo(rs.getString("nombre_insumo"));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
        }
        return lista;
    }

    // ➕ Registrar una nueva acción en el historial
    public boolean agregar(historial h) {
        String sql = "INSERT INTO historial (fecha, accion, novedad, id_ins, id_detalle) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(h.getFecha().getTime()));
            ps.setString(2, h.getAccion());
            ps.setString(3, h.getNovedad());
            ps.setInt(4, h.getId_ins());
            if (h.getId_detalle() != null) {
                ps.setInt(5, h.getId_detalle());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado al historial"));
            return true;

        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el historial"));
        }
        return false;
    }

    // 🔍 Buscar historial por ID
    public historial obtenerPorId(int id) {
        historial h = null;
        String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                     "FROM historial h " +
                     "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                     "WHERE h.idHist=?";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                h = new historial();
                h.setIdHist(rs.getInt("idHist"));
                h.setFecha(rs.getTimestamp("fecha"));
                h.setAccion(rs.getString("accion"));
                h.setNovedad(rs.getString("novedad"));
                h.setId_ins(rs.getInt("id_ins"));
                h.setId_detalle(rs.getInt("id_detalle"));
                h.setNombre_insumo(rs.getString("nombre_insumo"));
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
        }
        return h;
    }

    // ✏️ Actualizar registro del historial
    public boolean actualizar(historial h) {
        String sql = "UPDATE historial SET fecha=?, accion=?, novedad=?, id_ins=?, id_detalle=? WHERE idHist=?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(h.getFecha().getTime()));
            ps.setString(2, h.getAccion());
            ps.setString(3, h.getNovedad());
            ps.setInt(4, h.getId_ins());
            if (h.getId_detalle() != null) {
                ps.setInt(5, h.getId_detalle());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, h.getIdHist());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Historial actualizado correctamente"));
            return true;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el historial"));
        }
        return false;
    }

    // 🗑️ Eliminar registro del historial
    public boolean eliminar(historial h) {
    String sql = "DELETE FROM historial WHERE idHist=?";
    try {
        ps = ConDB.conectar().prepareStatement(sql);
        ps.setInt(1, h.getIdHist());
        ps.executeUpdate();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro eliminado correctamente"));
        return true;
    } catch (SQLException e) {
        System.out.println("Error en eliminar(): " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro"));
        return false;
    }
}
    
    // ✅ Listar historial por insumo
public List<historial> listarPorInsumo(int id_ins) {
    List<historial> lista = new ArrayList<>();
    String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                 "FROM historial h " +
                 "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                 "WHERE h.id_ins = ? " +
                 "ORDER BY h.fecha DESC";
    try {
        ps = ConDB.conectar().prepareStatement(sql);
        ps.setInt(1, id_ins);
        rs = ps.executeQuery();
        while (rs.next()) {
            historial h = new historial();
            h.setIdHist(rs.getInt("idHist"));
            h.setFecha(rs.getTimestamp("fecha"));
            h.setAccion(rs.getString("accion"));
            h.setNovedad(rs.getString("novedad"));
            h.setId_ins(rs.getInt("id_ins"));
            h.setId_detalle(rs.getInt("id_detalle"));
            h.setNombre_insumo(rs.getString("nombre_insumo"));
            lista.add(h);
        }
    } catch (SQLException e) {
        System.out.println("Error en listarPorInsumo(): " + e.getMessage());
        e.printStackTrace();
    }
    return lista;
}




}
