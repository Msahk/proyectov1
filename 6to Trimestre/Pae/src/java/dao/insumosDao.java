package dao;

import control.ConDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.insumos;

public class insumosDao {
    PreparedStatement ps;
    ResultSet rs;

    
    public List<insumos> listar() {
        List<insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                insumos obj = new insumos();
                obj.setId_ins(rs.getInt("id_ins"));
                obj.setNombre(rs.getString("nombre"));
                obj.setCantidad(rs.getDouble("cantidad"));
                obj.setUnidad_medida(rs.getString("unidad_medida"));
                obj.setStock_min(rs.getDouble("stock_min"));
                lista.add(obj);
            }
        } catch (SQLException e) {
            System.out.println("Error en listar(): " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Agregar nuevo insumo
    public boolean agregar(insumos i) {
        String sql = "INSERT INTO insumos (nombre, cantidad, unidad_medida, stock_min) VALUES (?, ?, ?, ?)";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setDouble(4, i.getStock_min());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en agregar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar insumo
    public boolean actualizar(insumos i) {
        String sql = "UPDATE insumos SET nombre = ?, cantidad = ?, unidad_medida = ?, stock_min = ? WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setDouble(4, i.getStock_min());
            ps.setInt(5, i.getId_ins());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error en actualizar(): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Obtener insumo por ID
    public insumos obtenerPorId(int id) {
        insumos ins = null;
        String sql = "SELECT * FROM insumos WHERE id_ins = ?";
        try {
            ps = ConDB.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                ins = new insumos();
                ins.setId_ins(rs.getInt("id_ins"));
                ins.setNombre(rs.getString("nombre"));
                ins.setCantidad(rs.getDouble("cantidad"));
                ins.setUnidad_medida(rs.getString("unidad_medida"));
                ins.setStock_min(rs.getDouble("stock_min"));
            }
        } catch (SQLException e) {
            System.out.println("Error en obtenerPorId(): " + e.getMessage());
            e.printStackTrace();
        }
        return ins;
    }

    // Eliminar insumo
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
}
