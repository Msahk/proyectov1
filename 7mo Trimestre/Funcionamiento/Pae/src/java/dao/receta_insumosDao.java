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

    // 🔹 Listar todas las relaciones receta-insumo
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
                ri.setEstado(rs.getString("estado"));

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

    // 🔹 Agregar nueva relación receta-insumo
    public boolean agregar(receta_insumos ri) {
        String sql = "INSERT INTO receta_insumos (id_rec, id_ins, cantidad, unidad, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ri.getId_rec());
            ps.setInt(2, ri.getId_ins());
            ps.setDouble(3, ri.getCantidad());
            ps.setString(4, ri.getUnidad());
            ps.setString(5, ri.getEstado() != null ? ri.getEstado() : "Activo");

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

    // 🔹 Actualizar relación existente
    public boolean actualizar(receta_insumos ri) {
        String sql = "UPDATE receta_insumos SET id_rec = ?, id_ins = ?, cantidad = ?, unidad = ?, estado = ? WHERE id_rec_ins = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ri.getId_rec());
            ps.setInt(2, ri.getId_ins());
            ps.setDouble(3, ri.getCantidad());
            ps.setString(4, ri.getUnidad());
            ps.setString(5, ri.getEstado());
            ps.setInt(6, ri.getId_rec_ins());

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

    // 🔹 Eliminar relación
    public void eliminar(receta_insumos ri) {
        String sql = "DELETE FROM receta_insumos WHERE id_rec_ins = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

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

    // 🔹 Obtener una relación por ID
    public receta_insumos obtenerPorId(int id) {
        receta_insumos ri = null;
        String sql = "SELECT * FROM receta_insumos WHERE id_rec_ins = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ri = new receta_insumos();
                    ri.setId_rec_ins(rs.getInt("id_rec_ins"));
                    ri.setId_rec(rs.getInt("id_rec"));
                    ri.setId_ins(rs.getInt("id_ins"));
                    ri.setCantidad(rs.getDouble("cantidad"));
                    ri.setUnidad(rs.getString("unidad"));
                    ri.setEstado(rs.getString("estado"));

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

    // 🔹 Verificar si ya existe una relación receta-insumo
    public boolean existeRelacion(int id_rec, int id_ins) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM receta_insumos WHERE id_rec = ? AND id_ins = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
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

    // 🔹 Listar todos los insumos asociados a una receta
    public List<receta_insumos> buscarPorReceta(int id_rec) {
        List<receta_insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM receta_insumos WHERE id_rec = ?";

        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_rec);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    receta_insumos ri = new receta_insumos();
                    ri.setId_rec_ins(rs.getInt("id_rec_ins"));
                    ri.setId_rec(rs.getInt("id_rec"));
                    ri.setId_ins(rs.getInt("id_ins"));
                    ri.setCantidad(rs.getDouble("cantidad"));
                    ri.setUnidad(rs.getString("unidad"));
                    ri.setEstado(rs.getString("estado"));

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

    // 🔹 Alias del método anterior
    public List<receta_insumos> listarPorReceta(int id_rec) {
        return buscarPorReceta(id_rec);
    }

    // 🔹 Listar insumos activos (solo los que están disponibles)
    public List<insumos> listarInsumosActivos() {
        List<insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos WHERE estado = 'Activo' ORDER BY nombre";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                insumos i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setEstado(rs.getString("estado"));

                // 🔹 Recalcular estado según stock actual (si tu clase lo implementa)
                try {
                    i.recalcularEstado();
                } catch (Exception e) {
                    // ignora si el método no existe o no es necesario
                }

                lista.add(i);
            }

        } catch (SQLException e) {
            System.out.println("Error en listarInsumosActivos(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }
    
   public boolean actualizarEstado(int id_rec_ins, String nuevoEstado) {
    String sql = "UPDATE receta_insumos SET estado = ? WHERE id_rec_ins = ?";
    
    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nuevoEstado);
        ps.setInt(2, id_rec_ins);
        ps.executeUpdate();

        System.out.println("✅ Estado de receta_insumos actualizado a '" + nuevoEstado + "' para ID: " + id_rec_ins);
        return true;

    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar estado en receta_insumos: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

public void sincronizarEstadosPorInsumo() {
    String sql =
        "UPDATE receta_insumos ri " +
        "INNER JOIN insumos i ON ri.id_ins = i.id_ins " +
        "SET ri.estado = CASE " +
        "    WHEN i.estado NOT IN ('Activo') THEN 'Agotado' " +
        "    ELSE 'Activo' " +
        "END";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        int filas = ps.executeUpdate();
        System.out.println("🔄 Sincronización completa: " + filas + " registros actualizados según estado de insumos.");

    } catch (SQLException e) {
        System.err.println("❌ Error al sincronizar estados de receta_insumos: " + e.getMessage());
        e.printStackTrace();
    }
}



}
