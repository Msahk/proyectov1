package dao;

import models.pedidos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class pedidosDao {

    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    public String ultimoError = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
 public List<pedidos> listar() {
        List<pedidos> lista = new ArrayList<>();
        String sql = "SELECT p.*, v.id_Cliente, c.nombre AS nombreCliente FROM pedidos p JOIN ventas v ON p.id_ven = v.id_ven JOIN clientes c ON v.id_Cliente = c.id_Cliente ORDER BY p.id_ped DESC";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pedidos p = new pedidos();
                p.setIdPed(rs.getInt("id_ped"));
                p.setIdVen(rs.getInt("id_ven"));
                p.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                p.setEstado(rs.getString("estado"));
                p.setObservacionesPedido(rs.getString("observaciones_pedido"));
                 p.setIdCliente(rs.getInt("id_Cliente"));
    p.setNombreCliente(rs.getString("nombreCliente"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean existePedidoParaVenta(int idVen) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE id_ven=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVen);
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
    public boolean agregar(pedidos p) {
        String sql = "INSERT INTO pedidos (id_ven, fecha_entrega, estado, observaciones_pedido) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdVen());
            ps.setTimestamp(2, new Timestamp(p.getFechaEntrega().getTime()));
            ps.setString(3, p.getEstado());
            ps.setString(4, p.getObservacionesPedido());

            ultimoError = "";
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            ultimoError = e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(pedidos p) {
        String sql = "UPDATE pedidos SET id_ven=?, fecha_entrega=?, estado=?, observaciones_pedido=? WHERE id_ped=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdVen());
            ps.setTimestamp(2, new Timestamp(p.getFechaEntrega().getTime()));
            ps.setString(3, p.getEstado());
            ps.setString(4, p.getObservacionesPedido());
            ps.setInt(5, p.getIdPed());

            ultimoError = "";
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            ultimoError = e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idPed) {
        String sql = "DELETE FROM pedidos WHERE id_ped=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPed);
            ultimoError = "";
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            ultimoError = e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public pedidos obtenerPorId(int idPed) {
        String sql = "SELECT * FROM pedidos WHERE id_ped=?";
        pedidos p = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPed);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new pedidos();
                    p.setIdPed(rs.getInt("id_ped"));
                    p.setIdVen(rs.getInt("id_ven"));
                    p.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                    p.setEstado(rs.getString("estado"));
                    p.setObservacionesPedido(rs.getString("observaciones_pedido"));
                }
            }

        } catch (SQLException e) {
            ultimoError = e.getMessage();
            e.printStackTrace();
        }

        return p;
    }
}