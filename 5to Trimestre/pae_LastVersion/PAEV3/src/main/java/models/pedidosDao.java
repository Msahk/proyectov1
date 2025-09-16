
package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para acceder a los pedidos desde la base de datos.
 * Se conecta a la tabla "pedidos" y devuelve una lista de objetos.
 * @author Esteban
 */
public class pedidosDao {
    public boolean actualizar(pedidos p) {
   String sql = "UPDATE pedidos SET fecha_entrega = ?, estado = ?, observaciones_pedido = ? WHERE id_ped = ?";
    try (Connection conn = cn.conexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, p.getFecha_entrega());
        ps.setString(2, p.getEstado());
        ps.setString(3, p.getObservaciones_pedido());
        ps.setInt(4, p.getId_ped());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Error al actualizar pedido: " + e.getMessage());
    }
    return false;
}

    conexion cn = new conexion();

    public List<pedidos> listar() {
        List<pedidos> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = cn.conexion();

            if (conn == null) {
                System.out.println("Error: conexión fallida a la base de datos.");
                return lista;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                pedidos obj = new pedidos();
                obj.setId_ped(rs.getInt("id_ped"));
                obj.setId_ven(rs.getInt("id_ven"));
                obj.setFecha_entrega(rs.getString("fecha_entrega"));
                obj.setObservaciones_pedido(rs.getString("observaciones_pedido"));
                lista.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
        } finally {
            // Cerramos los recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }

        return lista;
    }
}
