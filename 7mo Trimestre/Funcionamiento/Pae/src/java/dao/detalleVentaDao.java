package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.DetalleVenta;

/**
 * DAO para operaciones sobre la tabla detalle_venta
 * @author esteb
 */
public class detalleVentaDao {

    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public boolean agregar(DetalleVenta det) {
        String sql = "INSERT INTO detalle_venta (id_ven, id_rec, cantidad) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, det.getIdVen());
            ps.setInt(2, det.getIdReceta());
            ps.setInt(3, det.getCantidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
