package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.conexion;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class usuariosDao {

    conexion cn = new conexion();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Validar usuario por email y contraseña
    public usuarios Validar(String email, String pass) throws ClassNotFoundException {
    usuarios obj_usu = null;
    String sql = "SELECT * FROM usuarios WHERE correo = ? OR documento = ?";
    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, email); // por si es documento
        rs = ps.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("password");

            if (BCrypt.checkpw(pass, storedHash)) {
                obj_usu = new usuarios();
                obj_usu.setId_usu(rs.getInt("id_usu"));
                obj_usu.setDocumento(rs.getInt("documento"));
                obj_usu.setNombres(rs.getString("nombres"));
                obj_usu.setApellidos(rs.getString("apellidos"));
                obj_usu.setTelefono(rs.getLong("telefono"));
                obj_usu.setDireccion(rs.getString("direccion"));
                obj_usu.setCorreo(rs.getString("correo"));
                obj_usu.setRol(rs.getString("rol"));
                obj_usu.setPassword(storedHash);
                obj_usu.setEstado(rs.getString("estado"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en Validar(): " + e.getMessage());
    } finally {
        cerrarTodo();
    }
    return obj_usu;
}

    

    // Listar todos los usuarios
    public List<usuarios> listar() {
        List<usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios order by estado";

        try {
            conn = cn.conexion();
            if (conn == null) {
                System.out.println("Error: Conexión fallida a la base de datos.");
                return lista;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios obj = new usuarios();
                obj.setId_usu(rs.getInt("id_usu"));
                obj.setDocumento(rs.getInt("documento"));
                obj.setNombres(rs.getString("nombres"));
                obj.setApellidos(rs.getString("apellidos"));
                obj.setTelefono(rs.getLong("telefono"));
                obj.setDireccion(rs.getString("direccion"));
                obj.setCorreo(rs.getString("correo"));
                obj.setRol(rs.getString("rol"));
                obj.setEstado(rs.getString("estado"));
                obj.setPassword(rs.getString("password"));
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

    

public boolean agregar(usuarios u) {
    String sql = "INSERT INTO usuarios (documento, nombres, apellidos, telefono, direccion, correo, rol, password, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'A')";
    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setInt(1, u.getDocumento());
        ps.setLong(4, u.getTelefono());
        ps.setString(2, u.getNombres());
        ps.setString(3, u.getApellidos());
        ps.setString(5, u.getDireccion());
        ps.setString(6, u.getCorreo());
        ps.setString(7, u.getRol());

        // Encriptar la contraseña
        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
        ps.setString(8, hashedPassword);

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error en agregar(): " + e.getMessage());
    } finally {
        cerrarTodo();
    }
    return false;
}



            public boolean actualizar(usuarios u) {
                String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, rol = ?, password = ?, correo = ?, direccion = ?, telefono = ?, documento = ? WHERE id_usu = ?";
                try {
                    conn = cn.conexion();
                    ps = conn.prepareStatement(sql);

                    ps.setString(1, u.getNombres());
                    ps.setString(2, u.getApellidos());
                    ps.setString(3, u.getRol());

                    // Hashear la contraseña recibida
                    String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
                    ps.setString(4, hashedPassword);

                    ps.setString(5, u.getCorreo());
                    ps.setString(6, u.getDireccion());
                    ps.setLong(7, u.getTelefono());
                    ps.setInt(8, u.getDocumento());
                    ps.setInt(9, u.getId_usu());

                    return ps.executeUpdate() > 0;

                } catch (SQLException e) {
                    System.out.println("Error en actualizar(): " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    cerrarTodo();
                }
                return false;
            }

    
    public usuarios obtenerPorId(int id) throws ClassNotFoundException {
    usuarios usu = null;
    String sql = "SELECT * FROM usuarios WHERE id_usu = ?";

    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            usu = new usuarios();
            usu.setId_usu(rs.getInt("id_usu"));
            usu.setDocumento(rs.getInt("documento"));
            usu.setNombres(rs.getString("nombres"));
            usu.setApellidos(rs.getString("apellidos"));
            usu.setTelefono(rs.getLong("telefono"));
            usu.setDireccion(rs.getString("direccion"));
            usu.setCorreo(rs.getString("correo"));
            usu.setRol(rs.getString("rol"));
            usu.setPassword(rs.getString("password"));
            usu.setEstado(rs.getString("estado"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return usu;
}
    

    // Eliminar un usuario por documento
        public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usu=?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        
   public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE usuarios SET estado = ? WHERE id_usu = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
    return false;
}
   
   
   public boolean existeCorreoODocumento(String correo, int documento) {
    boolean existe = false;
    String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ? OR documento = ?";

    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setInt(2, documento);
        rs = ps.executeQuery();

        if (rs.next()) {
            existe = rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error en existeCorreoODocumento(): " + e.getMessage());
        e.printStackTrace();
    } finally {
        cerrarTodo();
    }

    return existe;
}
   
   
   public List<usuarios> listarPorRol(String rolBuscado) {
    List<usuarios> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios WHERE rol = ?";
    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setString(1, rolBuscado);
        rs = ps.executeQuery();
        while (rs.next()) {
            usuarios u = new usuarios();
            u.setId_usu(rs.getInt("id_usu"));
            u.setDocumento(rs.getInt("documento"));
            u.setNombres(rs.getString("nombres"));
            u.setApellidos(rs.getString("apellidos"));
            u.setTelefono(rs.getLong("telefono"));
            u.setDireccion(rs.getString("direccion"));
            u.setCorreo(rs.getString("correo"));
            u.setRol(rs.getString("rol"));
            u.setEstado(rs.getString("estado"));
            u.setPassword(rs.getString("password"));
            lista.add(u);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar por rol: " + e.getMessage());
    } finally {
        cerrarTodo();
    }
    return lista;
}

   
   public usuarios olvidar(String correo, int doc) throws ClassNotFoundException{
       usuarios usu = null;
    String sql = "SELECT * FROM usuarios WHERE correo = ? AND documento = ?";
    try {
        conn = cn.conexion();
        ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setInt(2, doc);
        rs = ps.executeQuery();


            if (rs.next()) {
                usu = new usuarios();
                usu.setId_usu(rs.getInt("id_usu"));
                usu.setDocumento(rs.getInt("documento"));
                usu.setNombres(rs.getString("nombres"));
                usu.setApellidos(rs.getString("apellidos"));
                usu.setTelefono(rs.getLong("telefono"));
                usu.setDireccion(rs.getString("direccion"));
                usu.setCorreo(rs.getString("correo"));
                usu.setRol(rs.getString("rol"));
                usu.setPassword(rs.getString("password"));
                usu.setEstado(rs.getString("estado"));
            }
    } catch (SQLException e) {
        System.out.println("Error en Validar(): " + e.getMessage());
    } finally {
        cerrarTodo();
    }
    return usu;
   }
   
   
   public boolean actualizarContra(int id, String passNueva) {
        String sql = "UPDATE usuarios SET password = ? WHERE id_usu = ?";
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            String hashedPassword = BCrypt.hashpw(passNueva, BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarTodo();
        }
    return false;
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
