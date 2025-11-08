
package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.usuarios;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class usuariosDataSource implements JRDataSource{
    private List<usuarios> lstUsu;
    private int indice;

    public usuariosDataSource() {
        lstUsu = new ArrayList<>();
        indice = -1;
        try {
          String sql = "SELECT * FROM usuarios";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()) {
                usuarios usu = new usuarios();
                usu.setDocumento(rs.getInt("documento"));
                usu.setNombres(rs.getString("nombres"));
                usu.setApellidos(rs.getString("apellidos"));
                usu.setTelefono(rs.getLong("telefono"));
                usu.setDireccion(rs.getString("direccion"));
                usu.setCorreo(rs.getString("correo"));
                usu.setRol(rs.getString("rol"));
                usu.setEstado(rs.getString("estado"));

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
            case "documento":
                valor = lstUsu.get(indice).getDocumento();
                break;
            case "nombres":
                valor = lstUsu.get(indice).getNombres();
                break;
            case "apellidos":
                valor = lstUsu.get(indice).getApellidos();
                break;
            case "telefono":
                valor = lstUsu.get(indice).getTelefono();
                break;
            case "direccion":
                valor = lstUsu.get(indice).getDireccion();
                break;
            case "correo":
                valor = lstUsu.get(indice).getCorreo();
                break;
            case "rol":
                valor = lstUsu.get(indice).getRol();
                break;
            case "estado":
                valor = lstUsu.get(indice).getEstado();
                break;
        }
        
        return valor;
    }
}
