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

   public List<detalle_insumo> listar() {
    List<detalle_insumo> lista = new ArrayList<>();
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "ORDER BY di.id_detalle DESC";

    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            detalle_insumo d = new detalle_insumo();
            d.setId_detalle(rs.getInt("id_detalle"));
            d.setId_ins(rs.getInt("id_ins"));
            d.setCantidad(rs.getDouble("cantidad"));
            d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
            d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
            d.setEstado(rs.getString("estado"));
            d.setNombre_insumo(rs.getString("nombre_insumo"));

            // 🔹 Actualizar estado automáticamente
            d.setEstado(calcularEstado(d));

            lista.add(d);
        }

    } catch (SQLException e) {
        System.out.println("Error en listar(): " + e.getMessage());
        e.printStackTrace();
    }

    return lista;
}
  

    // ➕ Agregar nuevo detalle de insumo
public boolean agregar(detalle_insumo d) {
    String sql = "INSERT INTO detalle_insumo (id_ins, cantidad, fecha_ingreso, fecha_vencimiento, estado) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = ConDB.conectar(); 
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        if (d.getFecha_ingreso() == null) {
            d.setFecha_ingreso(new java.util.Date()); // fecha actual
        }

        // 🔹 Calcular estado automáticamente
        d.setEstado(calcularEstado(d));

        ps.setInt(1, d.getId_ins());
        ps.setDouble(2, d.getCantidad());
        ps.setTimestamp(3, new java.sql.Timestamp(d.getFecha_ingreso().getTime()));
        ps.setDate(4, new java.sql.Date(d.getFecha_vencimiento().getTime()));
        ps.setString(5, d.getEstado());

        ps.executeUpdate();

        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                d.setId_detalle(generatedKeys.getInt(1));
            }
        }

        // 🔹 Actualizar stock solo si el lote está Activo
        if ("Activo".equalsIgnoreCase(d.getEstado())) {
            actualizarStock(d.getId_ins(), d.getCantidad());
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle de insumo agregado correctamente"));
        return true;

    } catch (SQLException e) {
        System.out.println("Error en agregar(): " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el detalle"));
        e.printStackTrace();
        return false;
    }
}





    // ✏️ Actualizar detalle de insumo con cálculo automático de estado y ajuste de stock
public boolean actualizar(detalle_insumo d) {
    String sql = "UPDATE detalle_insumo SET id_ins=?, cantidad=?, fecha_ingreso=?, fecha_vencimiento=?, estado=? WHERE id_detalle=?";
    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

        // 🔹 Obtener cantidad anterior para ajustar stock
        detalle_insumo detalleAnterior = obtenerPorId(d.getId_detalle());
        double cantidadAnterior = detalleAnterior.getCantidad();

        // 🔹 Calcular estado automáticamente antes de actualizar
        d.setEstado(calcularEstado(d));

        ps.setInt(1, d.getId_ins());
        ps.setDouble(2, d.getCantidad());
        ps.setTimestamp(3, new java.sql.Timestamp(d.getFecha_ingreso().getTime()));
        ps.setDate(4, new java.sql.Date(d.getFecha_vencimiento().getTime()));
        ps.setString(5, d.getEstado());
        ps.setInt(6, d.getId_detalle());

        ps.executeUpdate();

        // 🔹 Ajustar stock solo si el lote está activo
        if ("Activo".equalsIgnoreCase(d.getEstado())) {
            double diferencia = d.getCantidad() - cantidadAnterior;
            actualizarStock(d.getId_ins(), diferencia);
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle de insumo actualizado correctamente"));
        return true;

    } catch (SQLException e) {
        System.out.println("Error en actualizar(): " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el detalle"));
        e.printStackTrace();
    }
    return false;
}


    // 🔍 Obtener detalle por ID
    // 🔍 Obtener detalle por ID con cálculo automático de estado
public detalle_insumo obtenerPorId(int id) {
    detalle_insumo d = null;
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.id_detalle=?";
    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                d = new detalle_insumo();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidad(rs.getDouble("cantidad"));
                d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                d.setEstado(rs.getString("estado"));
                d.setNombre_insumo(rs.getString("nombre_insumo"));

                // 🔹 Calcular estado automáticamente
                d.setEstado(calcularEstado(d));
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en obtenerPorId(): " + e.getMessage());
        e.printStackTrace();
    }
    return d;
}

    // 🗑️ Eliminar detalle
    public boolean eliminar(detalle_insumo d) {
        String sql = "DELETE FROM detalle_insumo WHERE id_detalle=?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, d.getId_detalle());
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle eliminado correctamente"));
            return true;
        } catch (SQLException e) {
            System.out.println("Error en eliminar(): " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el detalle"));
            return false;
        }
    }

    // ⚡ Actualiza el stock del insumo
    public boolean actualizarStock(int id_ins, double cantidad) {
        String sql = "UPDATE insumos SET stock_actual = stock_actual + ? WHERE id_ins = ?";
        try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, cantidad);
            ps.setInt(2, id_ins);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 📝 Registra la entrada en historial
    public boolean registrarHistorialEntrada(detalle_insumo d) {
    String sql = "INSERT INTO historial (fecha, accion, novedad, id_ins, id_detalle) "
               + "VALUES (NOW(), 'Entrada', ?, ?, ?)";
    String unidad = "";

    try (Connection con = ConDB.conectar()) {

        // 🔹 Obtener la unidad_medida del insumo asociado
        String sqlUnidad = "SELECT unidad_medida FROM insumo WHERE id_ins = ?";
        try (PreparedStatement psUnidad = con.prepareStatement(sqlUnidad)) {
            psUnidad.setInt(1, d.getId_ins());
            try (ResultSet rs = psUnidad.executeQuery()) {
                if (rs.next()) {
                    unidad = rs.getString("unidad_medida");
                }
            }
        }

        // 🔹 Registrar historial
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


    // dentro de detalle_insumoDao.java
    public List<detalle_insumo> listarPorInsumo(int id_insumo) {
    List<detalle_insumo> lista = new ArrayList<>();
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.id_ins = ? "
               + "ORDER BY di.fecha_ingreso ASC";

    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_insumo);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                detalle_insumo d = new detalle_insumo();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidad(rs.getDouble("cantidad"));
                d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                d.setEstado(rs.getString("estado"));
                d.setNombre_insumo(rs.getString("nombre_insumo"));

                // 🔹 Actualizar estado automáticamente
                d.setEstado(calcularEstado(d));

                lista.add(d);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


// dentro de detalle_insumoDao.java
    public List<detalle_insumo> listarPorEstado(String estado) {
    List<detalle_insumo> lista = new ArrayList<>();
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.estado = ? "
               + "ORDER BY di.fecha_ingreso ASC";

    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, estado);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                detalle_insumo d = new detalle_insumo();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidad(rs.getDouble("cantidad"));
                d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                d.setEstado(rs.getString("estado"));
                d.setNombre_insumo(rs.getString("nombre_insumo"));

                // 🔹 Actualizar estado automáticamente
                d.setEstado(calcularEstado(d));

                lista.add(d);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


   public List<detalle_insumo> listarPorInsumoYEstado(int id_insumo, String estados) {
    List<detalle_insumo> lista = new ArrayList<>();
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.id_ins = ? AND di.estado IN (" + estadosString(estados) + ")"
               + " ORDER BY di.fecha_ingreso ASC";

    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_insumo);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                detalle_insumo d = new detalle_insumo();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidad(rs.getDouble("cantidad"));
                d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                d.setEstado(rs.getString("estado"));
                d.setNombre_insumo(rs.getString("nombre_insumo"));

                // 🔹 Actualizar estado automáticamente
                d.setEstado(calcularEstado(d));

                lista.add(d);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


    public List<detalle_insumo> listarEliminadosPorInsumo(int id_insumo) {
    List<detalle_insumo> lista = new ArrayList<>();
    String sql = "SELECT di.*, i.nombre AS nombre_insumo "
               + "FROM detalle_insumo di "
               + "LEFT JOIN insumos i ON di.id_ins = i.id_ins "
               + "WHERE di.id_ins = ? AND di.estado = 'Eliminado' "
               + "ORDER BY di.fecha_ingreso ASC";

    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_insumo);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                detalle_insumo d = new detalle_insumo();
                d.setId_detalle(rs.getInt("id_detalle"));
                d.setId_ins(rs.getInt("id_ins"));
                d.setCantidad(rs.getDouble("cantidad"));
                d.setFecha_ingreso(rs.getTimestamp("fecha_ingreso"));
                d.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                d.setEstado(rs.getString("estado"));
                d.setNombre_insumo(rs.getString("nombre_insumo"));

                // 🔹 Aunque esté eliminado, recalcular por seguridad (opcional)
                d.setEstado(calcularEstado(d));

                lista.add(d);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


// Convierte "Activo,Vencido" a "'Activo','Vencido'" para SQL
    private String estadosString(String estados) {
        String[] arr = estados.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append("'").append(arr[i].trim()).append("'");
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
   public boolean descontarPorProduccion(int idInsumo, double cantidadUsada) {
    String sqlSelect =
    "SELECT id_detalle, cantidad, fecha_vencimiento, fecha_ingreso " +
    "FROM detalle_insumo " +
    "WHERE id_ins = ? AND estado = 'Activo' " +
    "ORDER BY fecha_vencimiento ASC, fecha_ingreso ASC";


    String sqlUpdate = "UPDATE detalle_insumo SET cantidad = ?, estado = ? WHERE id_detalle = ?";
    String sqlUpdateStock = "UPDATE insumos SET stock_actual = stock_actual - ? WHERE id_ins = ?";

    try (Connection con = ConDB.conectar();
         PreparedStatement psSelect = con.prepareStatement(sqlSelect);
         PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
         PreparedStatement psUpdateStock = con.prepareStatement(sqlUpdateStock)) {

        con.setAutoCommit(false); // 🔒 transacción

        psSelect.setInt(1, idInsumo);
        ResultSet rs = psSelect.executeQuery();

        double restante = cantidadUsada;

        while (rs.next() && restante > 0) {
            int idDetalle = rs.getInt("id_detalle");
            double cantidadLote = rs.getDouble("cantidad");

            double nuevoValor = cantidadLote - restante;
            String nuevoEstado = "Activo";

            if (nuevoValor <= 0) {
                nuevoEstado = "Agotado";
                nuevoValor = 0;
                restante = Math.abs(nuevoValor);
            } else {
                restante = 0;
            }

            psUpdate.setDouble(1, nuevoValor);
            psUpdate.setString(2, nuevoEstado);
            psUpdate.setInt(3, idDetalle);
            psUpdate.executeUpdate();
        }

        // 🔄 Actualiza stock global
        psUpdateStock.setDouble(1, cantidadUsada);
        psUpdateStock.setInt(2, idInsumo);
        psUpdateStock.executeUpdate();

        con.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    
    
}
   
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

        // 🧮 Paso 1: Calcular el total disponible en los lotes activos
        double totalDisponible = 0;
        List<int[]> lotes = new ArrayList<>(); // [id_detalle, cantidad]

        while (rs.next()) {
            lotes.add(new int[]{rs.getInt("id_detalle"), rs.getInt("cantidad")});
            totalDisponible += rs.getDouble("cantidad");
        }

        // 🚫 Si no hay suficiente stock, no hacemos nada
        if (totalDisponible < cantidadUsada) {
            System.out.println("⚠ No hay suficiente cantidad en los lotes para el insumo ID: " + idInsumo);
            return false;
        }

        // 🔁 Paso 2: Descontar en orden (por vencimiento y antigüedad)
        double restante = cantidadUsada;

        for (int[] lote : lotes) {
            int idDetalle = lote[0];
            double cantidadLote = lote[1];

            double nuevaCantidad = cantidadLote - restante;

            if (nuevaCantidad > 0) {
                // ✅ Descontó parcialmente de este lote y termina
                psUpdate.setDouble(1, nuevaCantidad);
                psUpdate.setString(2, "Activo");
                psUpdate.setInt(3, idDetalle);
                psUpdate.executeUpdate();
                restante = 0;
                break;
            } else {
                // ✅ Agotó este lote y sigue con el siguiente
                psUpdate.setDouble(1, 0);
                psUpdate.setString(2, "Agotado");
                psUpdate.setInt(3, idDetalle);
                psUpdate.executeUpdate();
                restante = Math.abs(nuevaCantidad);
            }
        }

        System.out.println("✅ Descuento completado para insumo ID " + idInsumo + " | Cantidad usada: " + cantidadUsada);
        return true;

    } catch (SQLException e) {
        System.err.println("❌ Error en descontarDeLotes(): " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// ⚡ Actualiza automáticamente el estado de un lote según su fecha de vencimiento
public String calcularEstado(detalle_insumo d) {
    if ("Agotado".equalsIgnoreCase(d.getEstado()) || "Eliminado".equalsIgnoreCase(d.getEstado())) {
        return d.getEstado(); // no se cambia
    }
    java.util.Date hoy = new java.util.Date();
    if (d.getFecha_vencimiento() != null && d.getFecha_vencimiento().before(hoy)) {
        return "Vencido";
    }
    return "Activo";
}

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

// Dentro de detalle_insumoDao.java
public boolean actualizarEstadoInsumo(int id_insumo, String nuevoEstado) {
    String sql = "UPDATE insumos SET estado = ? WHERE id_ins = ?";
    try (Connection con = ConDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nuevoEstado);
        ps.setInt(2, id_insumo);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
