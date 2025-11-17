package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import control.ConDB;
import control.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.usuarios;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;

public class usuariosDao {

    PreparedStatement ps;
    ResultSet rs;

    public List<usuarios> listar() {
        List<usuarios> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM usuarios order by estado";
            ps = ConDB.conectar().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                usuarios obj = new usuarios();
                obj.setIdUsu(rs.getInt("id_usu"));
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
        }

        return lista;
    }

    public boolean agregar(usuarios u) {
        String sql = "INSERT INTO usuarios (documento, nombres, apellidos, telefono, direccion, correo, rol, password, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'A')";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(2, u.getNombres());
            ps.setInt(1, u.getDocumento());
            ps.setLong(4, u.getTelefono());
            ps.setString(3, u.getApellidos());
            ps.setString(5, u.getDireccion());
            ps.setString(6, u.getCorreo());
            ps.setString(7, u.getRol());
            String pw = Utilidades.encriptar(u.getPassword());
            ps.setString(8, pw);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(usuarios u) {
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, rol = ?, password = ?, correo = ?, direccion = ?, telefono = ?, documento = ? WHERE id_usu = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);

            ps.setString(1, u.getNombres());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getRol());

            // Hashear la contraseña recibida
            String pw = Utilidades.encriptar(u.getPassword());
            ps.setString(4, pw);

            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getDireccion());
            ps.setLong(7, u.getTelefono());
            ps.setInt(8, u.getDocumento());
            ps.setInt(9, u.getIdUsu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public usuarios obtenerPorId(usuarios u) {
        usuarios usu = null;
        String sql = "SELECT * FROM usuarios WHERE id_usu = ?";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, u.getIdUsu());
            rs = ps.executeQuery();

            if (rs.next()) {
                usu = new usuarios();
                usu.setIdUsu(rs.getInt("id_usu"));
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

    public usuarios obtenerPorId(int id) {
        usuarios usu = null;
        String sql = "SELECT * FROM usuarios WHERE id_usu = ?";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                usu = new usuarios();
                usu.setIdUsu(rs.getInt("id_usu"));
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

    public void eliminar(usuarios u) {
        String sql = "DELETE FROM usuarios WHERE id_usu=?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, u.getIdUsu());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producto eliminado exitosamente"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando"));
        }
    }



    public boolean cambiarEstado(usuarios u) {
        String selectSql = "SELECT estado FROM usuarios WHERE id_usu = ?";
        String updateSql = "UPDATE usuarios SET estado = ? WHERE id_usu = ?";

        try (Connection conn = ConDB.conectar(); PreparedStatement psSelect = conn.prepareStatement(selectSql)) {

            psSelect.setInt(1, u.getIdUsu());
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    String estadoActual = rs.getString("estado");
                    String nuevoEstado = "A".equalsIgnoreCase(estadoActual) ? "I" : "A";

                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setString(1, nuevoEstado);
                        psUpdate.setInt(2, u.getIdUsu());
                        return psUpdate.executeUpdate() > 0;
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeCorreoODocumento(String correo, int documento) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ? OR documento = ?";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, correo);
            ps.setInt(2, documento);
            rs = ps.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error en existeCorreoODocumento(): " + e.getMessage());
            e.printStackTrace();
        }

        return existe;
    }

    public List<usuarios> listarPorRol(String rolBuscado) {
        List<usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, rolBuscado);
            rs = ps.executeQuery();
            while (rs.next()) {
                usuarios u = new usuarios();
                u.setIdUsu(rs.getInt("id_usu"));
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
        }
        return lista;
    }

    public usuarios olvidar(String correo, int doc) throws ClassNotFoundException {
        usuarios usu = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND documento = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, correo);
            ps.setInt(2, doc);
            rs = ps.executeQuery();

            if (rs.next()) {
                usu = new usuarios();
                usu.setIdUsu(rs.getInt("id_usu"));
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
        }
        return usu;
    }

    public boolean actualizarContra(int id, String passNueva) {
        String sql = "UPDATE usuarios SET password = ? WHERE id_usu = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            String pw = Utilidades.encriptar(passNueva);
            ps.setString(1, pw);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

 public usuarios Validar(String email, String pass) {
    usuarios obj_usu = null;
    String sql = "SELECT * FROM usuarios WHERE correo = ? OR documento = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ps.setString(2, email);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int idUsu = rs.getInt("id_usu");
                String storedHash = rs.getString("password");

                // Debug para comprobar valores directamente desde ResultSet
                System.out.println("DEBUG VALIDAR -> ResultSet: nombres=" + rs.getString("nombres")
                        + ", id_usu=" + idUsu + ", correo=" + rs.getString("correo"));

                // Verificamos contraseña con BCrypt
                if (BCrypt.checkpw(pass, storedHash)) {
                    obj_usu = new usuarios();
                    // Asignar TODOS los campos, incluyendo el ID
                    obj_usu.setIdUsu(idUsu);
                    obj_usu.setDocumento(rs.getInt("documento"));
                    obj_usu.setNombres(rs.getString("nombres"));
                    obj_usu.setApellidos(rs.getString("apellidos"));
                    obj_usu.setTelefono(rs.getLong("telefono"));
                    obj_usu.setDireccion(rs.getString("direccion"));
                    obj_usu.setCorreo(rs.getString("correo"));
                    obj_usu.setRol(rs.getString("rol"));
                    obj_usu.setPassword(storedHash);
                    obj_usu.setEstado(rs.getString("estado"));

                    System.out.println("DEBUG >>> Usuario validado: " 
                        + obj_usu.getNombres() + " (ID=" + obj_usu.getIdUsu() + ")");
                } else {
                    System.out.println("⚠️ Contraseña incorrecta para correo/documento: " + email);
                }
            } else {
                System.out.println("❌ No existe usuario con correo/documento: " + email);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error en Validar(): " + e.getMessage());
        e.printStackTrace();
    }

    return obj_usu;
}



    public String obtenerNombrePorId(int idUsuario) {
        String nombre = "";
        String sql = "SELECT nombres, apellidos FROM usuarios WHERE id_usu = ?";
        try (PreparedStatement ps = ConDB.conectar().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String n = rs.getString("nombres");
                    String a = rs.getString("apellidos");
                    if (n == null) {
                        n = "";
                    }
                    if (a == null) {
                        a = "";
                    }
                    nombre = (n + " " + a).trim();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return nombre;
    }
    
    
public List<usuarios> listarUsuariosVenta() {
    List<usuarios> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios WHERE (rol = 'EV' OR rol = 'A') AND estado = 'A'";
    try (Connection cn = ConDB.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            usuarios u = new usuarios();
            u.setIdUsu(rs.getInt("id_usu"));
            u.setNombres(rs.getString("nombres"));
            u.setApellidos(rs.getString("apellidos"));
            lista.add(u);
        }

    } catch (SQLException e) {
        System.out.println("Error en listarUsuariosVenta(): " + e.getMessage());
    }
    return lista;
}

public usuarios buscarPorCorreo(String correo) {
    usuarios u = null;

    String sql = "SELECT * FROM usuarios WHERE correo = ? LIMIT 1";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, correo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            u = new usuarios();
            u.setIdUsu(rs.getInt("idUsu"));
            u.setDocumento(rs.getInt("documento"));
            u.setNombres(rs.getString("nombres"));
            u.setApellidos(rs.getString("apellidos"));
            u.setTelefono(rs.getLong("telefono"));
            u.setDireccion(rs.getString("direccion"));
            u.setCorreo(rs.getString("correo"));
            u.setRol(rs.getString("rol"));
            u.setPassword(rs.getString("password"));
            u.setEstado(rs.getString("estado"));
        }

    } catch (Exception e) {
        System.out.println("Error buscarPorCorreo: " + e.getMessage());
    }

    return u;
}


public boolean actualizarPassword(usuarios u) {

    String sql = "UPDATE usuarios SET password = ? WHERE idUsu = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, u.getPassword());
        ps.setInt(2, u.getIdUsu());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        System.out.println("Error actualizarPassword: " + e.getMessage());
        return false;
    }
}


}
