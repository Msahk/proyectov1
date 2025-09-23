
package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.clientes;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ClientesDataSource implements JRDataSource{
    private List<clientes> lstUsu;
    private int indice;

    public ClientesDataSource() {
        lstUsu = new ArrayList<>();
        indice = -1;
        try {
          String sql = "SELECT * FROM clientes";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()) {
                clientes usu = new clientes();
                usu.setId_Cliente(rs.getInt("id_Cliente"));
usu.setNombre(rs.getString("nombre"));
usu.setTelefono(rs.getString("telefono"));
usu.setCorreo(rs.getString("correo"));
                

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
            case "id_Cliente":
    valor = lstUsu.get(indice).getId_Cliente();
    break;
case "nombre":
    valor = lstUsu.get(indice).getNombre();
    break;
case "telefono":
    valor = lstUsu.get(indice).getTelefono();
    break;
case "correo":
    valor = lstUsu.get(indice).getCorreo();
    break;
           
        }
        
        return valor;
    }
}
