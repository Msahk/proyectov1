package models;

import config.conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class insumosDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion cn = new conexion();

    public insumosDao() {}

    public List<insumos> listar() {
        List<insumos> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                insumos i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getDouble("cantidad"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(Double.parseDouble(rs.getString("stock_min")));
                lista.add(i);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar insumos: " + e.getMessage());
        }
        return lista;
    }

    public int agregar(insumos i) {
        int idGenerado = -1;
        String sql = "INSERT INTO insumos (nombre, cantidad, unidad_medida, stock_min) VALUES (?, ?, ?, ?)";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setString(4, String.valueOf(i.getStock_min()));  // stock_min es VARCHAR(45)
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);

                // Insertamos en inv_entradas
                String entradaSql = "INSERT INTO inv_entradas (fecha, cantidad, id_ins) VALUES (CURRENT_DATE, ?, ?)";
                ps = con.prepareStatement(entradaSql);
                ps.setDouble(1, i.getCantidad());
                ps.setInt(2, idGenerado);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar insumo y entrada: " + e.getMessage());
        }
        return idGenerado;
    }

    public insumos obtenerPorId(int id) {
        String sql = "SELECT * FROM insumos WHERE id_ins = ?";
        insumos i = null;

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                i = new insumos();
                i.setId_ins(rs.getInt("id_ins"));
                i.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getDouble("cantidad"));
                i.setUnidad_medida(rs.getString("unidad_medida"));
                i.setStock_min(Double.parseDouble(rs.getString("stock_min")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener insumo: " + e.getMessage());
        }

        return i;
    }

    public boolean actualizar(insumos i) {
        String sql = "UPDATE insumos SET nombre = ?, cantidad = ?, unidad_medida = ?, stock_min = ? WHERE id_ins = ?";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, i.getNombre());
            ps.setDouble(2, i.getCantidad());
            ps.setString(3, i.getUnidad_medida());
            ps.setString(4, String.valueOf(i.getStock_min()));
            ps.setInt(5, i.getId_ins());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar insumo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM insumos WHERE id_ins = ?";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar insumo: " + e.getMessage());
            return false;
        }
    }
}
