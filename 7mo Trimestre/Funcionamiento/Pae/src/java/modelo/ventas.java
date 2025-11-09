package modelo;

import java.io.Serializable;
import java.util.Date;

public class ventas implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idVen;
    private String tipo;
    private int idCliente;
    private String nombreCliente;
    private int idUsuario;       // Usuario que registra la venta
    private String nombreUsuario;
    private int idAsignado;      // Usuario al que se le asigna la venta
    private String nombreAsignado;
    private String apellidoAsignado;
    private double total;
    private String estado;
    private Date fecha;
    private String observaciones;
    private String apellidoUsuario;
    private int numeroProduccion;
    
    

    public int getNumeroProduccion() {
        return numeroProduccion;
    }

    public void setNumeroProduccion(int numeroProduccion) {
        this.numeroProduccion = numeroProduccion;
    }

    public int getIdVen() {
        return idVen;
    }

    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdAsignado() {
        return idAsignado;
    }

    public void setIdAsignado(int idAsignado) {
        this.idAsignado = idAsignado;
    }



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getApellidoUsuario() {
    return apellidoUsuario;
}

public void setApellidoUsuario(String apellidoUsuario) {
    this.apellidoUsuario = apellidoUsuario;
}

public String getNombreAsignado() {
    return nombreAsignado;
}

public void setNombreAsignado(String nombreAsignado) {
    this.nombreAsignado = nombreAsignado;
}

public String getApellidoAsignado() {
    return apellidoAsignado;
}

public void setApellidoAsignado(String apellidoAsignado) {
    this.apellidoAsignado = apellidoAsignado;
}

}
