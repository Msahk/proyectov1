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
import modelo.insumos;

public class insumosDao {

    PreparedStatement ps;
    ResultSet rs;

    // 🔹 Listar todos los insumos y actualizar estado automáticamente
    public List<insumos> listar() {
        List<insumos> lista = new ArrayList<>();

        try (Connection con = ConDB.conectar()) {
            String sql = "SELECT * FROM insumos ORDER BY nombre";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                insumos i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setEstado(rs.getString("estado"));

                // 🔸 Recalcular estado según stock
                i.recalcularEstado();

                // 🔸 Si cambió, actualiza en la BD
                if (!i.getEstado().equals(rs.getString("estado"))) {
                    actualizarEstado(i.getId_ins(), i.getEstado());
                }

                lista.add(i);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 Agregar un nuevo insumo
    public boolean agregar(insumos i) {
        i.recalcularEstado(); // antes de insertar, recalcula el estado

        String sql = "INSERT INTO insumos (nombre, unidad_medida, stock_min, stock_actual, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getUnidad_medida());
            ps.setDouble(3, i.getStock_min());
            ps.setDouble(4, i.getStock_actual());
            ps.setString(5, i.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
        }
        return false;
    }

    // 🔹 Actualizar insumo existente
    public boolean actualizar(insumos i) {
        i.recalcularEstado(); // antes de actualizar, valida nuevamente

        String sql = "UPDATE insumos SET nombre = ?, unidad_medida = ?, stock_min = ?, stock_actual = ?, estado = ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getUnidad_medida());
            ps.setDouble(3, i.getStock_min());
            ps.setDouble(4, i.getStock_actual());
            ps.setString(5, i.getEstado());
            ps.setInt(6, i.getId_ins());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Obtener insumo por ID
    public insumos obtenerPorId(int id) {
        insumos i = null;
        String sql = "SELECT * FROM insumos WHERE id_ins = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    // 🔹 Eliminar insumo
    public void eliminar(insumos i) {
        String sql = "DELETE FROM insumos WHERE id_ins = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setInt(1, i.getId_ins());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Insumo eliminado exitosamente"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando insumo"));
            e.printStackTrace();
        }
    }

    // 🔹 Verificar si existe un nombre duplicado
    public boolean existeNombre(String nombre) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM insumos WHERE nombre = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
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

    // 🔹 Actualizar estado manualmente
    public void actualizarEstado(int id_ins, String estado) {
        String sql = "UPDATE insumos SET estado = ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_ins);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en actualizarEstado(): " + e.getMessage());
        }
    }

    // 🔹 Descontar stock (por ejemplo al registrar una venta)
    public boolean descontarStock(Integer id_ins, double cantidadUsada) {
        String sql = "UPDATE insumos SET stock_actual = stock_actual - ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar()) {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, cantidadUsada);
            ps.setInt(2, id_ins);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error en descontarStock(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Listar solo los insumos activos
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
                lista.add(i);
            }

        } catch (SQLException e) {
            System.out.println("Error en listarInsumosActivos(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 Validar stock de insumos para producción (según receta)
    public boolean validarStockParaProduccion(int idReceta) {
        String sql = "SELECT i.nombre, i.stock_actual, i.stock_min " +
                     "FROM insumos i " +
                     "JOIN receta_insumos ri ON i.id_ins = ri.id_insumo " +
                     "WHERE ri.id_receta = ?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReceta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                double stockActual = rs.getDouble("stock_actual");
                double stockMin = rs.getDouble("stock_min");
                String nombre = rs.getString("nombre");

                if (stockActual <= 0 || stockActual < stockMin) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No se puede finalizar la producción",
                            "El insumo '" + nombre + "' no tiene stock suficiente."));
                    return false;
                }
            }
            return true; // Todos los insumos cumplen
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public insumos obtenerPorNombre(String nombre) {
    insumos i = null;
    String sql = "SELECT * FROM insumos WHERE nombre = ?";
    try (Connection con = ConDB.conectar()) {
        ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        rs = ps.executeQuery();

        if (rs.next()) {
            i = new insumos();
            i.setId_ins(rs.getInt("id_ins"));
            i.setNombre(rs.getString("nombre"));
            i.setUnidad_medida(rs.getString("unidad_medida"));
            i.setStock_min(rs.getDouble("stock_min"));
            i.setStock_actual(rs.getDouble("stock_actual"));
            i.setEstado(rs.getString("estado"));
        }
    } catch (Exception e) {
        System.out.println("Error en obtenerPorNombre(): " + e.getMessage());
    }
    return i;
}
    
    public List<insumos> listarConStockReal() {
    List<insumos> lista = listar(); // traemos todos los insumos
    detalle_insumoDao daoDetalle = new detalle_insumoDao();

    for (insumos i : lista) {
        double stockReal = daoDetalle.calcularStockActual(i.getId_ins());
        i.setStock_actual(stockReal); // actualizamos el stock_actual para la vista
        i.recalcularEstado();         // recalculamos estado según stock real
    }

    return lista;
}


}
