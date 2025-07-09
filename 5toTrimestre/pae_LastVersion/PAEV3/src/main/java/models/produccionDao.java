package models;

import config.conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class produccionDao {
    conexion cn = new conexion();

    // ✅ Validar stock antes de registrar
    private boolean validarStock(Connection conn, List<detalleProduccion> detalles) throws SQLException {
        String sqlStock = "SELECT cantidad FROM insumos WHERE id_ins = ?";
        try (PreparedStatement psStock = conn.prepareStatement(sqlStock)) {
            for (detalleProduccion d : detalles) {
                psStock.setInt(1, d.getId_ins());
                try (ResultSet rs = psStock.executeQuery()) {
                    if (rs.next()) {
                        double stockActual = rs.getDouble("cantidad");
                        if (stockActual < d.getCantidadNecesaria()) {
                            return false;
                        }
                    } else {
                        throw new SQLException("Insumo no encontrado con id_ins = " + d.getId_ins());
                    }
                }
            }
        }
        return true;
    }

    // ✅ Registrar nueva producción
    public boolean registrarProduccion(produccion prod) throws ClassNotFoundException, SQLException {
        boolean exito = false;
        String sqlProduccion = "INSERT INTO produccion (fecha_produccion, tipo, cantidad, id_prot, id_res, estado) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_produccion (id_proc, id_ins, cantidad) VALUES (?, ?, ?)";
        String sqlActualizarStock = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";

        Connection conn = null;
        PreparedStatement psProduccion = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        ResultSet rs = null;

        try {
            conn = cn.conexion();
            conn.setAutoCommit(false);

            if (!validarStock(conn, prod.getDetalles())) {
                throw new SQLException("No hay stock suficiente para uno o más insumos.");
            }

            psProduccion = conn.prepareStatement(sqlProduccion, Statement.RETURN_GENERATED_KEYS);
            psProduccion.setDate(1, prod.getFecha_produccion());
            psProduccion.setString(2, prod.getTipo());
            psProduccion.setInt(3, prod.getCantidad());
            psProduccion.setInt(4, prod.getId_prot());
            psProduccion.setInt(5, prod.getId_res());
            psProduccion.setString(6, prod.getEstado());

            int rows = psProduccion.executeUpdate();
            if (rows == 0) throw new SQLException("Error al insertar producción.");

            rs = psProduccion.getGeneratedKeys();
            int idProduccion = 0;
            if (rs.next()) {
                idProduccion = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID generado.");
            }

            psDetalle = conn.prepareStatement(sqlDetalle);
            psStock = conn.prepareStatement(sqlActualizarStock);

            for (detalleProduccion d : prod.getDetalles()) {
                psDetalle.setInt(1, idProduccion);
                psDetalle.setInt(2, d.getId_ins());
                psDetalle.setDouble(3, d.getCantidadNecesaria());
                psDetalle.executeUpdate();

                psStock.setDouble(1, d.getCantidadNecesaria());
                psStock.setInt(2, d.getId_ins());
                psStock.executeUpdate();
            }

            conn.commit();
            exito = true;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (psProduccion != null) try { psProduccion.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (psDetalle != null) try { psDetalle.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (psStock != null) try { psStock.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return exito;
    }

    // ✅ Listar todas las producciones
    public List<produccion> listarTodasProducciones() throws SQLException, ClassNotFoundException {
        List<produccion> lista = new ArrayList<>();
        String sql = "SELECT id_proc, fecha_produccion, tipo, cantidad, estado, id_res FROM produccion ORDER BY fecha_produccion DESC";

        try (Connection conn = cn.conexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produccion p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setTipo(rs.getString("tipo"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstado(rs.getString("estado"));
                p.setId_res(rs.getInt("id_res"));
                lista.add(p);
            }
        }

        return lista;
    }
    
   public produccion obtenerPorId(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produccion WHERE id_proc = ?";
        produccion p = null;
        
        
        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setTipo(rs.getString("tipo"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstado(rs.getString("estado"));
                p.setId_res(rs.getInt("id_res"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener produccion: " + e.getMessage());
        } finally {
            //cerrar();
        }

        return p;
    }
    

    // ✅ Eliminar producción (y detalles si no usas ON DELETE CASCADE)
    public boolean eliminarProduccion(int id) throws SQLException, ClassNotFoundException {
        String sqlDetalle = "DELETE FROM detalle_produccion WHERE id_proc = ?";
        String sqlProduccion = "DELETE FROM produccion WHERE id_proc = ?";

        try (Connection conn = cn.conexion();
             PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
             PreparedStatement psProduccion = conn.prepareStatement(sqlProduccion)) {

            conn.setAutoCommit(false);

            psDetalle.setInt(1, id);
            psDetalle.executeUpdate();

            psProduccion.setInt(1, id);
            int rowsAffected = psProduccion.executeUpdate();

            conn.commit();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ✅ Actualizar producción principal (no incluye detalles)
    public boolean actualizarProduccion(produccion prod) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE produccion SET fecha_produccion = ?, tipo = ?, cantidad = ?, estado = ? WHERE id_proc = ?";

        try (Connection conn = cn.conexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, prod.getFecha_produccion());
            ps.setString(2, prod.getTipo());
            ps.setInt(3, prod.getCantidad());
            ps.setString(4, prod.getEstado());
            ps.setInt(5, prod.getId_proc());

            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Actualizar detalles de una producción (reemplazar todos)
    public boolean actualizarDetallesProduccion(int idProc, List<detalleProduccion> nuevosDetalles) throws SQLException, ClassNotFoundException {
        String sqlEliminar = "DELETE FROM detalle_produccion WHERE id_proc = ?";
        String sqlInsertar = "INSERT INTO detalle_produccion (id_proc, id_ins, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = cn.conexion();
             PreparedStatement psEliminar = conn.prepareStatement(sqlEliminar);
             PreparedStatement psInsertar = conn.prepareStatement(sqlInsertar)) {

            conn.setAutoCommit(false);

            psEliminar.setInt(1, idProc);
            psEliminar.executeUpdate();

            for (detalleProduccion d : nuevosDetalles) {
                psInsertar.setInt(1, idProc);
                psInsertar.setInt(2, d.getId_ins());
                psInsertar.setDouble(3, d.getCantidadNecesaria());
                psInsertar.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
