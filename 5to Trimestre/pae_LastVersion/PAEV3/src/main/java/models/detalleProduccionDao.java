package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class detalleProduccionDao {

    conexion cn = new conexion();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Agregar un detalle de producción
    public boolean agregarDetalle(detalleProduccion detalle) throws ClassNotFoundException {
        String sql = "INSERT INTO detalle_produccion (id_proc, id_ins, cantidad) VALUES (?, ?, ?)";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, detalle.getId_proc());
            ps.setInt(2, detalle.getId_ins());
            ps.setDouble(3, detalle.getCantidadNecesaria());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los detalles por ID de producción
    public List<detalleProduccion> obtenerPorProduccion(int idProduccion) throws ClassNotFoundException {
        List<detalleProduccion> lista = new ArrayList<>();
        String sql = "SELECT dp.*, i.nombre AS nombreInsumo FROM detalle_produccion dp " +
                     "JOIN insumos i ON dp.id_ins = i.id_ins WHERE dp.id_proc = ?";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduccion);
            rs = ps.executeQuery();

            while (rs.next()) {
                detalleProduccion d = new detalleProduccion();
                d.setId_detpro(rs.getInt("id_detpro"));
                d.setId_proc(rs.getInt("id_proc"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidadNecesaria(rs.getDouble("cantidad"));
                d.setNombreInsumo(rs.getString("nombreInsumo"));
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Eliminar un detalle por ID
    public boolean eliminarDetalle(int idDetalle) throws ClassNotFoundException {
        String sql = "DELETE FROM detalle_produccion WHERE id_detpro = ?";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idDetalle);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar todos los detalles de una producción
    public boolean eliminarPorProduccion(int idProduccion) throws ClassNotFoundException {
        String sql = "DELETE FROM detalle_produccion WHERE id_proc = ?";

        try {
            conn = cn.conexion();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduccion);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
