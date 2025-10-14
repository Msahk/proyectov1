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
import modelo.receta_insumos;
import modelo.recetas;
import modelo.insumos;

public class receta_insumosDao {

    // 🔹 Listar todos los registros
    public List<receta_insumos> listar() {
        List<receta_insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM receta_insumos";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                receta_insumos ri = new receta_insumos();
                ri.setId_rec_ins(rs.getInt("id_rec_ins"));
                ri.setId_rec(rs.getInt("id_rec"));
                ri.setId_ins(rs.getInt("id_ins"));
                ri.setCantidad(rs.getDouble("cantidad"));
                ri.setUnidad(rs.getString("unidad"));

                // 🔹 Cargar objetos completos
                ri.setReceta(new recetasDao().obtenerPorId(rs.getInt("id_rec")));
                ri.setInsumo(new insumosDao().obtenerPorId(rs.getInt("id_ins")));

                lista.add(ri);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 Agregar nuevo registro
    public boolean agregar(receta_insumos ri) {
        String sql = "INSERT INTO receta_insumos (id_rec, id_ins, cantidad, unidad) VALUES (?, ?, ?, ?)";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ri.getId_rec());
            ps.setInt(2, ri.getId_ins());
            ps.setDouble(3, ri.getCantidad());
            ps.setString(4, ri.getUnidad());

            if (ps.executeUpdate() > 0) {
                new recetasDao().actualizarEstado(ri.getId_rec());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Actualizar registro
    public boolean actualizar(receta_insumos ri) {
        String sql = "UPDATE receta_insumos SET id_rec = ?, id_ins = ?, cantidad = ?, unidad = ? WHERE id_rec_ins = ?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ri.getId_rec());
            ps.setInt(2, ri.getId_ins());
            ps.setDouble(3, ri.getCantidad());
            ps.setString(4, ri.getUnidad());
            ps.setInt(5, ri.getId_rec_ins());

            if (ps.executeUpdate() > 0) {
                new recetasDao().actualizarEstado(ri.getId_rec());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Eliminar registro
    public void eliminar(receta_insumos ri) {
        String sql = "DELETE FROM receta_insumos WHERE id_rec_ins = ?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ri.getId_rec_ins());
            ps.executeUpdate();
            new recetasDao().actualizarEstado(ri.getId_rec());

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Relación receta-insumo eliminada exitosamente"));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar la relación"));
            e.printStackTrace();
        }
    }

    // 🔹 Obtener registro por ID
    public receta_insumos obtenerPorId(int id) {
        receta_insumos ri = null;
        String sql = "SELECT * FROM receta_insumos WHERE id_rec_ins = ?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ri = new receta_insumos();
                    ri.setId_rec_ins(rs.getInt("id_rec_ins"));
                    ri.setId_rec(rs.getInt("id_rec"));
                    ri.setId_ins(rs.getInt("id_ins"));
                    ri.setCantidad(rs.getDouble("cantidad"));
                    ri.setUnidad(rs.getString("unidad"));

                    // 🔹 Cargar objetos completos
                    ri.setReceta(new recetasDao().obtenerPorId(rs.getInt("id_rec")));
                    ri.setInsumo(new insumosDao().obtenerPorId(rs.getInt("id_ins")));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }

        return ri;
    }

    // 🔹 Validar duplicados
    public boolean existeRelacion(int id_rec, int id_ins) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM receta_insumos WHERE id_rec = ? AND id_ins = ?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_rec);
            ps.setInt(2, id_ins);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en existeRelacion(): " + e.getMessage());
            e.printStackTrace();
        }

        return existe;
    }

    // 🔹 Buscar insumos por receta
    public List<receta_insumos> buscarPorReceta(int id_rec) {
        List<receta_insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM receta_insumos WHERE id_rec = ?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_rec);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    receta_insumos ri = new receta_insumos();
                    ri.setId_rec_ins(rs.getInt("id_rec_ins"));
                    ri.setId_rec(rs.getInt("id_rec"));
                    ri.setId_ins(rs.getInt("id_ins"));
                    ri.setCantidad(rs.getDouble("cantidad"));
                    ri.setUnidad(rs.getString("unidad"));

                    // 🔹 Cargar objetos completos
                    ri.setReceta(new recetasDao().obtenerPorId(rs.getInt("id_rec")));
                    ri.setInsumo(new insumosDao().obtenerPorId(rs.getInt("id_ins")));

                    lista.add(ri);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en buscarPorReceta(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
    
    // 🔹 Listar insumos por receta (usado en producción)
public List<receta_insumos> listarPorReceta(int id_rec) {
    return buscarPorReceta(id_rec);
}
}
