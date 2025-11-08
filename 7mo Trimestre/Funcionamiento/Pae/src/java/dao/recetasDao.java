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
import modelo.recetas;

public class recetasDao {

    PreparedStatement ps;
    ResultSet rs;

    // Listar todas las recetas
    public List<recetas> listar() {
        List<recetas> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM recetas ORDER BY nombre";
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                recetas r = new recetas();
                r.setId_rec(rs.getInt("id_rec"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setPrecio(rs.getDouble("precio")); // ✅ Nuevo campo
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Agregar nueva receta
    public boolean agregar(recetas r) {
        String sql = "INSERT INTO recetas (nombre, descripcion, precio, estado) VALUES (?, ?, ?, 'Activo')";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setDouble(3, r.getPrecio()); // ✅ Nuevo campo
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar receta
    public boolean actualizar(recetas r) {
        String sql = "UPDATE recetas SET nombre = ?, descripcion = ?, precio = ? WHERE id_rec = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setDouble(3, r.getPrecio()); // ✅ Nuevo campo
            ps.setInt(4, r.getId_rec());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Obtener receta por ID
    public recetas obtenerPorId(int id) {
        recetas r = null;
        String sql = "SELECT * FROM recetas WHERE id_rec = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                r = new recetas();
                r.setId_rec(rs.getInt("id_rec"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setPrecio(rs.getDouble("precio")); // ✅ Nuevo campo
                r.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }
        return r;
    }

    // Eliminar receta
    public void eliminar(recetas r) {
        String sql = "DELETE FROM recetas WHERE id_rec = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, r.getId_rec());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Receta eliminada exitosamente"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar receta"));
            e.printStackTrace();
        }
    }

    // Verificar si existe nombre
    public boolean existeNombre(String nombre) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM recetas WHERE nombre = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error en existeNombre(): " + e.getMessage());
        }
        return existe;
    }

    // Buscar recetas por nombre
    public List<recetas> buscarPorNombre(String nombre) {
        List<recetas> lista = new ArrayList<>();
        String sql = "SELECT * FROM recetas WHERE nombre LIKE ? ORDER BY nombre";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recetas r = new recetas();
                    r.setId_rec(rs.getInt("id_rec"));
                    r.setNombre(rs.getString("nombre"));
                    r.setDescripcion(rs.getString("descripcion"));
                    r.setPrecio(rs.getDouble("precio")); // ✅ Nuevo campo
                    r.setEstado(rs.getString("estado"));
                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en buscarPorNombre(): " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar estado automático
    public boolean actualizarEstado(int id_rec) {
        String sql = "UPDATE recetas r SET estado = "
                + "(SELECT CASE WHEN EXISTS ( "
                + "SELECT 1 FROM receta_insumos ri "
                + "INNER JOIN insumos i ON ri.id_ins = i.id_ins "
                + "WHERE ri.id_rec = r.id_rec AND i.estado = 'Inactivo') "
                + "THEN 'Inactivo' ELSE 'Activo' END) "
                + "WHERE id_rec = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_rec);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizarEstado(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Listar solo las recetas activas
    public List<recetas> listarActivas() {
        List<recetas> lista = new ArrayList<>();
        String sql = "SELECT * FROM recetas WHERE estado = 'Activo' ORDER BY nombre";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                recetas r = new recetas();
                r.setId_rec(rs.getInt("id_rec"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setPrecio(rs.getDouble("precio")); // ✅ Nuevo campo
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error en listarActivas(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
}
