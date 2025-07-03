package models;

import java.sql.Date;

public class credito {

    private String idCredito;
    private Date fec_cred;
    private Date fec_venc;
    private double monto_total;
    private String estado; 
    private String docCliente;

    public credito() {
    }

    public credito(String idCredito, Date fec_cred, Date fec_venc, double monto_total, String estado, String docCliente) {
        this.idCredito = idCredito;
        this.fec_cred = fec_cred;
        this.fec_venc = fec_venc;
        this.monto_total = monto_total;
        this.estado = estado;
        this.docCliente = docCliente;
    }


    public String getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(String idCredito) {
        this.idCredito = idCredito;
    }

    public Date getFec_cred() {
        return fec_cred;
    }

    public void setFec_cred(Date fec_cred) {
        this.fec_cred = fec_cred;
    }

    public Date getFec_venc() {
        return fec_venc;
    }

    public void setFec_venc(Date fec_venc) {
        this.fec_venc = fec_venc;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDocCliente() {
        return docCliente;
    }

    public void setDocCliente(String docCliente) {
        this.docCliente = docCliente;
    }

    
    @Override
    public String toString() {
        return "Credito{" +
                "idCredito='" + idCredito + '\'' +
                ", fec_cred=" + fec_cred +
                ", fec_venc=" + fec_venc +
                ", monto_total=" + monto_total +
                ", estado='" + estado + '\'' +
                ", docCliente='" + docCliente + '\'' +
                '}';
    }
}
