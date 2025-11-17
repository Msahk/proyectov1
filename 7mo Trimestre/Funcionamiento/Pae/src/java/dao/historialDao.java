package dao;

import control.ConDB;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.historial;

public class historialDao {

    // Listar todo el historial
    public List<historial> listar() {
        List<historial> lista = new ArrayList<>();
        String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                     "FROM historial h " +
                     "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                     "ORDER BY h.fecha DESC";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                historial h = new historial();
                h.setIdHist(rs.getInt("idHist"));
                h.setFecha(rs.getTimestamp("fecha"));
                h.setAccion(rs.getString("accion"));
                h.setEstado(rs.getString("estado"));
                h.setCantidad(rs.getInt("cantidad"));
                h.setStockActual(rs.getInt("stock_actual"));
                h.setNovedad(rs.getString("novedad"));
                h.setId_ins(rs.getInt("id_ins"));
                h.setId_detalle(rs.getInt("id_detalle"));
                h.setNombre_insumo(rs.getString("nombre_insumo"));
                lista.add(h);
            }

        } catch (SQLException e) {
            System.err.println("Error en listar(): " + e.getMessage());
        }
        return lista;
    }

    // Listar historial por insumo
    public List<historial> listarPorInsumo(int id_ins) {
        List<historial> lista = new ArrayList<>();
        String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                     "FROM historial h " +
                     "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                     "WHERE h.id_ins=? " +
                     "ORDER BY h.fecha DESC";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_ins);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial h = new historial();
                    h.setIdHist(rs.getInt("idHist"));
                    h.setFecha(rs.getTimestamp("fecha"));
                    h.setAccion(rs.getString("accion"));
                    h.setEstado(rs.getString("estado"));
                    h.setCantidad(rs.getInt("cantidad"));
                    h.setStockActual(rs.getInt("stock_actual"));
                    h.setNovedad(rs.getString("novedad"));
                    h.setId_ins(rs.getInt("id_ins"));
                    h.setId_detalle(rs.getInt("id_detalle"));
                    h.setNombre_insumo(rs.getString("nombre_insumo"));
                    lista.add(h);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en listarPorInsumo(): " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Obtener historial por ID
    public historial obtenerPorId(int id) {
        historial h = null;
        String sql = "SELECT h.*, i.nombre AS nombre_insumo " +
                     "FROM historial h " +
                     "LEFT JOIN insumos i ON h.id_ins = i.id_ins " +
                     "WHERE h.idHist=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    h = new historial();
                    h.setIdHist(rs.getInt("idHist"));
                    h.setFecha(rs.getTimestamp("fecha"));
                    h.setAccion(rs.getString("accion"));
                    h.setEstado(rs.getString("estado"));
                    h.setCantidad(rs.getInt("cantidad"));
                    h.setStockActual(rs.getInt("stock_actual"));
                    h.setNovedad(rs.getString("novedad"));
                    h.setId_ins(rs.getInt("id_ins"));
                    h.setId_detalle(rs.getInt("id_detalle"));
                    h.setNombre_insumo(rs.getString("nombre_insumo"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
        }
        return h;
    }

   // Agregar historial con logs
public boolean agregar(historial h) {

    try {
        // LOG: datos que se van a insertar
        System.out.println("=== Intentando agregar historial ===");
        System.out.println("Fecha: " + h.getFecha());
        System.out.println("Acción: " + h.getAccion());
        System.out.println("Estado: " + h.getEstado());
        System.out.println("Cantidad: " + h.getCantidad());
        System.out.println("Stock_actual enviado: " + h.getStockActual());
        System.out.println("Novedad: " + h.getNovedad());
        System.out.println("id_ins: " + h.getId_ins());
        System.out.println("id_detalle: " + h.getId_detalle());

        String sql = "INSERT INTO historial "
                + "(fecha, accion, estado, cantidad, stock_actual, novedad, id_ins, id_detalle) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(h.getFecha().getTime()));
            ps.setString(2, h.getAccion());
            ps.setString(3, h.getEstado());
            ps.setInt(4, h.getCantidad());
            ps.setInt(5, h.getStockActual()); // ✔ YA VIENE LISTO DESDE EL BEAN
            ps.setString(6, h.getNovedad());
            ps.setInt(7, h.getId_ins());

            // id_detalle puede venir null
            if (h.getId_detalle() != null) {
                ps.setInt(8, h.getId_detalle());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas: " + filas);

            return filas > 0;
        }

    } catch (SQLException e) {
        System.err.println("Error en historialDao.agregar(): " + e.getMessage());
        e.printStackTrace();

    } catch (Exception ex) {
        System.err.println("Error inesperado: " + ex.getMessage());
        ex.printStackTrace();
    }

    return false;
}



    // Actualizar historial
    public boolean actualizar(historial h) {
        String sql = "UPDATE historial SET fecha=?, accion=?, estado=?, cantidad=?, stock_actual=?, novedad=?, id_ins=?, id_detalle=? WHERE idHist=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(h.getFecha().getTime()));
            ps.setString(2, h.getAccion());
            ps.setString(3, h.getEstado());
            ps.setInt(4, h.getCantidad());
            ps.setInt(5, h.getStockActual());
            ps.setString(6, h.getNovedad());
            ps.setInt(7, h.getId_ins());
            if (h.getId_detalle() != null) {
                ps.setInt(8, h.getId_detalle());
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }
            ps.setInt(9, h.getIdHist());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en actualizar(): " + e.getMessage());
        }
        return false;
    }

    // Eliminar historial
    public boolean eliminar(int idHist) {
        String sql = "DELETE FROM historial WHERE idHist=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHist);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en eliminar(): " + e.getMessage());
        }
        return false;
    }

    // Calcular stock actual por insumo
    public int calcularStockActual(int id_ins) {
        int stock = 0;
        String sql = "SELECT SUM(CASE WHEN accion='Entrada' THEN cantidad ELSE -cantidad END) AS stock " +
                     "FROM historial WHERE id_ins=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_ins);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stock = rs.getInt("stock");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en calcularStockActual(): " + e.getMessage());
        }
        return stock;
    }
    
    public int obtenerStockActualInsumo(int id_ins) {
    int stock = 0;
    String sql = "SELECT stock_actual FROM insumos WHERE id_ins = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id_ins);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                stock = rs.getInt("stock_actual");
            }
        }

    } catch (SQLException e) {
        System.err.println("Error en obtenerStockActualInsumo(): " + e.getMessage());
    }

    return stock;
}

}
