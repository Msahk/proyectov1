package models;

public class detalle_produccion {
    private int id_detpro;
    private int id_proc;
    private int id_sal;

    public detalle_produccion() {
    }

    public detalle_produccion(int id_detpro, int id_proc, int id_sal) {
        this.id_detpro = id_detpro;
        this.id_proc = id_proc;
        this.id_sal = id_sal;
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

    public int getId_sal() {
        return id_sal;
    }

    public void setId_sal(int id_sal) {
        this.id_sal = id_sal;
    }

    @Override
    public String toString() {
        return "detalle_produccion{" +
                "id_detpro=" + id_detpro +
                ", id_proc=" + id_proc +
                ", id_sal=" + id_sal +
                '}';
    }
}
