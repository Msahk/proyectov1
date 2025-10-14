package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import control.ConDB;
import modelo.insumos;

public class insumosDao {
    PreparedStatement ps;
    ResultSet rs;

    // 🧾 Listar todos los insumos
    public List<insumos> listar() {
        List<insumos> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM insumos ORDER BY nombre";
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();

            Date hoy = new Date();

            while (rs.next()) {
                insumos i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getDouble("cantidad"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                i.setEstado(rs.getString("estado"));

                // 🔍 Verificar fecha de vencimiento y actualizar estado automáticamente
                if (i.getFecha_vencimiento() != null && i.getFecha_vencimiento().before(hoy)) {
                    if (!"Inactivo".equalsIgnoreCase(i.getEstado())) {
                        actualizarEstado(i.getId_ins(), "Inactivo");
                        i.setEstado("Inactivo");
                    }
                } else {
                    if (!"Activo".equalsIgnoreCase(i.getEstado())) {
                        actualizarEstado(i.getId_ins(), "Activo");
                        i.setEstado("Activo");
                    }
                }

                lista.add(i);
            }

        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ➕ Agregar nuevo insumo
    public boolean agregar(insumos i) {
        String sql = "INSERT INTO insumos (nombre, cantidad, unidad_medida, stock_min, stock_actual, fecha_vencimiento, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setDouble(4, i.getStock_min());
            ps.setDouble(5, i.getStock_actual());
            ps.setDate(6, new java.sql.Date(i.getFecha_vencimiento().getTime()));

            // Estado automático según fecha
            String estado = (i.getFecha_vencimiento() != null && i.getFecha_vencimiento().before(new Date())) ? "Inactivo" : "Activo";
            ps.setString(7, estado);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
        }
        return false;
    }

    // ✏️ Actualizar un insumo
    public boolean actualizar(insumos i) {
        String sql = "UPDATE insumos SET nombre = ?, cantidad = ?, unidad_medida = ?, stock_min = ?, stock_actual = ?, fecha_vencimiento = ?, estado = ? WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setDouble(4, i.getStock_min());
            ps.setDouble(5, i.getStock_actual());
            ps.setDate(6, new java.sql.Date(i.getFecha_vencimiento().getTime()));

            // Estado según caducidad
            String estado = (i.getFecha_vencimiento() != null && i.getFecha_vencimiento().before(new Date())) ? "Inactivo" : "Activo";
            ps.setString(7, estado);

            ps.setInt(8, i.getId_ins());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // 🔍 Obtener por ID
    public insumos obtenerPorId(int id) {
        insumos i = null;
        String sql = "SELECT * FROM insumos WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getDouble("cantidad"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                i.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    // ❌ Eliminar
    public void eliminar(insumos i) {
        String sql = "DELETE FROM insumos WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, i.getId_ins());
            ps.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Insumo eliminado exitosamente"));
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error eliminando insumo"));
            e.printStackTrace();
        }
    }

    // 🔎 Verificar si el nombre ya existe
    public boolean existeNombre(String nombre) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM insumos WHERE nombre = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error en existeNombre(): " + e.getMessage());
        }
        return existe;
    }

    // ⚙️ Método para actualizar solo el estado
    public void actualizarEstado(int id_ins, String estado) {
        String sql = "UPDATE insumos SET estado = ? WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id_ins);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en actualizarEstado(): " + e.getMessage());
        }
    }
    
    public insumos obtenerPorNombre(String nombre) {
    insumos i = null;
    String sql = "SELECT * FROM insumos WHERE nombre = ?";

    try (Connection con = control.ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombre);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getDouble("cantidad"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(rs.getDouble("stock_min"));
                i.setStock_actual(rs.getDouble("stock_actual"));
                i.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                i.setEstado(rs.getString("estado"));
            }
        }
    } catch (Exception e) {
        System.out.println("Error en obtenerPorNombre: " + e.getMessage());
    }
    return i;
}
    
    // 🔹 Descontar stock de un insumo (usado al finalizar producción)
public boolean descontarStock(Integer id_ins, double cantidadUsada) {
    String sql = "UPDATE insumos SET stock_actual = stock_actual - ? WHERE id_ins = ?";
    try (Connection con = ConDB.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, cantidadUsada);
        ps.setInt(2, id_ins);

        int filas = ps.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        System.out.println("Error en descontarStock(): " + e.getMessage());
        e.printStackTrace();
    }
    return false;
}


}
