package dao;

import models.ventas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ventasDao {

    
    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    
public List<ventas> listar() {
    List<ventas> lista = new ArrayList<>();
    String sql = "SELECT v.*, c.nombre AS nombreCliente, u.nombres AS nombreUsuario " +
                 "FROM ventas v " +
                 "LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
                 "LEFT JOIN usuarios u ON v.id_usu = u.id_usu " +
                 "ORDER BY v.id_ven DESC";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            ventas v = new ventas();
            v.setIdVen(rs.getInt("id_ven"));
            v.setTipo(rs.getString("Tipo"));
            v.setFecha(rs.getTimestamp("fecha"));
            v.setIdUsuario(rs.getInt("id_usu"));
            v.setIdCliente(rs.getInt("id_Cliente"));
            v.setTotal(rs.getDouble("total"));
            v.setEstado(rs.getString("estado"));
            v.setObservaciones(rs.getString("observaciones"));
            v.setNombreCliente(rs.getString("nombreCliente"));
            v.setNombreUsuario(rs.getString("nombreUsuario"));
            lista.add(v);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
   
    public boolean agregar(ventas v) {
        String sql = "INSERT INTO ventas (Tipo, fecha, id_usu, id_Cliente, total, estado, observaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, new Timestamp(v.getFecha().getTime()));
            if (v.getIdUsuario() > 0) ps.setInt(3, v.getIdUsuario());
            else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, v.getIdCliente());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getEstado());
            ps.setString(7, v.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean actualizar(ventas v) {
        String sql = "UPDATE ventas SET Tipo=?, fecha=?, id_usu=?, id_Cliente=?, total=?, estado=?, observaciones=? "
                   + "WHERE id_ven=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, new Timestamp(v.getFecha().getTime()));
            if (v.getIdUsuario() > 0) ps.setInt(3, v.getIdUsuario());
            else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, v.getIdCliente());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getEstado());
            ps.setString(7, v.getObservaciones());
            ps.setInt(8, v.getIdVen());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ventas WHERE id_ven=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public ventas obtenerPorId(int id) {
        String sql = "SELECT * FROM ventas WHERE id_ven=?";
        ventas v = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new ventas();
                    v.setIdVen(rs.getInt("id_ven"));
                    v.setTipo(rs.getString("Tipo"));
                    v.setFecha(rs.getTimestamp("fecha"));
                    v.setIdUsuario(rs.getInt("id_usu"));
                    v.setIdCliente(rs.getInt("id_Cliente"));
                    v.setTotal(rs.getDouble("total"));
                    v.setEstado(rs.getString("estado"));
                    v.setObservaciones(rs.getString("observaciones"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }
}