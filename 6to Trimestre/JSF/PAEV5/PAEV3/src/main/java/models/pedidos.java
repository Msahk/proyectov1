package models;

import java.io.Serializable;
import java.util.Date;

public class pedidos implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPed;
    private int idVen;
    private Date fechaEntrega;
    private String estado;
    private String observacionesPedido;

    public int getIdPed() { return idPed; }
    public void setIdPed(int idPed) { this.idPed = idPed; }

    public int getIdVen() { return idVen; }
    public void setIdVen(int idVen) { this.idVen = idVen; }

    public Date getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(Date fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacionesPedido() { return observacionesPedido; }
    public void setObservacionesPedido(String observacionesPedido) { this.observacionesPedido = observacionesPedido; }
}