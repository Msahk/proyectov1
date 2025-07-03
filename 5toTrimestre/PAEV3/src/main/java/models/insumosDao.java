// Source code is decompiled from a .class file using FernFlower decompiler.
package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class insumosDao {
   Connection con;
   PreparedStatement ps;
   ResultSet rs;
   conexion cn = new conexion();

   public insumosDao() {
   }

   public List<insumos> listar() {
      List<insumos> lista = new ArrayList();
      String sql = "SELECT * FROM insumos";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.rs = this.ps.executeQuery();

         while(this.rs.next()) {
            insumos i = new insumos();
            i.setId_ins(this.rs.getInt("id_ins"));
            i.setNombre(this.rs.getString("nombre"));
            i.setCantidad(this.rs.getDouble("cantidad"));
            i.setUnidad_medida(this.rs.getString("unidad_medida"));
            i.setStock_min(this.rs.getDouble("stock_min"));
            lista.add(i);
         }
      } catch (SQLException var4) {
         System.out.println("Error al listar insumos: " + var4.getMessage());
      }

      return lista;
   }

   public boolean agregar(insumos i) {
      String sql = "INSERT INTO insumos (nombre, cantidad, unidad_medida, stock_min) VALUES (?, ?, ?, ?)";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setString(1, i.getNombre());
         this.ps.setDouble(2, i.getCantidad());
         this.ps.setString(3, i.getUnidad_medida());
         this.ps.setDouble(4, i.getStock_min());
         this.ps.executeUpdate();
         return true;
      } catch (SQLException var4) {
         System.out.println("Error al agregar insumo: " + var4.getMessage());
         return false;
      }
   }

   public insumos obtenerPorId(int id) {
      String sql = "SELECT * FROM insumos WHERE id_ins = ?";
      insumos i = null;

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         this.rs = this.ps.executeQuery();
         if (this.rs.next()) {
            i = new insumos();
            i.setId_ins(this.rs.getInt("id_ins"));
            i.setNombre(this.rs.getString("nombre"));
            i.setCantidad(this.rs.getDouble("cantidad"));
            i.setUnidad_medida(this.rs.getString("unidad_medida"));
            i.setStock_min(this.rs.getDouble("stock_min"));
         }
      } catch (SQLException var5) {
         System.out.println("Error al obtener insumo: " + var5.getMessage());
      }

      return i;
   }

   public boolean actualizar(insumos i) {
      String sql = "UPDATE insumos SET nombre = ?, cantidad = ?, unidad_medida = ?, stock_min = ? WHERE id_ins = ?";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setString(1, i.getNombre());
         this.ps.setDouble(2, i.getCantidad());
         this.ps.setString(3, i.getUnidad_medida());
         this.ps.setDouble(4, i.getStock_min());
         this.ps.setInt(5, i.getId_ins());
         this.ps.executeUpdate();
         return true;
      } catch (SQLException var4) {
         System.out.println("Error al actualizar insumo: " + var4.getMessage());
         return false;
      }
   }

   public boolean eliminar(int id) {
      String sql = "DELETE FROM insumos WHERE id_ins = ?";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         int filasAfectadas = this.ps.executeUpdate();
         if (filasAfectadas > 0) {
            return true;
         } else {
            System.out.println("No se encontró insumo con id: " + id);
            return false;
         }
      } catch (SQLException var4) {
         System.out.println("Error al eliminar insumo: " + var4.getMessage());
         return false;
      }
   }
}
