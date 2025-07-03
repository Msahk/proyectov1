package models;

import java.sql.Date;

public class pago {

    private String idPagos;
    private int numero_cuota;
    private double monto_pagado;
    private Date fecha_pago;
    private String tipo_pago;
    private double valor_pagado;
    private String observaciones;
    private String idCredito;

    public pago() {
    }

    public pago(String idPagos, int numero_cuota, double monto_pagado, Date fecha_pago,
                 String tipo_pago, double valor_pagado, String observaciones, String idCredito) {
        this.idPagos = idPagos;
        this.numero_cuota = numero_cuota;
        this.monto_pagado = monto_pagado;
        this.fecha_pago = fecha_pago;
        this.tipo_pago = tipo_pago;
        this.valor_pagado = valor_pagado;
        this.observaciones = observaciones;
        this.idCredito = idCredito;
    }


    public String getIdPagos() {
        return idPagos;
    }

    public void setIdPagos(String idPagos) {
        this.idPagos = idPagos;
    }

    public int getNumero_cuota() {
        return numero_cuota;
    }

    public void setNumero_cuota(int numero_cuota) {
        this.numero_cuota = numero_cuota;
    }

    public double getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(double monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public double getValor_pagado() {
        return valor_pagado;
    }

    public void setValor_pagado(double valor_pagado) {
        this.valor_pagado = valor_pagado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(String idCredito) {
        this.idCredito = idCredito;
    }

    @Override
    public String toString() {
        return "Pagos{" +
                "idPagos='" + idPagos + '\'' +
                ", numero_cuota=" + numero_cuota +
                ", monto_pagado=" + monto_pagado +
                ", fecha_pago=" + fecha_pago +
                ", tipo_pago='" + tipo_pago + '\'' +
                ", valor_pagado=" + valor_pagado +
                ", observaciones='" + observaciones + '\'' +
                ", idCredito='" + idCredito + '\'' +
                '}';
    }
}
