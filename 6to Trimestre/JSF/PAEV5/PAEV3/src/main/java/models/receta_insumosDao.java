// package models;

// import config.conexion;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class receta_insumosDao {
    
//     private Connection con;
//     private conexion cn = new conexion();
//     private PreparedStatement ps;
//     private ResultSet rs;

//     // ➕ Agregar un insumo a una receta
//     public boolean agregar(receta_insumos ri) {
//         String sql = "INSERT INTO receta_insumos (id_rec, id_ins, cantidad, unidad) VALUES (?, ?, ?, ?)";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, ri.getId_rec());
//             ps.setInt(2, ri.getId_ins());
//             ps.setDouble(3, ri.getCantidad());
//             ps.setString(4, ri.getUnidad());
//             return ps.executeUpdate() == 1;
//         } catch (SQLException e) {
//             System.out.println("❌ Error al agregar receta_insumo: " + e.getMessage());
//         }
//         return false;
//     }

//     // ✏️ Actualizar cantidad/unidad de un insumo
//     public boolean actualizar(receta_insumos ri) {
//         String sql = "UPDATE receta_insumos SET cantidad = ?, unidad = ? WHERE id_rec_ins = ?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setDouble(1, ri.getCantidad());
//             ps.setString(2, ri.getUnidad());
//             ps.setInt(3, ri.getId_rec_ins());
//             return ps.executeUpdate() == 1;
//         } catch (SQLException e) {
//             System.out.println("❌ Error al actualizar receta_insumo: " + e.getMessage());
//         }
//         return false;
//     }

//     // 🗑 Eliminar un insumo por ID
//     public boolean eliminarPorId(int id_rec_ins) {
//         String sql = "DELETE FROM receta_insumos WHERE id_rec_ins = ?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id_rec_ins);
//             return ps.executeUpdate() == 1;
//         } catch (SQLException e) {
//             System.out.println("❌ Error al eliminar receta_insumo: " + e.getMessage());
//         }
//         return false;
//     }

//     // 📜 Listar insumos de una receta (para JSP y JSON)
//     public List<receta_insumos> listarPorReceta(int id_rec) {
//         List<receta_insumos> lista = new ArrayList<>();
//         String sql = "SELECT ri.id_rec_ins, ri.id_rec, ri.id_ins, ri.cantidad, ri.unidad, i.nombre AS nombre_insumo " +
//                      "FROM receta_insumos ri " +
//                      "JOIN insumos i ON ri.id_ins = i.id_ins " +
//                      "WHERE ri.id_rec = ?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id_rec);
//             rs = ps.executeQuery();
//             while (rs.next()) {
//                 receta_insumos ri = new receta_insumos();
//                 ri.setId_rec_ins(rs.getInt("id_rec_ins"));
//                 ri.setId_rec(rs.getInt("id_rec"));
//                 ri.setId_ins(rs.getInt("id_ins"));
//                 ri.setCantidad(rs.getDouble("cantidad"));
//                 ri.setUnidad(rs.getString("unidad"));
//                 ri.setNombre_insumo(rs.getString("nombre_insumo"));
//                 lista.add(ri);
//             }
//         } catch (SQLException e) {
//             System.out.println("❌ Error al listar receta_insumos: " + e.getMessage());
//         }
//         return lista;
//     }

//     // 🔍 Obtener un insumo específico por ID
//     public receta_insumos obtenerPorId(int id_rec_ins) {
//         String sql = "SELECT ri.id_rec_ins, ri.id_rec, ri.id_ins, ri.cantidad, ri.unidad, i.nombre AS nombre_insumo " +
//                      "FROM receta_insumos ri " +
//                      "JOIN insumos i ON ri.id_ins = i.id_ins " +
//                      "WHERE ri.id_rec_ins = ?";
//         receta_insumos ri = null;
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id_rec_ins);
//             rs = ps.executeQuery();
//             if (rs.next()) {
//                 ri = new receta_insumos();
//                 ri.setId_rec_ins(rs.getInt("id_rec_ins"));
//                 ri.setId_rec(rs.getInt("id_rec"));
//                 ri.setId_ins(rs.getInt("id_ins"));
//                 ri.setCantidad(rs.getDouble("cantidad"));
//                 ri.setUnidad(rs.getString("unidad"));
//                 ri.setNombre_insumo(rs.getString("nombre_insumo"));
//             }
//         } catch (SQLException e) {
//             System.out.println("❌ Error al obtener receta_insumo: " + e.getMessage());
//         }
//         return ri;
//     }
// }
