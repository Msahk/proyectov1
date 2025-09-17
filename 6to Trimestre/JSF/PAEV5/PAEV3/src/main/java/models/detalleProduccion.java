package models;

public class detalleProduccion {
    private int id_detpro;
    private int id_proc;
    private int id_ins;

    private String nombreInsumo;
    private double cantidadNecesaria;

    public detalleProduccion() {}

    public detalleProduccion(int id_detpro, int id_proc, int id_ins, double cantidadNecesaria) {
        this.id_detpro = id_detpro;
        this.id_proc = id_proc;
        this.id_ins = id_ins;
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public int getId_detpro() {
        return id_detpro;
    }

    public void setId_detpro(int id_detpro) {
        this.id_detpro = id_detpro;
    }

    public int getId_proc() {
        return id_proc;
    }

    public void setId_proc(int id_proc) {
        this.id_proc = id_proc;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public double getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(double cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    @Override
    public String toString() {
        return "detalleProduccion{" +
                "id_detpro=" + id_detpro +
                ", id_proc=" + id_proc +
                ", id_ins=" + id_ins +
                ", nombreInsumo='" + nombreInsumo + '\'' +
                ", cantidadNecesaria=" + cantidadNecesaria +
                '}';
    }
}
