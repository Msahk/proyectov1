package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import control.ConDB;
import modelo.pago;

public class pagoDao {

    // 🔹 Agregar nuevo pago y devolver ID generado
    public int agregar(pago p) {
        String sql = "INSERT INTO pago (id_ven, fecha_pago, monto, tipo_pago) VALUES (?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getIdVenta());
            ps.setTimestamp(2, new Timestamp(p.getFechaPago().getTime()));
            ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getTipoPago());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            manejarError("Error al registrar pago", e);
        }

        return idGenerado;
    }

    // 🔹 Actualizar pago existente
    public boolean actualizar(pago p) {
        String sql = "UPDATE pago SET id_ven=?, fecha_pago=?, monto=?, tipo_pago=? WHERE id_pago=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getIdVenta());
            ps.setTimestamp(2, new Timestamp(p.getFechaPago().getTime()));
            ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getTipoPago());
            ps.setInt(5, p.getIdPago());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            manejarError("Error al actualizar pago", e);
            return false;
        }
    }

    // 🔹 Eliminar pago por ID
    public boolean eliminar(int idPago) {
        String sql = "DELETE FROM pago WHERE id_pago=?";
        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPago);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            manejarError("Error al eliminar pago", e);
            return false;
        }
    }

    // 🔹 Listar pagos por ID de venta
    public List<pago> listarPorVenta(int idVenta) {
        List<pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago WHERE id_ven=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pago p = new pago();
                    p.setIdPago(rs.getInt("id_pago"));
                    p.setIdVenta(rs.getInt("id_ven"));
                    p.setFechaPago(rs.getTimestamp("fecha_pago"));
                    p.setMonto(rs.getDouble("monto"));
                    p.setTipoPago(rs.getString("tipo_pago"));
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            manejarError("Error al listar pagos por venta", e);
        }

        return lista;
    }

    // 🔹 Calcular el total pagado por una venta
    public double totalPagosVenta(int idVenta) {
        double total = 0;
        String sql = "SELECT SUM(monto) AS totalPagos FROM pago WHERE id_ven=?";

        try (Connection con = ConDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("totalPagos");
                }
            }

        } catch (SQLException e) {
            manejarError("Error al obtener total de pagos", e);
        }

        return total;
    }

    // 🔹 Método auxiliar para manejar errores
    private void manejarError(String mensaje, SQLException e) {
        e.printStackTrace();
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, e.getMessage()));
        } else {
            System.err.println(mensaje + ": " + e.getMessage());
        }
    }
}
