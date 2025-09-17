// package models;

// import config.conexion;
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class produccionDao {

//     conexion cn = new conexion();

//     // 🔹 Listar todas las producciones con sus recetas
//     public List<produccion> listar() {
//         List<produccion> lista = new ArrayList<>();
//         String sql = "SELECT * FROM produccion ORDER BY fecha_hora DESC";

//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

//             while (rs.next()) {
//                 produccion p = new produccion();
//                 p.setId_proc(rs.getInt("id_proc"));
//                 p.setFecha_produccion(rs.getDate("fecha_produccion"));
//                 p.setEstado(rs.getString("estado"));
//                 p.setUsuario(rs.getInt("usuario"));
//                 p.setFecha_hora(rs.getTimestamp("fecha_hora"));

//                 // Cargar recetas asociadas a esta producción
//                 p.setRecetas(listarRecetasPorProduccion(p.getId_proc()));

//                 lista.add(p);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return lista;
//     }

//     // 🔹 Guardar producción (sin descontar insumos)
//     public boolean guardarProduccion(produccion p) {
//         String sqlInsertProd = "INSERT INTO produccion (fecha_produccion, estado, usuario, fecha_hora) VALUES (?, ?, ?, NOW())";

//         try (Connection con = cn.conexion()) {
//             con.setAutoCommit(false);

//             // 1️⃣ Guardar producción
//             int idProduccion = 0;
//             try (PreparedStatement ps = con.prepareStatement(sqlInsertProd, Statement.RETURN_GENERATED_KEYS)) {
//                 // Convertir java.util.Date a java.sql.Date
//                 ps.setDate(1, new java.sql.Date(p.getFecha_produccion().getTime()));
//                 ps.setString(2, p.getEstado());
//                 ps.setInt(3, p.getUsuario());
//                 ps.executeUpdate();

//                 try (ResultSet rs = ps.getGeneratedKeys()) {
//                     if (rs.next()) {
//                         idProduccion = rs.getInt(1);
//                     }
//                 }
//             }

//             // 2️⃣ Guardar relaciones con recetas (sin descontar stock)
//             for (produccion_receta pr : p.getRecetas()) {
//                 String sqlInsertProdRec = "INSERT INTO produccion_recetas (id_produccion, id_rec, cantidad) VALUES (?, ?, ?)";
//                 try (PreparedStatement ps = con.prepareStatement(sqlInsertProdRec)) {
//                     ps.setInt(1, idProduccion);
//                     ps.setInt(2, pr.getId_rec());
//                     ps.setInt(3, pr.getCantidad());
//                     ps.executeUpdate();
//                 }
//             }

//             con.commit();
//             return true;

//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // 🔹 Actualizar estado de producción
//     public boolean actualizarEstado(produccion p) {
//         String sql = "UPDATE produccion SET estado = ? WHERE id_proc = ?";
//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setString(1, p.getEstado());
//             ps.setInt(2, p.getId_proc());

//             return ps.executeUpdate() > 0;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // 🔹 Listar recetas asociadas a una producción
//     public List<produccion_receta> listarRecetasPorProduccion(int idProduccion) {
//         List<produccion_receta> lista = new ArrayList<>();
//         String sql = "SELECT pr.id_rec, r.nombre, pr.cantidad "
//                 + "FROM produccion_recetas pr JOIN recetas r ON pr.id_rec = r.id_rec "
//                 + "WHERE pr.id_produccion = ?";

//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setInt(1, idProduccion);
//             try (ResultSet rs = ps.executeQuery()) {
//                 while (rs.next()) {
//                     produccion_receta pr = new produccion_receta();
//                     pr.setId_rec(rs.getInt("id_rec"));
//                     pr.setNombreReceta(rs.getString("nombre")); // atributo extra en produccion_receta
//                     pr.setCantidad(rs.getInt("cantidad"));
//                     lista.add(pr);
//                 }
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return lista;
//     }

//     // 🔹 Eliminar producción y sus recetas asociadas
//     public boolean eliminarProduccion(int idProd) {
//         String sql1 = "DELETE FROM produccion_recetas WHERE id_produccion = ?";
//         String sql2 = "DELETE FROM produccion WHERE id_proc = ?";

//         try (Connection con = cn.conexion()) {
//             con.setAutoCommit(false);

//             try (PreparedStatement ps1 = con.prepareStatement(sql1)) {
//                 ps1.setInt(1, idProd);
//                 ps1.executeUpdate();
//             }

//             try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
//                 ps2.setInt(1, idProd);
//                 ps2.executeUpdate();
//             }

//             con.commit();
//             return true;

//         } catch (Exception e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // 🔹 Buscar producción por ID
//     public produccion buscarPorId(int id) {
//         produccion p = new produccion();
//         String sql = "SELECT * FROM produccion WHERE id_proc = ?";

//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

//             ps.setInt(1, id);
//             try (ResultSet rs = ps.executeQuery()) {
//                 if (rs.next()) {
//                     p.setId_proc(rs.getInt("id_proc"));
//                     p.setFecha_produccion(rs.getDate("fecha_produccion"));
//                     p.setEstado(rs.getString("estado"));
//                     p.setUsuario(rs.getInt("usuario"));
//                     p.setFecha_hora(rs.getTimestamp("fecha_hora"));
//                 }
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return p;
//     }

//     // 🔹 Descontar stock de insumo
//     private void descontarInsumo(int id_ins, double cantidad, Connection con) throws SQLException {
//         String sqlCheck = "SELECT cantidad FROM insumos WHERE id_ins = ?";
//         try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
//             psCheck.setInt(1, id_ins);
//             try (ResultSet rs = psCheck.executeQuery()) {
//                 if (rs.next()) {
//                     double stockActual = rs.getDouble("cantidad");
//                     if (stockActual < cantidad) {
//                         throw new SQLException("Stock insuficiente para el insumo " + id_ins);
//                     }
//                 }
//             }
//         }

//         String sqlUpdate = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";
//         try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
//             psUpdate.setDouble(1, cantidad);
//             psUpdate.setInt(2, id_ins);
//             psUpdate.executeUpdate();
//         }
//     }

//     public boolean finalizarProduccion(int idProduccion) {
//     String sqlInsumos = "SELECT ri.id_ins, (ri.cantidad * pr.cantidad) AS total_cantidad "
//             + "FROM produccion_recetas pr "
//             + "INNER JOIN receta_insumos ri ON pr.id_rec = ri.id_rec "
//             + "WHERE pr.id_produccion = ?";

//     String sqlUpdateInsumo = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";
//     String sqlUpdateProduccion = "UPDATE produccion SET estado = 'FINALIZADA' WHERE id_proc = ?";

//     Connection con = null;
//     try {
//         con = cn.conexion();
//         con.setAutoCommit(false); // 🔹 Inicia transacción

//         // 1️⃣ Obtener insumos necesarios
//         try (PreparedStatement psInsumos = con.prepareStatement(sqlInsumos)) {
//             psInsumos.setInt(1, idProduccion);
//             ResultSet rs = psInsumos.executeQuery();

//             // 2️⃣ Preparar actualización de stock y registro de salida
//             try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateInsumo);
//                  PreparedStatement psSalida = con.prepareStatement(
//                          "INSERT INTO inv_salidas(id_ins, cantidad, fecha_hora, usuario, id_proc, observacion) "
//                                  + "VALUES (?, ?, NOW(), ?, ?, ?)")) {

//                 while (rs.next()) {
//                     int idIns = rs.getInt("id_ins");
//                     double cantidad = rs.getDouble("total_cantidad");

//                     // Verificar stock suficiente
//                     if (!hayStockSuficiente(idIns, cantidad, con)) {
//                         throw new SQLException("Stock insuficiente para insumo " + idIns);
//                     }

//                     // 🔹 Descontar stock
//                     psUpdate.setDouble(1, cantidad);
//                     psUpdate.setInt(2, idIns);
//                     psUpdate.addBatch();

//                     // 🔹 Registrar salida
//                     psSalida.setInt(1, idIns);
//                     psSalida.setDouble(2, cantidad);
//                     psSalida.setString(3, "Sistema"); // Usuario (por defecto, puedes pasarlo dinámico)
//                     psSalida.setInt(4, idProduccion);
//                     psSalida.setString(5, "Salida por producción finalizada");
//                     psSalida.addBatch();
//                 }

//                 psUpdate.executeBatch();
//                 psSalida.executeBatch();
//             }
//         }

//         // 3️⃣ Marcar producción como FINALIZADA
//         try (PreparedStatement psProduccion = con.prepareStatement(sqlUpdateProduccion)) {
//             psProduccion.setInt(1, idProduccion);
//             psProduccion.executeUpdate();
//         }

//         con.commit();
//         return true;

//     } catch (SQLException e) {
//         e.printStackTrace();
//         if (con != null) {
//             try {
//                 con.rollback(); // 🔹 Revertir transacción
//             } catch (SQLException ex) {
//                 ex.printStackTrace();
//             }
//         }
//         return false;
//     } finally {
//         if (con != null) {
//             try {
//                 con.setAutoCommit(true);
//                 con.close();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }


// // Método auxiliar para verificar stock
//     private boolean hayStockSuficiente(int id_ins, double cantidad, Connection con) throws SQLException {
//         String sqlCheck = "SELECT cantidad FROM insumos WHERE id_ins = ?";
//         try (PreparedStatement ps = con.prepareStatement(sqlCheck)) {
//             ps.setInt(1, id_ins);
//             try (ResultSet rs = ps.executeQuery()) {
//                 if (rs.next()) {
//                     return rs.getDouble("cantidad") >= cantidad;
//                 }
//             }
//         }
//         return false;
//     }

//     public List<inv_salidas> listarSalidas() {
//         List<inv_salidas> lista = new ArrayList<>();
//         String sql = "SELECT * FROM inv_salidas ORDER BY fecha_hora DESC";
//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

//             while (rs.next()) {
//                 inv_salidas s = new inv_salidas();
//                 s.setId_salida(rs.getInt("id_salida"));
//                 s.setId_ins(rs.getInt("id_ins"));
//                 s.setCantidad(rs.getDouble("cantidad"));
//                 s.setFecha_hora(rs.getTimestamp("fecha_hora"));
//                 s.setUsuario(rs.getString("usuario"));
//                 s.setId_proc(rs.getInt("id_proc"));
//                 s.setObservacion(rs.getString("observacion"));
//                 lista.add(s);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return lista;
//     }

//     public boolean eliminarSalida(int idSalida) {
//         String sql = "DELETE FROM inv_salidas WHERE id_salida = ?";
//         try (Connection con = cn.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {
//             ps.setInt(1, idSalida);
//             return ps.executeUpdate() == 1;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

// // 🔹 Actualizar recetas de una producción
//     public boolean actualizarRecetasProduccion(int idProd, List<produccion_receta> listaRecetas) {
//         String sqlEliminar = "DELETE FROM produccion_recetas WHERE id_produccion = ?";
//         String sqlInsertar = "INSERT INTO produccion_recetas (id_produccion, id_rec, cantidad) VALUES (?, ?, ?)";

//         try (Connection con = cn.conexion()) {
//             con.setAutoCommit(false);

//             // 1️⃣ Eliminar relaciones existentes
//             try (PreparedStatement psEliminar = con.prepareStatement(sqlEliminar)) {
//                 psEliminar.setInt(1, idProd);
//                 psEliminar.executeUpdate();
//             }

//             // 2️⃣ Insertar nuevas relaciones
//             try (PreparedStatement psInsertar = con.prepareStatement(sqlInsertar)) {
//                 for (produccion_receta pr : listaRecetas) {
//                     psInsertar.setInt(1, idProd);
//                     psInsertar.setInt(2, pr.getId_rec());
//                     psInsertar.setInt(3, pr.getCantidad());
//                     psInsertar.addBatch();
//                 }
//                 psInsertar.executeBatch();
//             }

//             con.commit();
//             return true;

//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

// }
