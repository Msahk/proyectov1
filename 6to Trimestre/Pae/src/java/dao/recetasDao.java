package dao;

import control.ConDB;
import modelo.recetas;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class recetasDao {

    PreparedStatement ps;
    ResultSet rs;

    // Listar todas las recetas
    public List<recetas> listar() {
        List<recetas> lista = new ArrayList<>();
        String sql = "SELECT * FROM recetas ORDER BY id_rec DESC";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                recetas r = new recetas();
                r.setId_rec(rs.getInt("id_rec"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error en listar recetas: " + e.getMessage());
        }
        return lista;
    }

    // Agregar receta
    public boolean agregar(recetas r) {
        String sql = "INSERT INTO recetas (nombre, descripcion) VALUES (?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar receta: " + e.getMessage());
        }
        return false;
    }

    // Actualizar receta
    public boolean actualizar(recetas r) {
        String sql = "UPDATE recetas SET nombre = ?, descripcion = ? WHERE id_rec = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getId_rec());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en actualizar receta: " + e.getMessage());
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
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId receta: " + e.getMessage());
        }
        return r;
    }

   // Eliminar receta por ID
public boolean eliminar(int idReceta) {
    String sql = "DELETE FROM recetas WHERE id_rec = ?";
    try (PreparedStatement ps = ConDB.conectar().prepareStatement(sql)) {
        ps.setInt(1, idReceta);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
