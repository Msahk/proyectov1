// package models;

// import config.conexion;
// import java.sql.*;
// import java.util.*;

// public class insumosDao {
//     Connection con;
//     conexion cn = new conexion();
//     PreparedStatement ps;
//     ResultSet rs;

//     public List<insumos> listar() {
//         List<insumos> lista = new ArrayList<>();
//         String sql = "SELECT * FROM insumos";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             rs = ps.executeQuery();
//             while (rs.next()) {
//                 insumos ins = new insumos();
//                 ins.setId_ins(rs.getInt("id_ins"));
//                 ins.setNombre(rs.getString("nombre"));
//                 ins.setCantidad(rs.getDouble("cantidad"));
//                 ins.setUnidad_medida(rs.getString("unidad_medida"));
//                 ins.setStock_min(rs.getDouble("stock_min"));
//                 lista.add(ins);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return lista;
//     }

//    public boolean agregar(insumos ins) {
//     String sql = "INSERT INTO insumos(nombre, cantidad, unidad_medida, stock_min) VALUES (?, ?, ?, ?)";
//     try {
//         con = cn.conexion();
//         con.setAutoCommit(false); // iniciar transacción
//         ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//         ps.setString(1, ins.getNombre());
//         ps.setDouble(2, ins.getCantidad());
//         ps.setString(3, ins.getUnidad_medida());
//         ps.setDouble(4, ins.getStock_min());

//         int affectedRows = ps.executeUpdate();
//         if (affectedRows == 1) {
//             // Obtener id generado del insumo
//             ResultSet generatedKeys = ps.getGeneratedKeys();
//             if (generatedKeys.next()) {
//                 int idInsumo = generatedKeys.getInt(1);

//                 // Registrar entrada automática en inv_entradas
//                 String sqlEntrada = "INSERT INTO inv_entradas(id_ins, cantidad, fecha_hora, usuario, observacion) VALUES (?, ?, NOW(), ?, ?)";
//                 try (PreparedStatement psEntrada = con.prepareStatement(sqlEntrada)) {
//                     psEntrada.setInt(1, idInsumo);
//                     psEntrada.setDouble(2, ins.getCantidad());
//                     psEntrada.setString(3, "Sistema"); // usuario por defecto
//                     psEntrada.setString(4, "Registro inicial del insumo");
//                     psEntrada.executeUpdate();
//                 }
//             }
//         }
//         con.commit();
//         return true;
//     } catch (Exception e) {
//         try {
//             if (con != null) con.rollback();
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//         e.printStackTrace();
//         return false;
//     } finally {
//         try {
//             if (con != null) con.setAutoCommit(true);
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//     }
// }


//     public boolean actualizar(insumos ins) {
//         String sql = "UPDATE insumos SET nombre=?, cantidad=?, unidad_medida=?, stock_min=? WHERE id_ins=?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setString(1, ins.getNombre());
//             ps.setDouble(2, ins.getCantidad());
//             ps.setString(3, ins.getUnidad_medida());
//             ps.setDouble(4, ins.getStock_min());
//             ps.setInt(5, ins.getId_ins());
//             return ps.executeUpdate() == 1;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     public boolean eliminar(int id) {
//         String sql = "DELETE FROM insumos WHERE id_ins=?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id);
//             return ps.executeUpdate() == 1;
//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     public insumos buscarPorId(int id) {
//         insumos ins = null;
//         String sql = "SELECT * FROM insumos WHERE id_ins=?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id);
//             rs = ps.executeQuery();
//             if (rs.next()) {
//                 ins = new insumos();
//                 ins.setId_ins(rs.getInt("id_ins"));
//                 ins.setNombre(rs.getString("nombre"));
//                 ins.setCantidad(rs.getDouble("cantidad"));
//                 ins.setUnidad_medida(rs.getString("unidad_medida"));
//                 ins.setStock_min(rs.getDouble("stock_min"));
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return ins;
//     }
    
//     public boolean eliminarPorRecetaEInsumo(int idRec, int idIns) {
//     String sql = "DELETE FROM receta_insumos WHERE id_rec = ? AND id_ins = ?";
//     try {
//         con = cn.conexion();
//         ps = con.prepareStatement(sql);
//         ps.setInt(1, idRec);
//         ps.setInt(2, idIns);
//         return ps.executeUpdate() == 1;
//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     }
// }
    
//     public List<inv_entradas> listarEntradas() {
//     List<inv_entradas> lista = new ArrayList<>();
//     String sql = "SELECT * FROM inv_entradas ORDER BY fecha_hora DESC";
//     try {
//         con = cn.conexion();
//         ps = con.prepareStatement(sql);
//         rs = ps.executeQuery();
//         while (rs.next()) {
//             inv_entradas e = new inv_entradas();
//             e.setId_entrada(rs.getInt("id_entrada"));        // id de la entrada
//             e.setId_ins(rs.getInt("id_ins"));               // id del insumo
//             e.setCantidad(rs.getDouble("cantidad"));        // cantidad decimal
//             e.setFecha_hora(rs.getTimestamp("fecha_hora")); // fecha y hora
//             e.setUsuario(rs.getString("usuario"));          // usuario que registró
//             e.setObservacion(rs.getString("observacion"));  // observación / tipo de acción
//             lista.add(e);
//         }
//     } catch (Exception ex) {
//         ex.printStackTrace();
//     }
//     return lista;
// }

// // En insumosDao
// public boolean eliminarEntrada(int idEntrada) {
//     String sql = "DELETE FROM inv_entradas WHERE id_entrada = ?";
//     try (Connection con = cn.conexion();
//          PreparedStatement ps = con.prepareStatement(sql)) {
//         ps.setInt(1, idEntrada);
//         return ps.executeUpdate() == 1;
//     } catch (Exception e) {
//         e.printStackTrace();
//         return false;
//     }
// }


// }
