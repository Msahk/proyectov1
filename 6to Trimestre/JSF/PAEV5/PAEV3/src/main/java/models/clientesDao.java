// package models;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// import config.conexion;
// import java.util.ArrayList;
// import java.util.List;

// public class clientesDao {

//     conexion cn = new conexion();
//     Connection conn = null;
//     PreparedStatement ps = null;
//     ResultSet rs = null;
    
    
//     public clientes obtenerPorId(int id) throws ClassNotFoundException {
//     clientes clie = null;
//     String sql = "SELECT * FROM clientes WHERE id_Cliente = ?";

//     try {
//         conn = cn.conexion();
//         ps = conn.prepareStatement(sql);
//         ps.setInt(1, id);
//         rs = ps.executeQuery();

//         if (rs.next()) {
//             clie = new clientes();
//             clie.setId_Cliente(rs.getInt("id_Cliente"));
//             clie.setNombre(rs.getString("nombre"));
//             clie.setTelefono(rs.getString("telefono"));
//             clie.setCorreo(rs.getString("correo"));
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
//     return clie;
// }
    

    
// }
