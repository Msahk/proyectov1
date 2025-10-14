package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import control.ConDB;
import modelo.produccion;
import java.sql.Statement;


public class produccionDao {
    PreparedStatement ps;
    ResultSet rs;

    // Listar todas las producciones
    public List<produccion> listar() {
        List<produccion> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM produccion ORDER BY fecha_produccion DESC";
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                produccion p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getInt("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // Agregar nueva producción
    // Agregar nueva producción y retornar el ID generado
// en dao/produccionDao.java
public int agregar(produccion p) {
    String sql = "INSERT INTO produccion (fecha_produccion, estado, usuario, fecha_hora) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = ConDB.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setDate(1, new java.sql.Date(p.getFecha_produccion().getTime()));
        ps.setString(2, p.getEstado());
        ps.setInt(3, p.getUsuario());
        ps.setTimestamp(4, new java.sql.Timestamp(p.getFecha_hora().getTime()));
        int affected = ps.executeUpdate();
        if (affected > 0) {
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (gk.next()) {
                    return gk.getInt(1); // ID generado
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en agregar(): " + e.getMessage());
        e.printStackTrace();
    }
    return -1;
}



    // Actualizar una producción
    public boolean actualizar(produccion p) {
        String sql = "UPDATE produccion SET fecha_produccion = ?, estado = ?, usuario = ?, fecha_hora = ? WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(p.getFecha_produccion().getTime()));
            ps.setString(2, p.getEstado());
            ps.setInt(3, p.getUsuario());
            ps.setTimestamp(4, new java.sql.Timestamp(p.getFecha_hora().getTime()));
            ps.setInt(5, p.getId_proc());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Obtener una producción por ID
    public produccion obtenerPorId(int id) {
        produccion p = null;
        String sql = "SELECT * FROM produccion WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getInt("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }
        return p;
    }

    // Eliminar una producción
    public void eliminar(produccion p) {
        String sql = "DELETE FROM produccion WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, p.getId_proc());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producción eliminada exitosamente"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar producción"));
            e.printStackTrace();
        }
    }

    // Cambiar estado de producción (por ejemplo Pendiente / Finalizada)
    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE produccion SET estado = ? WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en cambiarEstado(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si ya existe una producción para una fecha específica
    public boolean existeFecha(java.util.Date fecha) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM produccion WHERE fecha_produccion = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(fecha.getTime()));
            rs = ps.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error en existeFecha(): " + e.getMessage());
        }
        return existe;
    }
}
