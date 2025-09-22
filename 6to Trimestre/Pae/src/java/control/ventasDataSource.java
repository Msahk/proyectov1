
package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ventas;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ventasDataSource implements JRDataSource{
    private List<ventas> lstVen;
    private int indice;

    public ventasDataSource() {
        lstVen = new ArrayList<>();
        indice = -1;
        try {
          String sql = "SELECT v.*, c.nombre AS nombreCliente, u.nombres AS nombreUsuario FROM ventas v LEFT JOIN clientes c ON v.id_Cliente = c.id_Cliente LEFT JOIN usuarios u ON v.id_usu = u.id_usu ORDER BY v.id_ven DESC";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()) {
                ventas usu = new ventas();
                usu.setIdVen(rs.getInt("id_ven"));
                usu.setTipo(rs.getString("Tipo"));
                usu.setFecha(rs.getTimestamp("fecha"));
                usu.setTotal(rs.getDouble("total"));
                usu.setEstado(rs.getString("estado"));
                usu.setObservaciones(rs.getString("observaciones"));
                usu.setNombreCliente(rs.getString("nombreCliente"));
                usu.setNombreUsuario(rs.getString("nombreUsuario"));

                lstVen.add(usu);
            }  
        } catch (SQLException e) {
        }
    }
    
    

    @Override
    public boolean next() throws JRException {
        indice++;
        return indice < lstVen.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        String nomcampo = jrf.getName();
        
        switch (nomcampo) {
            case "id_ven":
                valor = lstVen.get(indice).getIdVen();
                break;
            case "Tipo":
                valor = lstVen.get(indice).getTipo();
                break;
            case "fecha":
               valor = lstVen.get(indice).getFecha();
                break;
            case "total":
                valor = lstVen.get(indice).getTotal();
                break;
            case "estado":
              valor = lstVen.get(indice).getEstado();
                break;
            case "observaciones":
                 valor = lstVen.get(indice).getObservaciones();
                break;
            case "nombreCliente":
                 valor = lstVen.get(indice).getNombreCliente();
                break;
            case "nombreUsuario":
                 valor = lstVen.get(indice).getNombreUsuario();
                break;
        }
        
        return valor;
    }
}
