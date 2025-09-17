// package models;

// import config.conexion;
// import java.sql.CallableStatement;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Timestamp;

// public class VentaClientePedidoDao {

//     conexion cn = new conexion();
//     Connection conn = null;
//     PreparedStatement ps = null;
//     ResultSet rs = null;

//     /**
//      * Llama al procedimiento almacenado sp_insertarVentaClientePedido
//      * para insertar un cliente, una venta y su pedido en una sola transacción.
//      *
//      * @param vcp Objeto con todos los datos para cliente, venta y pedido
//      * @return true si la operación tuvo éxito; false en caso contrario
//      */
//     public boolean insertar(VentaClientePedido vcp) {
        
//         String sql = " { CALL sp_insertarVentaClientePedido(?,?,?,?,?,?,?,?,?,?,?) }";

//         try {
//             conn = cn.conexion();
//             ps = conn.prepareCall(sql);

//             ps.setString(1, vcp.getNombreCliente());
//             ps.setString(2, vcp.getTelefonoCliente());
//             ps.setString(3, vcp.getCorreoCliente());
//             ps.setString(4, vcp.getTipoVenta());
//             ps.setTimestamp(5, Timestamp.valueOf(vcp.getFechaVenta()));
//             ps.setInt(6, vcp.getIdUsuario());
//             ps.setBigDecimal(7, vcp.getTotalVenta());
//             ps.setString(8, vcp.getEstadoVenta());
//             ps.setString(9, vcp.getObsVenta());
//             ps.setTimestamp(10, Timestamp.valueOf(vcp.getFechaEntregaPedido()));
//             ps.setString(11, vcp.getObsPedido());

//             ps.execute();
//             return true;

//         } catch (SQLException e) {
//             System.err.println("Error al ejecutar sp_insertarVentaClientePedido: " + e.getMessage());
//             e.printStackTrace();
//             return false;

//         } finally {
//             // Cierra recursos
//             try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
//             try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
//         }
//     }

//      public boolean actualizar(int idVenta, int idCliente, VentaClientePedido vcp) {
//         boolean resultado = false;
//         try {
//             conn = cn.conexion();
//             if (conn == null) {
//                 System.out.println("Error: Conexión nula en actualizar()");
//                 return false;
//             }

//             // Llama al SP: ajusta el nombre y número de parámetros si es distinto
//             String sql = "{ CALL sp_actualizarVentaClientePedido(?,?,?,?,?,?,?,?,?,?,?,?) }";
//             ps = conn.prepareCall(sql);

//             // 1) IDs
//             ps.setInt   (1, idVenta);
//             ps.setInt   (2, idCliente);

//             // 2) Datos de cliente
//             ps.setString(3, vcp.getNombreCliente());
//             ps.setString(4, vcp.getTelefonoCliente());
//             ps.setString(5, vcp.getCorreoCliente());

//             // 3) Datos de venta
//             ps.setString   (6, vcp.getTipoVenta());
//             ps.setTimestamp(7, Timestamp.valueOf(vcp.getFechaVenta()));
//             ps.setInt      (8, vcp.getIdUsuario());
//             ps.setBigDecimal(9, vcp.getTotalVenta());
//             ps.setString   (10, vcp.getEstadoVenta());
//             ps.setString   (11, vcp.getObsVenta());

//             // 4) Datos de pedido
//             ps.setTimestamp(12, Timestamp.valueOf(vcp.getFechaEntregaPedido()));
//             // Si tu SP recibe un parámetro 13 para observaciones del pedido:
//             // ps.setString(13, vcp.getObsPedido());

//             // Ejecuta
//             resultado = ps.executeUpdate() > 0;
//         } catch (SQLException e) {
//             System.out.println("Error en actualizar(): " + e.getMessage());
//             e.printStackTrace();
//         } finally {
//             // Cierra recursos
//             try { if (ps != null) ps.close(); } catch (Exception e) {}
//             try { if (conn != null) conn.close(); } catch (Exception e) {}
//         }
//         return resultado;
//     }
    
    
//    public VentaClientePedido obtenerPorId(int idVenta) throws SQLException {
//     System.out.println(">>> Iniciando método obtenerPorId con ID: " + idVenta);

//     VentaClientePedido venta = null;
//     String sql =
//         "SELECT v.*, " +
//         "       c.nombre              AS nombreCliente, " +
//         "       c.telefono            AS telefonoCliente, " +
//         "       c.correo              AS correoCliente, " +
//         "       p.fecha_entrega       AS fechaEntregaPedido, " +
//         "       p.observaciones_pedido AS observacionesPedido " +
//         "  FROM ventas v " +
//         "  LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
//         "  LEFT JOIN pedidos  p ON v.id_ven     = p.id_ven " +
//         " WHERE v.id_ven = ?";

//     conexion cn = new conexion();
//     Connection con = cn.conexion();
//     if (con == null) {
//         System.out.println(">>> ERROR: La conexión es null");
//         throw new SQLException("No se pudo conectar a la base de datos");
//     }

//     PreparedStatement ps = null;
//     ResultSet rs = null;
//     try {
//         ps = con.prepareStatement(sql);
//         ps.setInt(1, idVenta);
//         rs = ps.executeQuery();
//         System.out.println(">>> Ejecutada la consulta SQL");

//         if (rs.next()) {
//             System.out.println(">>> Registro encontrado en ResultSet");
//             venta = new VentaClientePedido();
//             venta.setNombreCliente(rs.getString("nombreCliente"));
//             venta.setTelefonoCliente(rs.getString("telefonoCliente"));
//             venta.setCorreoCliente(rs.getString("correoCliente"));
//             venta.setTipoVenta(rs.getString("tipo"));
//             venta.setFechaVenta(rs.getTimestamp("fecha").toLocalDateTime());
//             venta.setIdUsuario(rs.getInt("id_usu"));
//             venta.setTotalVenta(rs.getBigDecimal("total"));
//             venta.setEstadoVenta(rs.getString("estado"));
//             venta.setObsVenta(rs.getString("observaciones"));
//             Timestamp tsEnt = rs.getTimestamp("fechaEntregaPedido");
//             if (tsEnt != null) {
//                 venta.setFechaEntregaPedido(tsEnt.toLocalDateTime());
//             }
//             venta.setObsPedido(rs.getString("observacionesPedido"));
//         } else {
//             System.out.println(">>> No se encontró ningún registro con ese ID");
//         }

//     } finally {
//         if (rs  != null) try { rs.close();  } catch (Exception e) {}
//         if (ps  != null) try { ps.close();  } catch (Exception e) {}
//         if (con != null) try { con.close(); } catch (Exception e) {}
//     }
//     return venta;
// }




// }


