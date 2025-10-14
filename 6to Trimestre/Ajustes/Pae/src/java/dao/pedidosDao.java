package dao;

import modelo.pedidos;
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
public List<pedidos> filtrarAvanzado(
        Integer idPedido,
        Integer idVenta,
        String estado,
        String cliente,
        java.sql.Date fechaDesde,
        java.sql.Date fechaHasta
) {
    List<pedidos> lista = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT p.*, v.id_Cliente, c.nombre AS nombreCliente FROM pedidos p JOIN ventas v ON p.id_ven = v.id_ven JOIN clientes c ON v.id_Cliente = c.id_Cliente WHERE 1=1 ");
    List<Object> params = new ArrayList<>();

    if (idPedido != null) {
        sql.append("AND p.id_ped = ? ");
        params.add(idPedido);
    }
    if (idVenta != null) {
        sql.append("AND p.id_ven = ? ");
        params.add(idVenta);
    }
    if (estado != null && !estado.isEmpty()) {
        sql.append("AND p.estado = ? ");
        params.add(estado);
    }
    if (cliente != null && !cliente.isEmpty()) {
        sql.append("AND c.nombre LIKE ? ");
        params.add("%" + cliente + "%");
    }
    if (fechaDesde != null) {
        sql.append("AND p.fecha_entrega >= ? ");
        params.add(fechaDesde);
    }
    if (fechaHasta != null) {
        sql.append("AND p.fecha_entrega <= ? ");
        params.add(fechaHasta);
    }
    sql.append("ORDER BY p.id_ped DESC");

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
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
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
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
    public boolean eliminarPorVenta(int idVenta) {
    String sql = "DELETE FROM pedidos WHERE id_ven=?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idVenta);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
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
    public boolean actualizarEstadoYObservacionesPorVenta(int idVenta, String estado, String observaciones) {
    String sql = "UPDATE pedidos SET estado=?, observaciones_pedido=? WHERE id_ven=?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, estado);
        ps.setString(2, observaciones);
        ps.setInt(3, idVenta);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public List<pedidos> filtrar(String estado, String cliente, Date fechaEntrega) {
    List<pedidos> lista = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT p.*, v.id_Cliente, c.nombre AS nombreCliente FROM pedidos p ");
    sql.append("JOIN ventas v ON p.id_ven = v.id_ven ");
    sql.append("JOIN clientes c ON v.id_Cliente = c.id_Cliente WHERE 1=1 ");

    if (estado != null && !estado.isEmpty()) {
        sql.append("AND p.estado = ? ");
    }
    if (cliente != null && !cliente.isEmpty()) {
        sql.append("AND c.nombre LIKE ? ");
    }
    if (fechaEntrega != null) {
        sql.append("AND DATE(p.fecha_entrega) = ? ");
    }

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {
        int index = 1;

        if (estado != null && !estado.isEmpty()) {
            ps.setString(index++, estado);
        }
        if (cliente != null && !cliente.isEmpty()) {
            ps.setString(index++, "%" + cliente + "%");
        }
        if (fechaEntrega != null) {
            ps.setDate(index++, new java.sql.Date(fechaEntrega.getTime()));
        }

        try (ResultSet rs = ps.executeQuery()) {
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
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
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