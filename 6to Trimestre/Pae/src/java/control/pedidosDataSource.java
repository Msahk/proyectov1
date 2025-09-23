package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.pedidos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class pedidosDataSource implements JRDataSource {
    private List<pedidos> lstPedidos;
    private int indice;

    public pedidosDataSource() {
        lstPedidos = new ArrayList<>();
        indice = -1;
        try {
            
            String sql = "SELECT p.*, v.id_Cliente, c.nombre AS nombreCliente " +
                         "FROM pedidos p " +
                         "LEFT JOIN ventas v ON p.id_ven = v.id_ven " +
                         "LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente " +
                         "ORDER BY p.id_ped DESC";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pedidos ped = new pedidos();
                ped.setIdPed(rs.getInt("id_ped"));
                ped.setIdVen(rs.getInt("id_ven"));
                ped.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                ped.setEstado(rs.getString("estado"));
                ped.setObservacionesPedido(rs.getString("observaciones_pedido")); 
                ped.setIdCliente(rs.getInt("id_Cliente"));
                ped.setNombreCliente(rs.getString("nombreCliente"));
                lstPedidos.add(ped);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public int getSize() {
    return lstPedidos.size();
}
    @Override
    public boolean next() throws JRException {
        indice++;
        return indice < lstPedidos.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        String nomcampo = jrf.getName();

        switch (nomcampo) {
            case "id_ped":
                valor = lstPedidos.get(indice).getIdPed();
                break;
            case "id_ven":
                valor = lstPedidos.get(indice).getIdVen();
                break;
            case "fecha_entrega":
                valor = lstPedidos.get(indice).getFechaEntrega();
                break;
            case "estado":
                valor = lstPedidos.get(indice).getEstado();
                break;
            case "observaciones_pedido":
                valor = lstPedidos.get(indice).getObservacionesPedido();
                break;
            case "nombreCliente":
                valor = lstPedidos.get(indice).getNombreCliente();
                break;
        }

        return valor;
    }
}