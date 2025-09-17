// package models;

// import config.conexion;
// import java.sql.*;
// import java.util.*;

// public class recetasDao {

//     Connection con;
//     conexion cn = new conexion();
//     PreparedStatement ps;
//     ResultSet rs;

//     public List<recetas> listar() {
//         List<recetas> lista = new ArrayList<>();
//         String sql = "SELECT * FROM recetas";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             rs = ps.executeQuery();
//             while (rs.next()) {
//                 recetas r = new recetas();
//                 r.setId_rec(rs.getInt("id_rec"));
//                 r.setNombre(rs.getString("nombre"));
//                 r.setDescripcion(rs.getString("descripcion"));
//                 lista.add(r);
//             }
//         } catch (SQLException e) {
//             System.out.println("Error al listar recetas: " + e.getMessage());
//         }
//         return lista;
//     }

//     public boolean agregar(recetas r) {
//         String sql = "INSERT INTO recetas(nombre, descripcion) VALUES (?, ?)";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setString(1, r.getNombre());
//             ps.setString(2, r.getDescripcion());
//             ps.executeUpdate();
//             return true;
//         } catch (SQLException e) {
//             System.out.println("Error al agregar receta: " + e.getMessage());
//         }
//         return false;
//     }

//     public recetas buscarPorId(int id) {
//         String sql = "SELECT * FROM recetas WHERE id_rec = ?";
//         recetas r = new recetas();
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id);
//             rs = ps.executeQuery();
//             if (rs.next()) {
//                 r.setId_rec(rs.getInt("id_rec"));
//                 r.setNombre(rs.getString("nombre"));
//                 r.setDescripcion(rs.getString("descripcion"));
//             }
//         } catch (SQLException e) {
//             System.out.println("Error al buscar receta: " + e.getMessage());
//         }
//         return r;
//     }

//     public boolean actualizar(recetas r) {
//         String sql = "UPDATE recetas SET nombre = ?, descripcion = ? WHERE id_rec = ?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setString(1, r.getNombre());
//             ps.setString(2, r.getDescripcion());
//             ps.setInt(3, r.getId_rec());
//             ps.executeUpdate();
//             return true;
//         } catch (SQLException e) {
//             System.out.println("Error al actualizar receta: " + e.getMessage());
//         }
//         return false;
//     }

//     public boolean eliminar(int id) {
//         String sql = "DELETE FROM recetas WHERE id_rec = ?";
//         try {
//             con = cn.conexion();
//             ps = con.prepareStatement(sql);
//             ps.setInt(1, id);
//             ps.executeUpdate();
//             return true;
//         } catch (SQLException e) {
//             System.out.println("Error al eliminar receta: " + e.getMessage());
//         }
//         return false;
//     }
    
//     // 🔹 Buscar una receta por su nombre
// public recetas buscarPorNombre(String nombre) {
//     recetas r = null;
//     String sql = "SELECT * FROM recetas WHERE nombre = ?";
//     try {
//         con = cn.conexion();
//         ps = con.prepareStatement(sql);
//         ps.setString(1, nombre);
//         rs = ps.executeQuery();
//         if (rs.next()) {
//             r = new recetas();
//             r.setId_rec(rs.getInt("id_rec"));
//             r.setNombre(rs.getString("nombre"));
//             r.setDescripcion(rs.getString("descripcion"));
//         }
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
//     return r;
// }

// }
