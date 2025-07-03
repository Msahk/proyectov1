// Source code is decompiled from a .class file using FernFlower decompiler.
package models;

import java.sql.Date;

public class produccion {
   private int id_proc;
   private Date fecha_produccion;
   private int total_emp;
   private String tipo;
   private int cantidad;
   private Integer id_prot;
   private Integer id_res;
   private String estado;

   public produccion() {
   }

   public produccion(int id_proc, Date fecha_produccion, int total_emp, String tipo, int cantidad, Integer id_prot, Integer id_res, String estado) {
      this.id_proc = id_proc;
      this.fecha_produccion = fecha_produccion;
      this.total_emp = total_emp;
      this.tipo = tipo;
      this.cantidad = cantidad;
      this.id_prot = id_prot;
      this.id_res = id_res;
      this.estado = estado;
   }

   public int getId_proc() {
      return this.id_proc;
   }

   public void setId_proc(int id_proc) {
      this.id_proc = id_proc;
   }

   public Date getFecha_produccion() {
      return this.fecha_produccion;
   }

   public void setFecha_produccion(Date fecha_produccion) {
      this.fecha_produccion = fecha_produccion;
   }

   public int getTotal_emp() {
      return this.total_emp;
   }

   public void setTotal_emp(int total_emp) {
      this.total_emp = total_emp;
   }

   public String getTipo() {
      return this.tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public int getCantidad() {
      return this.cantidad;
   }

   public void setCantidad(int cantidad) {
      this.cantidad = cantidad;
   }

   public Integer getId_prot() {
      return this.id_prot;
   }

   public void setId_prot(Integer id_prot) {
      this.id_prot = id_prot;
   }

   public Integer getId_res() {
      return this.id_res;
   }

   public void setId_res(Integer id_res) {
      this.id_res = id_res;
   }

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String toString() {
      return "produccion{id_proc=" + this.id_proc + ", fecha_produccion=" + this.fecha_produccion + ", total_emp=" + this.total_emp + ", tipo='" + this.tipo + '\'' + ", cantidad=" + this.cantidad + ", id_prot=" + this.id_prot + ", id_res=" + this.id_res + ", estado='" + this.estado + '\'' + '}';
   }
}
