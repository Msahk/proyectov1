package dao;

import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import control.ConDB;
import modelo.produccion;
import modelo.usuarios;


public class produccionDao {

    // 🔹 LISTAR todas las producciones (incluye nombre completo del usuario)
    // 🔹 LISTAR todas las producciones con nombre del creador y del asignado
public List<produccion> listar() {
    List<produccion> lista = new ArrayList<>();
    String sql = "SELECT p.*, " +
                 "u1.nombres AS nombreUsuario, u1.apellidos AS apellidoUsuario, " +
                 "u2.nombres AS nombreAsignado, u2.apellidos AS apellidoAsignado " +
                 "FROM produccion p " +
                 "LEFT JOIN usuarios u1 ON p.id_usu = u1.id_usu " +
                 "LEFT JOIN usuarios u2 ON p.id_asignado = u2.id_usu " +
                 "ORDER BY p.fecha_hora DESC";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            produccion p = new produccion();
            p.setId_proc(rs.getInt("id_proc"));
            p.setEstado(rs.getString("estado"));
            p.setFecha_hora(rs.getTimestamp("fecha_hora"));
            p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
            p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
            p.setId_usu(rs.getInt("id_usu"));
            p.setId_asignado(rs.getInt("id_asignado"));
            p.setNombreUsuario(rs.getString("nombreUsuario"));
            p.setApellidoUsuario(rs.getString("apellidoUsuario"));
            p.setNombreAsignado(rs.getString("nombreAsignado"));
            p.setApellidoAsignado(rs.getString("apellidoAsignado"));
            lista.add(p);
        }

    } catch (SQLException e) {
        System.out.println("❌ Error en listar(): " + e.getMessage());
    }

    return lista;
}


 // 🔹 AGREGAR nueva producción
public int agregar(produccion p) {
    String sql = "INSERT INTO produccion (estado, id_usu, id_asignado, fecha_hora) VALUES (?, ?, ?, ?)";
    int idGenerado = -1;

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, p.getEstado());
        ps.setInt(2, p.getId_usu());
        ps.setInt(3, p.getId_asignado()); // 👈 nuevo campo: usuario asignado
        ps.setTimestamp(4, new java.sql.Timestamp(p.getFecha_hora().getTime()));

        int filas = ps.executeUpdate();

        if (filas > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    System.out.println("✅ Producción insertada con ID: " + idGenerado);
                }
            }
        } else {
            System.out.println("⚠️ No se insertó ninguna fila en la tabla producción.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error en agregar() - produccionDao: " + e.getMessage());
    }

    return idGenerado;
}


    // 🔹 ACTUALIZAR producción existente
    public boolean actualizar(produccion p) {
        String sql = "UPDATE produccion SET estado=?, id_usu=?, fecha_hora=?, fecha_aceptacion=?, fecha_finalizacion=? WHERE id_proc=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getEstado());
            ps.setInt(2, p.getId_usu());
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
    String sql = "SELECT p.*, " +
         "u1.nombres AS nombreUsuario, u1.apellidos AS apellidoUsuario, " +
         "u2.nombres AS nombreAsignado, u2.apellidos AS apellidoAsignado " +
         "FROM produccion p " +
         "LEFT JOIN usuarios u1 ON p.id_usu = u1.id_usu " +
         "LEFT JOIN usuarios u2 ON p.id_asignado = u2.id_usu " +
         "WHERE p.id_proc = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setEstado(rs.getString("estado"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
                p.setIdReceta(rs.getInt("idReceta"));
                p.setCantidad(rs.getDouble("cantidad"));
                p.setId_usu(rs.getInt("id_usu"));
                p.setId_asignado(rs.getInt("id_asignado"));

                // ✅ Datos del usuario creador
                p.setNombreUsuario(rs.getString("nombreUsuario"));
                p.setApellidoUsuario(rs.getString("apellidoUsuario"));

                // ✅ Datos del usuario asignado
                p.setNombreAsignado(rs.getString("nombreAsignado"));
                p.setApellidoAsignado(rs.getString("apellidoAsignado"));
            }
        }

    } catch (SQLException e) {
        System.out.println("❌ Error en obtenerPorId(): " + e.getMessage());
    }

    return p;
}


    // 🔹 ELIMINAR producción
   // 🔹 ELIMINAR producción por ID
public boolean eliminar(int idProc) {
    String sql = "DELETE FROM produccion WHERE id_proc=?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idProc);
        int filas = ps.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        System.out.println("❌ Error en eliminar(): " + e.getMessage());
        return false;
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
            default:
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
            return false;
        }
    }

    // 🔹 Obtener producción asociada a una venta (con datos del usuario)
    public produccion obtenerPorVenta(int idVenta) {
    produccion p = null;
    String sql = "SELECT p.*, " +
         "u1.nombres AS nombreUsuario, u1.apellidos AS apellidoUsuario, " +
         "u2.nombres AS nombreAsignado, u2.apellidos AS apellidoAsignado " +
         "FROM produccion p " +
         "JOIN venta_produccion vp ON p.id_proc = vp.idProduccion " +
         "LEFT JOIN usuarios u1 ON p.id_usu = u1.id_usu " +
         "LEFT JOIN usuarios u2 ON p.id_asignado = u2.id_usu " +
         "WHERE vp.idVenta = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idVenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setEstado(rs.getString("estado"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
                p.setIdReceta(rs.getInt("idReceta"));
                p.setCantidad(rs.getDouble("cantidad"));
                p.setId_usu(rs.getInt("id_usu"));
                p.setId_asignado(rs.getInt("id_asignado"));

                // ✅ Datos del usuario creador
                p.setNombreUsuario(rs.getString("nombreUsuario"));
                p.setApellidoUsuario(rs.getString("apellidoUsuario"));

                // ✅ Datos del usuario asignado
                p.setNombreAsignado(rs.getString("nombreAsignado"));
                p.setApellidoAsignado(rs.getString("apellidoAsignado"));
            }
        }
    } catch (SQLException e) {
        System.out.println("❌ Error en obtenerPorVenta(): " + e.getMessage());
    }
    return p;
}
    
    // 🔹 LISTAR producciones según el usuario logueado
public List<produccion> listarPorUsuario(usuarios usuario) {
    List<produccion> lista = new ArrayList<>();

    String sqlBase = "SELECT p.*, " +
            "u1.nombres AS nombreUsuario, u1.apellidos AS apellidoUsuario, " +
            "u2.nombres AS nombreAsignado, u2.apellidos AS apellidoAsignado " +
            "FROM produccion p " +
            "LEFT JOIN usuarios u1 ON p.id_usu = u1.id_usu " +
            "LEFT JOIN usuarios u2 ON p.id_asignado = u2.id_usu ";

    String sql;

    // 🔸 Si el usuario tiene rol 'EP', solo ve sus producciones asignadas
    if ("EP".equalsIgnoreCase(usuario.getRol())) {
        sql = sqlBase + "WHERE p.id_asignado = ? ORDER BY p.fecha_hora DESC";
    } else {
        // 🔸 Los demás roles (A, EV, etc.) ven todas
        sql = sqlBase + "ORDER BY p.fecha_hora DESC";
    }

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        if ("EP".equalsIgnoreCase(usuario.getRol())) {
            ps.setInt(1, usuario.getIdUsu()); // Filtra solo las del usuario actual
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                produccion p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setEstado(rs.getString("estado"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setFecha_aceptacion(rs.getTimestamp("fecha_aceptacion"));
                p.setFecha_finalizacion(rs.getTimestamp("fecha_finalizacion"));
                p.setId_usu(rs.getInt("id_usu"));
                p.setId_asignado(rs.getInt("id_asignado"));
                p.setNombreUsuario(rs.getString("nombreUsuario"));
                p.setApellidoUsuario(rs.getString("apellidoUsuario"));
                p.setNombreAsignado(rs.getString("nombreAsignado"));
                p.setApellidoAsignado(rs.getString("apellidoAsignado"));
                lista.add(p);
            }
        }

    } catch (SQLException e) {
        System.out.println("❌ Error en listarPorUsuario(): " + e.getMessage());
    }

    return lista;
}


}
