package models;

import config.Conexion;
import utils.Encriptador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDao {

    Conexion cn = new Conexion();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Validar usuario por email y contraseña (encriptada)
    public usuario Validar(String email, String pass) throws ClassNotFoundException {
        usuario obj_usu = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, Encriptador.encriptarSHA256(pass));
            rs = ps.executeQuery();

            if (rs.next()) {
                obj_usu = new usuario();
                obj_usu.setIdUsuario(rs.getInt("idUsuario"));
                obj_usu.setNombreUsuario(rs.getString("nombreUsuario"));
                obj_usu.setRol(rs.getString("rol"));
                obj_usu.setPassword(rs.getString("password"));
                obj_usu.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error en Validar(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }

        return obj_usu;
    }

    // Listar todos los usuarios
    public List<usuario> listar() {
        List<usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuario u = new usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setRol(rs.getString("rol"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }

        return lista;
    }

    // Agregar nuevo usuario (contraseña encriptada)
    public boolean agregar(usuario u) {
        String sql = "INSERT INTO usuario (nombreUsuario, rol, password, email) VALUES (?, ?, ?, ?)";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getRol());
            ps.setString(3, Encriptador.encriptarSHA256(u.getPassword()));
            ps.setString(4, u.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Actualizar un usuario
    public boolean actualizar(usuario u) {
        String sql = "UPDATE usuario SET nombreUsuario = ?, rol = ?, password = ?, email = ? WHERE idUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getRol());
            ps.setString(3, Encriptador.encriptarSHA256(u.getPassword()));
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Eliminar un usuario por ID
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en eliminar(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Obtener un usuario por ID
    public usuario obtenerPorId(int id) {
        usuario u = null;
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setRol(rs.getString("rol"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
        } finally {
            cerrarTodo();
        }
        return u;
    }

    // Cerrar conexiones
    private void cerrarTodo() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error cerrando recursos: " + e.getMessage());
        }
    }
}
