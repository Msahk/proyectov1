package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ventas {
    
    public int id_ven;
    public String Tipo;
    public String fecha;
    public int id_usu;
    public int id_Cliente;
    public BigDecimal total;
    public String estado;
    public String observaciones;
    public String vendedorNombre;
    public String clienteNombre;

    public ventas(int id_ven, String Tipo, String fecha, int id_usu, int id_Cliente, BigDecimal total, String estado, String observaciones, String vendedorNombre, String clienteNombre) {
        this.id_ven = id_ven;
        this.Tipo = Tipo;
        this.fecha = fecha;
        this.id_usu = id_usu;
        this.id_Cliente = id_Cliente;
        this.total = total;
        this.estado = estado;
        this.observaciones = observaciones;
        this.vendedorNombre = vendedorNombre;
        this.clienteNombre = clienteNombre;
    }

    

    public ventas() {
        
    }

    public int getId_ven() {
        return id_ven;
    }

    public void setId_ven(int id_ven) {
        this.id_ven = id_ven;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getVendedorNombre() {
        return vendedorNombre;
    }

    public void setVendedorNombre(String vendedorNombre) {
        this.vendedorNombre = vendedorNombre;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String ClienteNombre) {
        this.clienteNombre = ClienteNombre;
    }
        
    
    
}
