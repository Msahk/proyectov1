// Source code is decompiled from a .class file using FernFlower decompiler.
package models;

public class insumos {
   private int id_ins;
   private String nombre;
   private double cantidad;
   private String unidad_medida;
   private double stock_min;

   public insumos() {
   }

   public insumos(int id_ins, String nombre, double cantidad, String unidad_medida, double stock_min) {
      this.id_ins = id_ins;
      this.nombre = nombre;
      this.cantidad = cantidad;
      this.unidad_medida = unidad_medida;
      this.stock_min = stock_min;
   }

   public int getId_ins() {
      return this.id_ins;
   }

   public void setId_ins(int id_ins) {
      this.id_ins = id_ins;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public double getCantidad() {
      return this.cantidad;
   }

   public void setCantidad(double cantidad) {
      this.cantidad = cantidad;
   }

   public String getUnidad_medida() {
      return this.unidad_medida;
   }

   public void setUnidad_medida(String unidad_medida) {
      this.unidad_medida = unidad_medida;
   }

   public double getStock_min() {
      return this.stock_min;
   }

   public void setStock_min(double stock_min) {
      this.stock_min = stock_min;
   }

   public String toString() {
      return "insumos{id_ins=" + this.id_ins + ", nombre='" + this.nombre + '\'' + ", cantidad=" + this.cantidad + ", unidad_medida='" + this.unidad_medida + '\'' + ", stock_min=" + this.stock_min + '}';
   }
}
