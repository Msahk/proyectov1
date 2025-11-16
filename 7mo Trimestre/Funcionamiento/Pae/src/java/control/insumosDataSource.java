package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.insumos;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class insumosDataSource implements JRDataSource {
    private List<insumos> listaInsumos;
    private int indice;

    public insumosDataSource() {
        listaInsumos = new ArrayList<>();
        indice = -1;
        try {
            String sql = "SELECT * FROM insumos";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                insumos ins = new insumos();
                ins.setId_ins(rs.getInt("id_ins"));
                ins.setNombre(rs.getString("nombre"));
                ins.setUnidad_medida(rs.getString("unidad_medida"));
                ins.setStock_min(rs.getBigDecimal("stock_min").doubleValue());

                listaInsumos.add(ins);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean next() throws JRException {
        indice++;
        return indice < listaInsumos.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        String nombreCampo = jrf.getName();

        switch (nombreCampo) {
            case "id_ins":
                valor = listaInsumos.get(indice).getId_ins();
                break;
            case "nombre":
                valor = listaInsumos.get(indice).getNombre();
                break;
            case "unidad_medida":
                valor = listaInsumos.get(indice).getUnidad_medida();
                break;
            case "stock_min":
                valor = listaInsumos.get(indice).getStock_min();
                break;
        }
        return valor;
    }
}
