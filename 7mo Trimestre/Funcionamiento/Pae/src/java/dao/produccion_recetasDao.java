package dao;

import control.ConDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.produccion_recetas;

public class produccion_recetasDao {

    public boolean agregar(produccion_recetas pr) {
        String sql = "INSERT INTO produccion_recetas (id_produccion, id_rec, cantidad) VALUES (?, ?, ?)";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pr.getId_produccion());
            ps.setInt(2, pr.getId_rec());
            ps.setInt(3, pr.getCantidad());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id_detalle) {
        String sql = "DELETE FROM produccion_recetas WHERE id_detalle = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_detalle);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(produccion_recetas pr) {
        String sql = "UPDATE produccion_recetas SET id_rec = ?, cantidad = ? WHERE id_detalle = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pr.getId_rec());
            ps.setInt(2, pr.getCantidad());
            ps.setInt(3, pr.getId_detalle());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<produccion_recetas> buscarPorProduccion(int id_produccion) {
        List<produccion_recetas> lista = new ArrayList<>();
        String sql = "SELECT * FROM produccion_recetas WHERE id_produccion = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_produccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    produccion_recetas pr = new produccion_recetas();
                    pr.setId_detalle(rs.getInt("id_detalle"));
                    pr.setId_produccion(rs.getInt("id_produccion"));
                    pr.setId_rec(rs.getInt("id_rec"));
                    pr.setCantidad(rs.getInt("cantidad"));
                    lista.add(pr);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existeRelacion(int id_produccion, int id_rec) {
        String sql = "SELECT COUNT(*) FROM produccion_recetas WHERE id_produccion = ? AND id_rec = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_produccion);
            ps.setInt(2, id_rec);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Eliminar todas las recetas asociadas a una producción
public boolean eliminarPorProduccion(int idProduccion) {
    String sql = "DELETE FROM produccion_recetas WHERE id_produccion = ?";
    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idProduccion);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("❌ Error en eliminarPorProduccion(): " + e.getMessage());
        return false;
    }
}

}
