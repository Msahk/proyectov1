// package models;

// import config.conexion;
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class entradaDao {

//     public static List<entrada> obtenerPorInsumo(int id_ins) {
//         List<entrada> lista = new ArrayList<>();

//         try {
//             // Usamos tu clase conexion
//             config.conexion cn = new config.conexion();
//             Connection con = cn.conexion();

//             String sql = "SELECT id_ent, fecha, cantidad, id_ins FROM inv_entradas WHERE id_ins = ?";
//             PreparedStatement ps = con.prepareStatement(sql);
//             ps.setInt(1, id_ins);
//             ResultSet rs = ps.executeQuery();

//             while (rs.next()) {
//                 entrada e = new entrada();
//                 e.setId_ent(rs.getInt("id_ent"));
//                 e.setFecha(rs.getDate("fecha"));
//                 e.setCantidad(rs.getInt("cantidad")); // Asegúrate que cantidad es tipo DECIMAL
//                 e.setId_ins(rs.getInt("id_ins"));
//                 lista.add(e);
//             }

//             rs.close();
//             ps.close();
//             con.close();
//         } catch (Exception e) {
//             System.out.println("Error en entradaDao.obtenerPorInsumo: " + e.getMessage());
//         }

//         return lista;
//     }
// }
