package models;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class VentaClientePedido {
    private String nombreCliente;
    private String telefonoCliente;
    private String correoCliente;
    private String tipoVenta;            // 'directa' o 'pedido'
    private LocalDateTime fechaVenta;
    private int idUsuario;
    private BigDecimal totalVenta;
    private String estadoVenta;          // 'Procesando' o 'Completada'
    private String obsVenta;
    private LocalDateTime fechaEntregaPedido;
    private String obsPedido;

    // Constructor vacío
    public VentaClientePedido() {
    }

    // Constructor completo
    public VentaClientePedido(String nombreCliente, String telefonoCliente, String correoCliente, String tipoVenta, LocalDateTime fechaVenta, int idUsuario, BigDecimal totalVenta, String estadoVenta, String obsVenta, LocalDateTime fechaEntregaPedido, String obsPedido) {    
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
        this.tipoVenta = tipoVenta;
        this.fechaVenta = fechaVenta;
        this.idUsuario = idUsuario;
        this.totalVenta = totalVenta;
        this.estadoVenta = estadoVenta;
        this.obsVenta = obsVenta;
        this.fechaEntregaPedido = fechaEntregaPedido;
        this.obsPedido = obsPedido;
    }

    // Getters y setters
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }
    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }
    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }
    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }
    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }
    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }
    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public String getObsVenta() {
        return obsVenta;
    }
    public void setObsVenta(String obsVenta) {
        this.obsVenta = obsVenta;
    }

    public LocalDateTime getFechaEntregaPedido() {
        return fechaEntregaPedido;
    }
    public void setFechaEntregaPedido(LocalDateTime fechaEntregaPedido) {
        this.fechaEntregaPedido = fechaEntregaPedido;
    }

    public String getObsPedido() {
        return obsPedido;
    }
    public void setObsPedido(String obsPedido) {
        this.obsPedido = obsPedido;
    }
}
