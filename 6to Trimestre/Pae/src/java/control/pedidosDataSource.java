
package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.pedidos;
import modelo.usuarios;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class pedidosDataSource implements JRDataSource{
    private List<pedidos> lstUsu;
    private int indice;

    public pedidosDataSource() {
        lstUsu = new ArrayList<>();
        indice = -1;
        try {
          String sql = "SELECT * FROM usuarios";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()) {
                pedidos usu = new pedidos();
                usu.setIdPed(rs.getInt("id_ped"));
                usu.setIdVen(rs.getInt("id_ven"));
                usu.setFechaEntrega(rs.getDate("fecha_entrega"));
                usu.setEstado(rs.getString("estado"));
                usu.setObservacionesPedido(rs.getString("observaciones"));
                usu.setNombreCliente(rs.getString("Cliente"));
               

                lstUsu.add(usu);
            }  
        } catch (SQLException e) {
        }
    }
    
    

    @Override
    public boolean next() throws JRException {
        indice++;
        return indice < lstUsu.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        String nomcampo = jrf.getName();
        
        switch (nomcampo) {
            case "id_ped":
                valor = lstUsu.get(indice).getIdPed();
                break;
            case "id_ven":
                valor = lstUsu.get(indice).getIdVen();
                break;
            case "fecha_entrega":
                valor = lstUsu.get(indice).getFechaEntrega();
                break;
            case "estado":
                valor = lstUsu.get(indice).getEstado();
                break;
            case "observaciones":
                valor = lstUsu.get(indice).getObservacionesPedido();
                break;
            case "Cliente":
                valor = lstUsu.get(indice).getIdCliente();
                break;
           
        }
        
        return valor;
    }
}
