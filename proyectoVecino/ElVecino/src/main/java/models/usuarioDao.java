package models;

import config.Conexion;
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

    // Validar usuario por email y contraseña
    public usuario Validar(String email, String pass) throws ClassNotFoundException {
        usuario obj_usu = null;
        String sql = "SELECT docUsuario, nombreUsuario, rol, password, email FROM usuario WHERE email = ? AND password = ?";

        try {
            conn = cn.conexion();
            if (conn == null) {
                System.out.println("Error: Conexión fallida a la base de datos.");
                return null;
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                obj_usu = new usuario();
                obj_usu.setDocUsuario(rs.getString("docUsuario"));
                obj_usu.setNombreUsuario(rs.getString("nombreUsuario"));
                obj_usu.setRol(rs.getString("rol"));
                obj_usu.setPassword(rs.getString("password"));
                obj_usu.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error en Validar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }

        return obj_usu;
    }

    // Listar todos los usuarios
    public List<usuario> listar() {
        List<usuario> lista = new ArrayList<>();
        String sql = "SELECT docUsuario, nombreUsuario, rol, password, email FROM usuario";

        try {
            conn = cn.conexion();
            if (conn == null) {
                System.out.println("Error: Conexión fallida a la base de datos.");
                return lista;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuario obj = new usuario();
                obj.setDocUsuario(rs.getString("docUsuario"));
                obj.setNombreUsuario(rs.getString("nombreUsuario"));
                obj.setRol(rs.getString("rol"));
                obj.setPassword(rs.getString("password"));
                obj.setEmail(rs.getString("email"));
                lista.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }

        return lista;
    }

    // Agregar nuevo usuario
    public boolean agregar(usuario u) {
        String sql = "INSERT INTO usuario (docUsuario, nombreUsuario, rol, password, email) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getDocUsuario());
            ps.setString(2, u.getNombreUsuario());
            ps.setString(3, u.getRol());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Actualizar un usuario existente
    public boolean actualizar(usuario u) {
        String sql = "UPDATE usuario SET nombreUsuario = ?, rol = ?, password = ?, email = ? WHERE docUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getRol());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getDocUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Eliminar un usuario por documento
    public boolean eliminar(String docUsuario) {
        String sql = "DELETE FROM usuario WHERE docUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, docUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en eliminar(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
        return false;
    }

    // Obtener un usuario por documento
    public usuario obtenerPorDocumento(String doc) {
        usuario u = null;
        String sql = "SELECT * FROM usuario WHERE docUsuario = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, doc);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new usuario();
                u.setDocUsuario(rs.getString("docUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setRol(rs.getString("rol"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorDocumento(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
        return u;
    }

    // Cerrar conexión, statement y resultset
    private void cerrarTodo() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
