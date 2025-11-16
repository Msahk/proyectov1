package dao;

import control.ConDB;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.detalle_insumo;

public class detalle_insumoDao {

    PreparedStatement ps;
    ResultSet rs;

    public List<detalle_insumo> listarDetalles() {
    List<detalle_insumo> lista = new ArrayList<>();

    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.estado <> 'Eliminado' "
               + "ORDER BY di.id_detalle DESC";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            detalle_insumo d = mapearDetalle(rs);
            lista.add(d);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}



    // 🔹 Agregar nuevo detalle de insumo
    public boolean agregar(detalle_insumo d) {
        String sql = "INSERT INTO detalle_insumo (id_ins, cantidad, fecha_ingreso, fecha_vencimiento, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (d.getFecha_ingreso() == null) {
                d.setFecha_ingreso(new java.util.Date());
            }

            d.setEstado(calcularEstado(d));

            ps.setInt(1, d.getId_ins());
            ps.setDouble(2, d.getCantidad());
            ps.setTimestamp(3, new Timestamp(d.getFecha_ingreso().getTime()));
            ps.setDate(4, new java.sql.Date(d.getFecha_vencimiento().getTime()));
            ps.setString(5, d.getEstado());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    d.setId_detalle(generatedKeys.getInt(1));
                }
            }

            if ("Activo".equalsIgnoreCase(d.getEstado())) {
                actualizarStock(d.getId_ins(), d.getCantidad());
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle de insumo agregado correctamente"));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el detalle"));
            return false;
        }
    }

    // 🔹 Actualizar detalle de insumo
    public boolean actualizar(detalle_insumo d) {
        String sql = "UPDATE detalle_insumo SET id_ins=?, cantidad=?, fecha_ingreso=?, fecha_vencimiento=?, estado=? WHERE id_detalle=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            detalle_insumo detalleAnterior = obtenerPorId(d.getId_detalle());
            double cantidadAnterior = detalleAnterior != null ? detalleAnterior.getCantidad() : 0;

            d.setEstado(calcularEstado(d));

            ps.setInt(1, d.getId_ins());
            ps.setDouble(2, d.getCantidad());
            ps.setTimestamp(3, new Timestamp(d.getFecha_ingreso().getTime()));
            ps.setDate(4, new java.sql.Date(d.getFecha_vencimiento().getTime()));
            ps.setString(5, d.getEstado());
            ps.setInt(6, d.getId_detalle());

            ps.executeUpdate();

            if ("Activo".equalsIgnoreCase(d.getEstado())) {
                double diferencia = d.getCantidad() - cantidadAnterior;
                actualizarStock(d.getId_ins(), diferencia);
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle de insumo actualizado correctamente"));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el detalle"));
            return false;
        }
    }

    // 🔹 Obtener detalle por ID
    public detalle_insumo obtenerPorId(int id) {
        detalle_insumo d = null;
        String sql = "SELECT di.*, i.nombre AS nombre_insumo "
                   + "FROM detalle_insumo di "
                   + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
                   + "WHERE di.id_detalle=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = mapearDetalle(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }

    // 🔹 Eliminar detalle
    public boolean eliminar(detalle_insumo d) {
        String sql = "DELETE FROM detalle_insumo WHERE id_detalle=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getId_detalle());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle eliminado correctamente"));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el detalle"));
            return false;
        }
    }

    // 🔹 Actualiza el stock de un insumo
    public boolean actualizarStock(int id_ins, double cantidad) {
        String sql = "UPDATE insumos SET stock_actual = stock_actual + ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, cantidad);
            ps.setInt(2, id_ins);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Registrar entrada en historial
    public boolean registrarHistorialEntrada(detalle_insumo d) {
        String sql = "INSERT INTO historial (fecha, accion, novedad, id_ins, id_detalle) "
                   + "VALUES (NOW(), 'Entrada', ?, ?, ?)";
        String unidad = "";

        try (Connection con = ConDB.conectar()) {

            String sqlUnidad = "SELECT unidad_medida FROM insumo WHERE id_ins = ?";
            try (PreparedStatement psUnidad = con.prepareStatement(sqlUnidad)) {
                psUnidad.setInt(1, d.getId_ins());
                try (ResultSet rs = psUnidad.executeQuery()) {
                    if (rs.next()) {
                        unidad = rs.getString("unidad_medida");
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "Ingreso de lote (" + d.getCantidad() + " " + unidad + ")");
                ps.setInt(2, d.getId_ins());
                ps.setInt(3, d.getId_detalle());
                ps.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Listar por insumo
    public List<detalle_insumo> listarPorInsumo(int id_insumo) {
        return listarPorCondicion("di.id_ins = ?", id_insumo);
    }

    // 🔹 Listar por estado
    public List<detalle_insumo> listarPorEstado(String estado) {
        return listarPorCondicion("di.estado = ?", estado);
    }

    // 🔹 Listar por insumo y estado
    public List<detalle_insumo> listarPorInsumoYEstado(int id_insumo, String estados) {
        List<detalle_insumo> lista = new ArrayList<>();
        String sql = "SELECT di.*, i.nombre AS nombre_insumo "
                   + "FROM detalle_insumo di "
                   + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
                   + "WHERE di.id_ins = ? AND di.estado IN (" + estadosString(estados) + ")"
                   + " ORDER BY di.fecha_ingreso ASC";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_insumo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalle(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 🔹 Listar eliminados por insumo
    public List<detalle_insumo> listarEliminadosPorInsumo(int id_insumo) {
        return listarPorCondicion("di.id_ins = ? AND di.estado = 'Eliminado'", id_insumo);
    }

    // 🔹 Descontar stock por producción (FIFO)
    public boolean descontarDeLotes(int idInsumo, double cantidadUsada) {
        String sqlSelect = "SELECT id_detalle, cantidad FROM detalle_insumo " +
                           "WHERE id_ins = ? AND estado = 'Activo' " +
                           "ORDER BY fecha_vencimiento ASC, fecha_ingreso ASC";

        String sqlUpdate = "UPDATE detalle_insumo SET cantidad = ?, estado = ? WHERE id_detalle = ?";

        try (Connection con = ConDB.conectar();
             PreparedStatement psSelect = con.prepareStatement(sqlSelect);
             PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {

            psSelect.setInt(1, idInsumo);
            ResultSet rs = psSelect.executeQuery();

            List<int[]> lotes = new ArrayList<>();
            double totalDisponible = 0;

            while (rs.next()) {
                int idDetalle = rs.getInt("id_detalle");
                double cantidad = rs.getDouble("cantidad");
                lotes.add(new int[]{idDetalle, (int)cantidad});
                totalDisponible += cantidad;
            }

            if (totalDisponible < cantidadUsada) {
                System.out.println("⚠ No hay suficiente cantidad para el insumo " + idInsumo);
                return false;
            }

            double restante = cantidadUsada;
            for (int[] lote : lotes) {
                int idDetalle = lote[0];
                double cantidadLote = lote[1];
                double nuevaCantidad = cantidadLote - restante;

                if (nuevaCantidad > 0) {
                    psUpdate.setDouble(1, nuevaCantidad);
                    psUpdate.setString(2, "Activo");
                    psUpdate.setInt(3, idDetalle);
                    psUpdate.executeUpdate();
                    break;
                } else {
                    psUpdate.setDouble(1, 0);
                    psUpdate.setString(2, "Agotado");
                    psUpdate.setInt(3, idDetalle);
                    psUpdate.executeUpdate();
                    restante = Math.abs(nuevaCantidad);
                }
            }

            actualizarStock(idInsumo, -cantidadUsada);
            System.out.println("✅ Descuento completado para insumo ID " + idInsumo);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Calcular estado de lote automáticamente
    public String calcularEstado(detalle_insumo d) {
        if ("Agotado".equalsIgnoreCase(d.getEstado()) || "Eliminado".equalsIgnoreCase(d.getEstado())) {
            return d.getEstado();
        }
        java.util.Date hoy = new java.util.Date();
        if (d.getFecha_vencimiento() != null && d.getFecha_vencimiento().before(hoy)) {
            return "Vencido";
        }
        return "Activo";
    }

    // 🔹 Calcular stock actual de un insumo
    public double calcularStockActual(int id_insumo) {
        double stock = 0;
        String sql = "SELECT SUM(cantidad) AS total FROM detalle_insumo WHERE id_ins=? AND estado='Activo'";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_insumo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stock = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    // 🔹 Actualizar estado del insumo global
    public boolean actualizarEstadoInsumo(int id_insumo, String nuevoEstado) {
        String sql = "UPDATE insumos SET estado = ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id_insumo);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Utilidades privadas

    // Mapear ResultSet a detalle_insumo


    // Genera cadena para SQL IN
    private String estadosString(String estados) {
        String[] arr = estados.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append("'").append(arr[i].trim()).append("'");
            if (i < arr.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    // Listar con condición genérica
    private List<detalle_insumo> listarPorCondicion(String condicion, Object parametro) {
        List<detalle_insumo> lista = new ArrayList<>();
        String sql = "SELECT di.*, i.nombre AS nombre_insumo FROM detalle_insumo di " +
                     "LEFT JOIN insumos i ON di.id_ins = i.id_ins WHERE " + condicion +
                     " ORDER BY di.fecha_ingreso ASC";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parametro instanceof Integer) {
                ps.setInt(1, (Integer)parametro);
            } else if (parametro instanceof String) {
                ps.setString(1, (String)parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalle(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public boolean eliminarLogico(int idDetalle) {
    String sql = "UPDATE detalle_insumo SET estado = 'Eliminado' WHERE id_detalle = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idDetalle);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        System.err.println("Error en eliminarLogico(): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    



private detalle_insumo mapearDetalle(ResultSet rs) throws SQLException {
    detalle_insumo d = new detalle_insumo();

    d.setId_detalle(rs.getInt("id_detalle"));
    d.setId_ins(rs.getInt("id_ins"));
    d.setCantidad(rs.getDouble("cantidad"));
    d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
    d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
    d.setEstado(rs.getString("estado"));

    // Campo adicional (JOIN con insumos)
    try {
        d.setNombre_insumo(rs.getString("nombre_insumo"));
    } catch (SQLException e) {
        // Si la consulta no trae nombre_insumo, simplemente lo ignora
        d.setNombre_insumo(null);
    }

    return d;
}

}
