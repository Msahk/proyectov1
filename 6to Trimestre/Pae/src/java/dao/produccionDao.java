package dao;

import control.ConDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.produccion;
import modelo.produccion_receta;

public class produccionDao {

    PreparedStatement ps;
    ResultSet rs;

    // 🔹 Listar todas las producciones con sus recetas
    public List<produccion> listar() {
        List<produccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM produccion ORDER BY fecha_hora DESC";

        try {
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                produccion p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getInt("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setRecetas(listarRecetasPorProduccion(p.getId_proc()));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error listar producciones: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // 🔹 Agregar producción con recetas (sin descontar stock)
    public boolean agregarProduccion(produccion p) {
        String sqlProd = "INSERT INTO produccion (fecha_produccion, estado, usuario, fecha_hora) VALUES (?, ?, ?, NOW())";
        String sqlRec = "INSERT INTO produccion_recetas (id_produccion, id_rec, cantidad) VALUES (?, ?, ?)";

        try {
            ps = ConDB.conectar().prepareStatement(sqlProd, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(p.getFecha_produccion().getTime()));
            ps.setString(2, p.getEstado());
            ps.setInt(3, p.getUsuario());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            int idProduccion = 0;
            if (rs.next()) {
                idProduccion = rs.getInt(1);
            } else {
                return false;
            }

            // Insertar recetas
            for (produccion_receta pr : p.getRecetas()) {
                ps = ConDB.conectar().prepareStatement(sqlRec);
                ps.setInt(1, idProduccion);
                ps.setInt(2, pr.getId_rec());
                ps.setDouble(3, pr.getCantidad());
                ps.executeUpdate();
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producción agregada correctamente"));
            return true;
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error agregando producción"));
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Listar recetas por producción
    public List<produccion_receta> listarRecetasPorProduccion(int idProd) {
        List<produccion_receta> lista = new ArrayList<>();
        String sql = "SELECT pr.id_rec, r.nombre, pr.cantidad "
                   + "FROM produccion_recetas pr "
                   + "JOIN recetas r ON pr.id_rec = r.id_rec "
                   + "WHERE pr.id_produccion = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idProd);
            rs = ps.executeQuery();
            while (rs.next()) {
                produccion_receta pr = new produccion_receta();
                pr.setId_rec(rs.getInt("id_rec"));
                pr.setNombreReceta(rs.getString("nombre"));
                pr.setCantidad(rs.getInt("cantidad"));
                lista.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error listar recetas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // 🔹 Actualizar estado de producción
    public boolean actualizarEstado(produccion p) {
        String sql = "UPDATE produccion SET estado = ? WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, p.getEstado());
            ps.setInt(2, p.getId_proc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar estado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Buscar producción por ID
    public produccion obtenerPorId(int id) {
        produccion p = null;
        String sql = "SELECT * FROM produccion WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new produccion();
                p.setId_proc(rs.getInt("id_proc"));
                p.setFecha_produccion(rs.getDate("fecha_produccion"));
                p.setEstado(rs.getString("estado"));
                p.setUsuario(rs.getInt("usuario"));
                p.setFecha_hora(rs.getTimestamp("fecha_hora"));
                p.setRecetas(listarRecetasPorProduccion(id));
            }
        } catch (SQLException e) {
            System.out.println("Error obtenerPorId: " + e.getMessage());
            e.printStackTrace();
        }
        return p;
    }

    // 🔹 Eliminar producción y sus recetas
    public boolean eliminar(int idProd) {
        String sqlRec = "DELETE FROM produccion_recetas WHERE id_produccion = ?";
        String sqlProd = "DELETE FROM produccion WHERE id_proc = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sqlRec);
            ps.setInt(1, idProd);
            ps.executeUpdate();

            ps = ConDB.conectar().prepareStatement(sqlProd);
            ps.setInt(1, idProd);
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Producción eliminada correctamente"));
            return true;
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando producción"));
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Actualizar recetas de una producción
    public boolean actualizarRecetas(int idProd, List<produccion_receta> listaRecetas) {
        String sqlEliminar = "DELETE FROM produccion_recetas WHERE id_produccion = ?";
        String sqlInsertar = "INSERT INTO produccion_recetas (id_produccion, id_rec, cantidad) VALUES (?, ?, ?)";

        try {
            ps = ConDB.conectar().prepareStatement(sqlEliminar);
            ps.setInt(1, idProd);
            ps.executeUpdate();

            for (produccion_receta pr : listaRecetas) {
                ps = ConDB.conectar().prepareStatement(sqlInsertar);
                ps.setInt(1, idProd);
                ps.setInt(2, pr.getId_rec());
                ps.setDouble(3, pr.getCantidad());
                ps.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error actualizar recetas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Finalizar producción y descontar insumos
    public boolean finalizarProduccion(int idProd, String usuario) {
        String sqlInsumos = "SELECT ri.id_ins, (ri.cantidad * pr.cantidad) AS total_cantidad "
                          + "FROM produccion_recetas pr "
                          + "JOIN receta_insumos ri ON pr.id_rec = ri.id_rec "
                          + "WHERE pr.id_produccion = ?";

        String sqlUpdateInsumo = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";
        String sqlInsertSalida = "INSERT INTO inv_salidas(id_ins, cantidad, fecha_hora, usuario, id_proc, observacion) "
                               + "VALUES (?, ?, NOW(), ?, ?, ?)";
        String sqlUpdateProd = "UPDATE produccion SET estado = 'FINALIZADA' WHERE id_proc = ?";

        try {
            Connection con = ConDB.conectar();
            con.setAutoCommit(false);

            List<StockCambio> cambios = new ArrayList<>();
            // Obtener insumos
            ps = con.prepareStatement(sqlInsumos);
            ps.setInt(1, idProd);
            rs = ps.executeQuery();
            while (rs.next()) {
                cambios.add(new StockCambio(rs.getInt("id_ins"), rs.getDouble("total_cantidad")));
            }

            // Verificar stock
            for (StockCambio sc : cambios) {
                if (!hayStockSuficiente(sc.id_ins, sc.cantidad, con)) {
                    throw new SQLException("Stock insuficiente insumo ID " + sc.id_ins);
                }
            }

            // Actualizar stock y registrar salidas
            for (StockCambio sc : cambios) {
                ps = con.prepareStatement(sqlUpdateInsumo);
                ps.setDouble(1, sc.cantidad);
                ps.setInt(2, sc.id_ins);
                ps.executeUpdate();

                ps = con.prepareStatement(sqlInsertSalida);
                ps.setInt(1, sc.id_ins);
                ps.setDouble(2, sc.cantidad);
                ps.setString(3, usuario);
                ps.setInt(4, idProd);
                ps.setString(5, "Salida por producción finalizada");
                ps.executeUpdate();
            }

            // Marcar producción como FINALIZADA
            ps = con.prepareStatement(sqlUpdateProd);
            ps.setInt(1, idProd);
            ps.executeUpdate();

            con.commit();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error finalizar producción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Verificar stock suficiente
    private boolean hayStockSuficiente(int id_ins, double cantidad, Connection con) throws SQLException {
        String sql = "SELECT cantidad FROM insumos WHERE id_ins = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, id_ins);
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("cantidad") >= cantidad;
        }
        return false;
    }

    // 🔹 Listar salidas de insumos
   

    // 🔹 Eliminar salida
    public boolean eliminarSalida(int idSalida) {
        String sql = "DELETE FROM inv_salidas WHERE id_salida = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, idSalida);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar salida: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Clase interna para manejo de stock
    private static class StockCambio {
        int id_ins;
        double cantidad;

        public StockCambio(int id_ins, double cantidad) {
            this.id_ins = id_ins;
            this.cantidad = cantidad;
        }
    }
}
