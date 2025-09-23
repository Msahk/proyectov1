package dao;

import modelo.ventas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;

public class ventasDao {

    
    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

   
    public List<ventas> listarProcesando() {
        List<ventas> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nombre AS nombreCliente FROM ventas v JOIN clientes c ON v.id_Cliente = c.id_Cliente WHERE v.estado = 'Procesando' ORDER BY v.id_ven DESC";
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
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public List<DetalleVenta> obtenerDetallesPorVenta(int idVenta) {
    List<DetalleVenta> detalles = new ArrayList<>();
    String sql = "SELECT r.nombre AS empanada, dv.cantidad " +
                 "FROM detalle_venta dv " +
                 "JOIN produccion p ON dv.id_proc = p.id_proc " +
                 "JOIN produccion_recetas pr ON pr.id_produccion = p.id_proc " +
                 "JOIN recetas r ON pr.id_rec = r.id_rec " +
                 "WHERE dv.id_ven = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idVenta);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DetalleVenta dto = new DetalleVenta();
            dto.setNombreEmpanada(rs.getString("empanada"));
            dto.setCantidad(rs.getInt("cantidad"));
            detalles.add(dto);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return detalles;
}
public boolean actualizarTipoVenta(modelo.ventas v) {
        String sql = "UPDATE ventas SET Tipo=?, fecha=?, id_usu=?, id_Cliente=?, total=?, estado=?, observaciones=? WHERE id_ven=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, new java.sql.Timestamp(v.getFecha().getTime()));
            if (v.getIdUsuario() > 0) ps.setInt(3, v.getIdUsuario());
            else ps.setNull(3, java.sql.Types.INTEGER);
            ps.setInt(4, v.getIdCliente());
            ps.setDouble(5, v.getTotal());
            ps.setString(6, v.getEstado());
            ps.setString(7, v.getObservaciones());
            ps.setInt(8, v.getIdVen());
            int rows = ps.executeUpdate();

           
            if (rows > 0 && "pedido".equalsIgnoreCase(v.getTipo())) {
                dao.pedidosDao pedidosDao = new dao.pedidosDao();
                if (!pedidosDao.existePedidoParaVenta(v.getIdVen())) {
                    modelo.pedidos nuevoPedido = new modelo.pedidos();
                    nuevoPedido.setIdVen(v.getIdVen());
                    nuevoPedido.setIdCliente(v.getIdCliente());
                    nuevoPedido.setNombreCliente(v.getNombreCliente());
                    nuevoPedido.setEstado("Pendiente");
                    nuevoPedido.setFechaEntrega(new java.util.Date());
                    nuevoPedido.setObservacionesPedido(v.getObservaciones());
                    pedidosDao.agregar(nuevoPedido);
                }
            }
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 public ventas obtenerUltimaVenta() {
        String sql = "SELECT v.*, c.nombre AS nombreCliente FROM ventas v LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente ORDER BY v.id_ven DESC LIMIT 1";
        ventas v = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
                v.setNombreCliente(rs.getString("nombreCliente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
    
    public boolean actualizarEstado(int idVen, String nuevoEstado) {
        String sql = "UPDATE ventas SET estado=? WHERE id_ven=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVen);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
  public List<ventas> filtrarAvanzado(
        Integer idVenta,
        String tipo,
        String cliente,
        String usuario,
        Double totalMin,
        Double totalMax,
        String estado,
        java.sql.Date fechaDesde,
        java.sql.Date fechaHasta
) {
    List<ventas> lista = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT v.*, c.nombre AS nombreCliente, u.nombres AS nombreUsuario FROM ventas v LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente LEFT JOIN usuarios u ON v.id_usu = u.id_usu WHERE 1=1 ");
    List<Object> params = new ArrayList<>();

    if (idVenta != null) {
        sql.append("AND v.id_ven = ? ");
        params.add(idVenta);
    }
    if (tipo != null && !tipo.isEmpty()) {
        sql.append("AND v.Tipo = ? ");
        params.add(tipo);
    }
    if (cliente != null && !cliente.isEmpty()) {
        sql.append("AND c.nombre LIKE ? ");
        params.add("%" + cliente + "%");
    }
    if (usuario != null && !usuario.isEmpty()) {
        sql.append("AND u.nombres LIKE ? ");
        params.add("%" + usuario + "%");
    }
    if (totalMin != null) {
        sql.append("AND v.total >= ? ");
        params.add(totalMin);
    }
    if (totalMax != null) {
        sql.append("AND v.total <= ? ");
        params.add(totalMax);
    }
    if (estado != null && !estado.isEmpty()) {
        sql.append("AND v.estado = ? ");
        params.add(estado);
    }
    if (fechaDesde != null) {
        sql.append("AND v.fecha >= ? ");
        params.add(fechaDesde);
    }
    if (fechaHasta != null) {
        sql.append("AND v.fecha <= ? ");
        params.add(fechaHasta);
    }
    sql.append("ORDER BY v.id_ven DESC");

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
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
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

     public int agregar(ventas v) {
    String sql = "INSERT INTO ventas (Tipo, fecha, id_usu, id_Cliente, total, estado, observaciones) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, v.getTipo());
        ps.setTimestamp(2, new Timestamp(v.getFecha().getTime()));
        if (v.getIdUsuario() > 0) ps.setInt(3, v.getIdUsuario());
        else ps.setNull(3, Types.INTEGER);
        ps.setInt(4, v.getIdCliente());
        ps.setDouble(5, v.getTotal());
        ps.setString(6, v.getEstado());
        ps.setString(7, v.getObservaciones());
        int affectedRows = ps.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; 
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

        int rows = ps.executeUpdate();

       
        if (rows > 0) {
            pedidosDao pedidosDao = new pedidosDao();
            if (pedidosDao.existePedidoParaVenta(v.getIdVen())) {
                pedidosDao.actualizarEstadoYObservacionesPorVenta(v.getIdVen(), v.getEstado(), v.getObservaciones());
            }
        }

        return rows > 0;

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
        String sql = "SELECT v.*, c.nombre AS nombreCliente FROM ventas v LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente WHERE v.id_ven=?";
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
                    v.setNombreCliente(rs.getString("nombreCliente"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
}