// Source code is decompiled from a .class file using FernFlower decompiler.
package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class detalle_ventaDao {
   Connection con;
   PreparedStatement ps;
   ResultSet rs;
   conexion cn = new conexion();

   produccionDao pDao = new produccionDao();
   public List<detalle_venta> listar() {
      List<detalle_venta> lista = new ArrayList();
      String sql = "SELECT * FROM detalle_venta";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.rs = this.ps.executeQuery();

         while(this.rs.next()) {
            detalle_venta i = new detalle_venta();
            i.setId_detalle(this.rs.getInt("id_detalle"));
            i.setId_ven(this.rs.getInt("id_ven"));
            i.setId_proc(this.rs.getInt("id_proc"));
            i.setCantidad(this.rs.getInt("cantidad"));
            
            produccion p = pDao.obtenerPorId(i.getId_proc());
            
            if (p != null) {
                i.setNombreProducto(p.getTipo());
            } else {
                i.setNombreProducto("DESCONOCIDO");
            }
            
            lista.add(i);
         }
      } catch (SQLException var4) {
         System.out.println("Error al listar detalle_venta: " + var4.getMessage());
      }

      return lista;
   }

   public boolean agregar(detalle_venta i) {
      String sql = "INSERT INTO detalle_venta ( id_ven, id_proc, cantidad) VALUES ( ?, ?, ?)";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, i.getId_ven());
         this.ps.setInt(2, i.getId_proc());
         this.ps.setInt(3, i.getCantidad());
         this.ps.executeUpdate();
         return true;
      } catch (SQLException var4) {
         System.out.println("Error al agregar detalle_venta: " + var4.getMessage());
         return false;
      }
   }

   public detalle_venta obtenerPorId(int id) {
      String sql = "SELECT * FROM detalle_venta WHERE id_detalle = ?";
      detalle_venta i = null;

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         this.rs = this.ps.executeQuery();
         if (this.rs.next()) {
            i = new detalle_venta();
            i.setId_detalle(this.rs.getInt("id_detalle"));
            i.setId_ven(this.rs.getInt("id_ven"));
            i.setId_proc(this.rs.getInt("id_proc"));
            i.setCantidad(this.rs.getInt("cantidad"));
         }
      } catch (SQLException var5) {
         System.out.println("Error al obtener detalle_venta: " + var5.getMessage());
      }

      return i;
   }

   public boolean actualizar(detalle_venta i) {
      String sql = "UPDATE detalle_venta SET id_ven = ?, id_proc = ?, cantidad = ? WHERE id_detalle = ?";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, i.getId_ven());
         this.ps.setInt(2, i.getId_proc());
         this.ps.setInt(3, i.getCantidad());
         this.ps.setInt(4, i.getId_detalle());
         this.ps.executeUpdate();
         return true;
      } catch (SQLException var4) {
         System.out.println("Error al actualizar detalle_venta: " + var4.getMessage());
         return false;
      }
   }

   public boolean eliminar(int id) {
      String sql = "DELETE FROM detalle_venta WHERE id_detalle = ?";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         int filasAfectadas = this.ps.executeUpdate();
         if (filasAfectadas > 0) {
            return true;
         } else {
            System.out.println("No se encontró detalle_venta con id: " + id);
            return false;
         }
      } catch (SQLException var4) {
         System.out.println("Error al eliminar detalle_venta: " + var4.getMessage());
         return false;
      }
   }
}
