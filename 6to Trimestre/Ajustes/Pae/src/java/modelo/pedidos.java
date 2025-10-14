package modelo;

import java.io.Serializable;
import java.util.Date;

public class pedidos implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPed;
    private int idVen;
    private int idCliente;
    private String nombreCliente;
    private Date fechaEntrega;
    private String estado;
    private String observacionesPedido;

    public int getIdPed() { return idPed; }
    public void setIdPed(int idPed) { this.idPed = idPed; }

    public int getIdVen() { return idVen; }
    public void setIdVen(int idVen) { this.idVen = idVen; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public Date getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(Date fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacionesPedido() { return observacionesPedido; }
    public void setObservacionesPedido(String observacionesPedido) { this.observacionesPedido = observacionesPedido; }
}