package models;

import config.conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class detalle_produccionDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion cn = new conexion();

    public List<detalle_produccion> listarPorProduccion(int id_proc) {
        List<detalle_produccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_produccion WHERE id_proc = ?";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proc);
            rs = ps.executeQuery();
            while (rs.next()) {
                detalle_produccion d = new detalle_produccion();
                d.setId_detpro(rs.getInt("id_detpro"));
                d.setId_proc(rs.getInt("id_proc"));
                d.setId_sal(rs.getInt("id_sal"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles de producción: " + e.getMessage());
        }
        return lista;
    }

    public boolean agregar(detalle_produccion d) {
        String sql = "INSERT INTO detalle_produccion (id_proc, id_sal) VALUES (?, ?)";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, d.getId_proc());
            ps.setInt(2, d.getId_sal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar detalle_produccion: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarPorProduccion(int id_proc) {
        String sql = "DELETE FROM detalle_produccion WHERE id_proc = ?";

        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proc);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle_produccion: " + e.getMessage());
        }
        return false;
    }
}
