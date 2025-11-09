package dao;

import modelo.ventas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;
import control.ConDB;


public class ventasDao {

    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
public boolean actualizarObservaciones(int idVen, String observaciones) {
    String sql = "UPDATE ventas SET observaciones = ? WHERE id_ven = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        if (observaciones != null) {
            ps.setString(1, observaciones);
        } else {
            ps.setNull(1, java.sql.Types.LONGVARCHAR);
        }
        ps.setInt(2, idVen);

        int rows = ps.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

   public List<ventas> listarProcesando() {
    List<ventas> lista = new ArrayList<>();

    String sql = "SELECT v.*, " +
                 "c.nombre AS nombreCliente, " +
                 "CONCAT(u1.nombres, ' ', u1.apellidos) AS nombreUsuario, " +
                 "CONCAT(u2.nombres, ' ', u2.apellidos) AS nombreAsignado " +
                 "FROM ventas v " +
                 "JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
                 "LEFT JOIN usuarios u1 ON v.id_usu = u1.id_usu " +       // Usuario que registró la venta
                 "LEFT JOIN usuarios u2 ON v.id_asignado = u2.id_usu " + // Usuario asignado a la venta
                 "WHERE v.estado = 'Procesando' " +
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
            v.setIdAsignado(rs.getInt("id_asignado"));
            v.setIdCliente(rs.getInt("id_Cliente"));
            v.setTotal(rs.getDouble("total"));
            v.setEstado(rs.getString("estado"));
            v.setObservaciones(rs.getString("observaciones"));
            v.setNombreCliente(rs.getString("nombreCliente"));
            v.setNombreUsuario(rs.getString("nombreUsuario"));   // quien registró
            v.setNombreAsignado(rs.getString("nombreAsignado")); // quien fue asignado
            lista.add(v);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}



    public ventas obtenerUltimaVenta() {
        String sql = "SELECT v.*, c.nombre AS nombreCliente FROM ventas v LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente ORDER BY v.id_ven DESC LIMIT 1";
        ventas v = null;
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVen);
            boolean actualizado = ps.executeUpdate() > 0;

            if (actualizado && "Completada".equalsIgnoreCase(nuevoEstado)) {
                actualizarEstadoPedidoAsociado(idVen, "Completado");
            }

            return actualizado;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ventas> listar() {
    List<ventas> lista = new ArrayList<>();

    String sql = "SELECT v.*, " +
            "c.nombre AS nombreCliente, " +
            "CONCAT(u.nombres, ' ', u.apellidos) AS nombreUsuario, " +
            "CONCAT(u2.nombres, ' ', u2.apellidos) AS nombreAsignado " +
            "FROM ventas v " +
            "LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
            "LEFT JOIN usuarios u ON v.id_usu = u.id_usu " +        // 🔹 Usuario que registró
            "LEFT JOIN usuarios u2 ON v.id_asignado = u2.id_usu " + // 🔹 Usuario asignado
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
            v.setIdAsignado(rs.getInt("id_asignado"));
            v.setIdCliente(rs.getInt("id_Cliente"));
            v.setTotal(rs.getDouble("total"));
            v.setEstado(rs.getString("estado"));
            v.setObservaciones(rs.getString("observaciones"));
            v.setNombreCliente(rs.getString("nombreCliente"));
            v.setNombreUsuario(rs.getString("nombreUsuario"));
            v.setNombreAsignado(rs.getString("nombreAsignado"));
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
        String asignado,   // 🔹 Nuevo filtro por usuario asignado
        Double totalMin,
        Double totalMax,
        String estado,
        java.sql.Date fechaDesde,
        java.sql.Date fechaHasta
) {
    List<ventas> lista = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT v.*, c.nombre AS nombreCliente, " +
        "CONCAT(u.nombres, ' ', u.apellidos) AS nombreUsuario, " +
        "CONCAT(u2.nombres, ' ', u2.apellidos) AS nombreAsignado " +
        "FROM ventas v " +
        "LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
        "LEFT JOIN usuarios u ON v.id_usu = u.id_usu " +
        "LEFT JOIN usuarios u2 ON v.id_asignado = u2.id_usu " +
        "WHERE 1=1 "
    );

    List<Object> params = new ArrayList<>();

    if (idVenta != null) { sql.append("AND v.id_ven = ? "); params.add(idVenta); }
    if (tipo != null && !tipo.isEmpty()) { sql.append("AND v.Tipo = ? "); params.add(tipo); }
    if (cliente != null && !cliente.isEmpty()) { sql.append("AND c.nombre LIKE ? "); params.add("%" + cliente + "%"); }
    if (usuario != null && !usuario.isEmpty()) { sql.append("AND CONCAT(u.nombres, ' ', u.apellidos) LIKE ? "); params.add("%" + usuario + "%"); }
    if (asignado != null && !asignado.isEmpty()) { sql.append("AND CONCAT(u2.nombres, ' ', u2.apellidos) LIKE ? "); params.add("%" + asignado + "%"); }
    if (totalMin != null) { sql.append("AND v.total >= ? "); params.add(totalMin); }
    if (totalMax != null) { sql.append("AND v.total <= ? "); params.add(totalMax); }
    if (estado != null && !estado.isEmpty()) { sql.append("AND v.estado = ? "); params.add(estado); }
    if (fechaDesde != null) { sql.append("AND v.fecha >= ? "); params.add(fechaDesde); }
    if (fechaHasta != null) { sql.append("AND v.fecha <= ? "); params.add(fechaHasta); }

    sql.append("ORDER BY v.id_ven DESC");

    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

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
                v.setIdAsignado(rs.getInt("id_asignado"));
                v.setIdCliente(rs.getInt("id_Cliente"));
                v.setTotal(rs.getDouble("total"));
                v.setEstado(rs.getString("estado"));
                v.setObservaciones(rs.getString("observaciones"));
                v.setNombreCliente(rs.getString("nombreCliente"));
                v.setNombreUsuario(rs.getString("nombreUsuario"));
                v.setNombreAsignado(rs.getString("nombreAsignado"));
                lista.add(v);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}



   public int agregar(ventas v) {
    String sql = "INSERT INTO ventas (Tipo, fecha, id_usu, id_asignado, id_Cliente, total, estado, observaciones) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, v.getTipo());
        ps.setTimestamp(2, new Timestamp(v.getFecha().getTime()));

        // Usuario que registra la venta (sesión)
        ps.setInt(3, v.getIdUsuario());

        // Usuario asignado (puede ser nulo)
        if (v.getIdAsignado() > 0) {
            ps.setInt(4, v.getIdAsignado());
        } else {
            ps.setNull(4, Types.INTEGER);
        }

        ps.setInt(5, v.getIdCliente());
        ps.setDouble(6, v.getTotal());
        ps.setString(7, v.getEstado());
        ps.setString(8, v.getObservaciones());

        int affectedRows = ps.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Devolver id de la venta generada
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("❌ Error al agregar venta: " + e.getMessage());
    }

    return -1; // Si hubo error
}



  public boolean actualizar(ventas v) {
    String sql = "UPDATE ventas SET Tipo=?, fecha=?, id_usu=?, id_asignado=?, id_Cliente=?, total=?, estado=?, observaciones=? " +
                 "WHERE id_ven=?";

    try (Connection con = getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, v.getTipo());
        ps.setTimestamp(2, new Timestamp(v.getFecha().getTime()));
        ps.setInt(3, v.getIdUsuario());

        // Usuario asignado (puede ser nulo)
        if (v.getIdAsignado() > 0) {
            ps.setInt(4, v.getIdAsignado());
        } else {
            ps.setNull(4, Types.INTEGER);
        }

        ps.setInt(5, v.getIdCliente());
        ps.setDouble(6, v.getTotal());
        ps.setString(7, v.getEstado());
        ps.setString(8, v.getObservaciones());
        ps.setInt(9, v.getIdVen());

        int rows = ps.executeUpdate();

        return rows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    public boolean eliminar(int id) {
        String sql = "DELETE FROM ventas WHERE id_ven=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 public ventas obtenerPorId(int id) {
    String sql =
        "SELECT v.*, " +
        "c.nombre AS nombreCliente, " +
        "u.nombres AS nombreUsuario, " +
        "u.apellidos AS apellidoUsuario, " +
        "a.nombres AS nombreAsignado, " +
        "a.apellidos AS apellidoAsignado " +
        "FROM ventas v " +
        "LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
        "LEFT JOIN usuarios u ON v.id_usu = u.id_usu " +
        "LEFT JOIN usuarios a ON v.id_asignado = a.id_usu " +
        "WHERE v.id_ven = ?";

    ventas v = null;

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        System.out.println("🔍 Ejecutando obtenerPorId() con idVenta = " + id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                v = new ventas();
                v.setIdVen(rs.getInt("id_ven"));
                v.setTipo(rs.getString("Tipo")); // tu tabla usa mayúscula
                v.setFecha(rs.getTimestamp("fecha"));
                v.setIdUsuario(rs.getInt("id_usu"));
                v.setIdAsignado(rs.getInt("id_asignado"));
                v.setIdCliente(rs.getInt("id_Cliente")); // campo real con C mayúscula
                v.setTotal(rs.getDouble("total"));
                v.setEstado(rs.getString("estado"));
                v.setObservaciones(rs.getString("observaciones"));

                // 🔹 Datos del cliente y usuarios
                v.setNombreCliente(rs.getString("nombreCliente"));
                v.setNombreUsuario(rs.getString("nombreUsuario"));
                v.setApellidoUsuario(rs.getString("apellidoUsuario")); // ✅ agregado
                v.setNombreAsignado(rs.getString("nombreAsignado"));   // ✅ agregado
                v.setApellidoAsignado(rs.getString("apellidoAsignado")); // ✅ agregado
            } else {
                System.out.println("⚠️ obtenerPorId(): no se encontró la venta con id = " + id);
            }
        }

    } catch (SQLException e) {
        System.out.println("❌ Error en obtenerPorId(): " + e.getMessage());
        e.printStackTrace();
    }

    return v;
}









    public List<DetalleVenta> obtenerDetallesPorVenta(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT vr.id_venta, r.nombre AS nombreReceta, vr.cantidad "
                + "FROM venta_recetas vr "
                + "INNER JOIN recetas r ON vr.id_rec = r.id_rec "
                + "WHERE vr.id_venta = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta det = new DetalleVenta();
                    det.setNombreEmpanada(rs.getString("nombreReceta"));
                    det.setCantidad(rs.getInt("cantidad"));
                    lista.add(det);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void actualizarEstadoPedidoAsociado(int idVenta, String nuevoEstado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id_ven = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVenta);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Pedido asociado a la venta " + idVenta + " actualizado a estado: " + nuevoEstado);
            } else {
                System.out.println("⚠️ No se encontró pedido asociado a la venta " + idVenta);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar estado del pedido asociado: " + e.getMessage());
        }
    }
    
    
    public int obtenerConteoProduccion(int id) {
    String sql =
        "select count(*) as conteo from produccion where id_asignado = ?";

    int conteo = 0;

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                conteo = rs.getInt("conteo");
                
            } else {
                System.out.println(" obtenerPorId(): no se encontró la venta con id = " + id);
            }
        }

    } catch (SQLException e) {
        System.out.println(" Error en obtenerPorId(): " + e.getMessage());
        e.printStackTrace();
    }

    return conteo;
}

}
