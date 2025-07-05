package models;

import config.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class clienteDao {

    private Conexion cn = new Conexion();
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // Listar todos los clientes
    public List<cliente> listar() {
        List<cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cliente c = new cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));
                c.setCategoriaCrediticia(rs.getString("categoriaCrediticia"));
                c.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                c.setLimiteCreditos(rs.getInt("limite_creditos")); // corregido
                c.setCreditosActuales(rs.getInt("creditos_actuales")); // corregido
                c.setIdUsuario(rs.getInt("idUsuario"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        } finally {
            cerrar();
        }

        return lista;
    }

    // Agregar cliente
    public boolean agregar(cliente c) {
        String sql = "INSERT INTO cliente (nombre, apellidos, telefono, direccion, categoriaCrediticia, fechaRegistro, limite_creditos, creditos_actuales, idUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCategoriaCrediticia());
            ps.setTimestamp(6, Timestamp.valueOf(c.getFechaRegistro()));
            ps.setInt(7, c.getLimiteCreditos()); // corregido
            ps.setInt(8, c.getCreditosActuales()); // corregido
            ps.setInt(9, c.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        } finally {
            cerrar();
        }
        return false;
    }

    // Obtener cliente por ID
    public cliente obtenerPorId(int id) {
        cliente c = null;
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));
                c.setCategoriaCrediticia(rs.getString("categoriaCrediticia"));
                c.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                c.setLimiteCreditos(rs.getInt("limite_creditos")); // corregido
                c.setCreditosActuales(rs.getInt("creditos_actuales")); // corregido
                c.setIdUsuario(rs.getInt("idUsuario"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        } finally {
            cerrar();
        }

        return c;
    }

    // Actualizar cliente
    public boolean actualizar(cliente c) {
        String sql = "UPDATE cliente SET nombre=?, apellidos=?, telefono=?, direccion=?, categoriaCrediticia=?, fechaRegistro=?, limite_creditos=?, creditos_actuales=?, idUsuario=? WHERE idCliente=?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCategoriaCrediticia());
            ps.setTimestamp(6, Timestamp.valueOf(c.getFechaRegistro()));
            ps.setInt(7, c.getLimiteCreditos()); // corregido
            ps.setInt(8, c.getCreditosActuales()); // corregido
            ps.setInt(9, c.getIdUsuario());
            ps.setInt(10, c.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        } finally {
            cerrar();
        }
        return false;
    }

    // Eliminar cliente
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM cliente WHERE idCliente=?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            cerrar();
        }
        return false;
    }

    // Cerrar conexiones
    private void cerrar() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
