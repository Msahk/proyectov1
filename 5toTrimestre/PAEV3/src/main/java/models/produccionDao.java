// Source code is decompiled from a .class file using FernFlower decompiler.
package models;

import config.conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class produccionDao {
   Connection con = null;
   PreparedStatement ps = null;
   ResultSet rs = null;
   conexion cn = new conexion();

   public produccionDao() {
   }

   public List<produccion> listar() {
      List<produccion> lista = new ArrayList();
      String sql = "SELECT * FROM produccion";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.rs = this.ps.executeQuery();

         while(this.rs.next()) {
            produccion p = new produccion();
            p.setId_proc(this.rs.getInt("id_proc"));
            p.setFecha_produccion(this.rs.getDate("fecha_produccion"));
            p.setTotal_emp(this.rs.getInt("total_emp"));
            p.setTipo(this.rs.getString("tipo"));
            p.setCantidad(this.rs.getInt("cantidad"));
            int idProt = this.rs.getInt("id_prot");
            p.setId_prot(this.rs.wasNull() ? null : idProt);
            int idRes = this.rs.getInt("id_res");
            p.setId_res(this.rs.wasNull() ? null : idRes);
            p.setEstado(this.rs.getString("estado"));
            lista.add(p);
         }
      } catch (SQLException var9) {
         System.out.println("Error al listar producciones: " + var9.getMessage());
      } finally {
         this.cerrarRecursos();
      }

      return lista;
   }

   public produccion obtenerPorId(int id) {
      produccion p = null;
      String sql = "SELECT * FROM produccion WHERE id_proc=?";

      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         this.rs = this.ps.executeQuery();
         if (this.rs.next()) {
            p = new produccion();
            p.setId_proc(this.rs.getInt("id_proc"));
            p.setFecha_produccion(this.rs.getDate("fecha_produccion"));
            p.setTotal_emp(this.rs.getInt("total_emp"));
            p.setTipo(this.rs.getString("tipo"));
            p.setCantidad(this.rs.getInt("cantidad"));
            int idProt = this.rs.getInt("id_prot");
            p.setId_prot(this.rs.wasNull() ? null : idProt);
            int idRes = this.rs.getInt("id_res");
            p.setId_res(this.rs.wasNull() ? null : idRes);
            p.setEstado(this.rs.getString("estado"));
         }
      } catch (SQLException var9) {
         System.out.println("Error al obtener producción: " + var9.getMessage());
      } finally {
         this.cerrarRecursos();
      }

      return p;
   }

   public boolean registrarProduccionCompleta(produccion p) {
      if (!this.hayStockSuficiente(p.getTipo(), p.getTotal_emp())) {
         System.out.println("❌ No hay suficiente stock para producir " + p.getTotal_emp() + " empanadas de " + p.getTipo());
         return false;
      } else {
         String insertProduccion = "INSERT INTO produccion (fecha_produccion, total_emp, tipo, cantidad, id_prot, id_res, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
         String insertSalida = "INSERT INTO inv_salidas (id_ins, cantidad, fecha) VALUES (?, ?, ?)";
         String insertDetalle = "INSERT INTO detalle_produccion (id_proc, id_sal) VALUES (?, ?)";
         String updateInsumo = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";
         Map<String, Map<Integer, Double>> recetaEmpanadas = this.getRecetas();
         Map<Integer, Double> receta = (Map)recetaEmpanadas.get(p.getTipo().toLowerCase());

         try {
            this.con = this.cn.conexion();
            this.con.setAutoCommit(false);
            this.ps = this.con.prepareStatement(insertProduccion, 1);
            this.ps.setDate(1, p.getFecha_produccion());
            this.ps.setInt(2, p.getTotal_emp());
            this.ps.setString(3, p.getTipo());
            this.ps.setInt(4, p.getCantidad());
            if (p.getId_prot() == null) {
               this.ps.setNull(5, 4);
            } else {
               this.ps.setInt(5, p.getId_prot());
            }

            if (p.getId_res() == null) {
               this.ps.setNull(6, 4);
            } else {
               this.ps.setInt(6, p.getId_res());
            }

            this.ps.setString(7, p.getEstado());
            this.ps.executeUpdate();
            this.rs = this.ps.getGeneratedKeys();
            int idProduccion = 0;
            if (this.rs.next()) {
               idProduccion = this.rs.getInt(1);
            }

            Iterator var9 = receta.entrySet().iterator();

            while(var9.hasNext()) {
               Map.Entry<Integer, Double> entry = (Map.Entry)var9.next();
               int id_ins = (Integer)entry.getKey();
               double cantidadTotal = (Double)entry.getValue() * (double)p.getTotal_emp();
               this.ps = this.con.prepareStatement(insertSalida, 1);
               this.ps.setInt(1, id_ins);
               this.ps.setDouble(2, cantidadTotal);
               this.ps.setDate(3, new Date(System.currentTimeMillis()));
               this.ps.executeUpdate();
               this.rs = this.ps.getGeneratedKeys();
               int idSalida = 0;
               if (this.rs.next()) {
                  idSalida = this.rs.getInt(1);
               }

               this.ps = this.con.prepareStatement(insertDetalle);
               this.ps.setInt(1, idProduccion);
               this.ps.setInt(2, idSalida);
               this.ps.executeUpdate();
               this.ps = this.con.prepareStatement(updateInsumo);
               this.ps.setDouble(1, cantidadTotal);
               this.ps.setInt(2, id_ins);
               this.ps.executeUpdate();
            }

            this.con.commit();
            System.out.println("✅ Producción registrada con éxito.");
            boolean var27 = true;
            return var27;
         } catch (SQLException var25) {
            try {
               if (this.con != null) {
                  this.con.rollback();
               }
            } catch (SQLException var24) {
               var24.printStackTrace();
            }

            System.out.println("❌ Error al registrar producción completa: " + var25.getMessage());
         } finally {
            try {
               if (this.con != null) {
                  this.con.setAutoCommit(true);
               }

               this.cerrarRecursos();
            } catch (SQLException var23) {
               var23.printStackTrace();
            }

         }

         return false;
      }
   }

   public boolean actualizarProduccionCompleta(produccion p) {
      boolean var3;
      try {
         this.con = this.cn.conexion();
         this.con.setAutoCommit(false);
         produccion prodAntigua = this.obtenerPorId(p.getId_proc());
         if (prodAntigua != null) {
            String sqlIdsSalidas = "SELECT id_sal FROM detalle_produccion WHERE id_proc = ?";
            PreparedStatement psBuscar = this.con.prepareStatement(sqlIdsSalidas);
            psBuscar.setInt(1, p.getId_proc());
            ResultSet rsSalidas = psBuscar.executeQuery();
            List<Integer> idsSalidas = new ArrayList();

            while(rsSalidas.next()) {
               idsSalidas.add(rsSalidas.getInt("id_sal"));
            }

            rsSalidas.close();
            psBuscar.close();
            String deleteDetalle = "DELETE FROM detalle_produccion WHERE id_proc = ?";
            this.ps = this.con.prepareStatement(deleteDetalle);
            this.ps.setInt(1, p.getId_proc());
            this.ps.executeUpdate();
            Iterator var8 = idsSalidas.iterator();

            int cantidadAnterior;
            String tipoAnterior;
            while(var8.hasNext()) {
               cantidadAnterior = (Integer)var8.next();
               tipoAnterior = "DELETE FROM inv_salidas WHERE id_sal = ?";
               this.ps = this.con.prepareStatement(tipoAnterior);
               this.ps.setInt(1, cantidadAnterior);
               this.ps.executeUpdate();
            }

            Map<String, Map<Integer, Double>> recetaEmpanadas = this.getRecetas();
            cantidadAnterior = prodAntigua.getTotal_emp();
            tipoAnterior = prodAntigua.getTipo();
            if (!tipoAnterior.equalsIgnoreCase(p.getTipo()) || cantidadAnterior != p.getTotal_emp()) {
               Map<Integer, Double> recetaAnterior = (Map)recetaEmpanadas.get(tipoAnterior.toLowerCase());
               if (recetaAnterior != null) {
                  Iterator var12 = recetaAnterior.entrySet().iterator();

                  while(var12.hasNext()) {
                     Map.Entry<Integer, Double> entry = (Map.Entry)var12.next();
                     int id_ins = (Integer)entry.getKey();
                     double cantidadADescontar = (Double)entry.getValue() * (double)cantidadAnterior;
                     String updateInsumoSumar = "UPDATE insumos SET cantidad = cantidad + ? WHERE id_ins = ?";
                     this.ps = this.con.prepareStatement(updateInsumoSumar);
                     this.ps.setDouble(1, cantidadADescontar);
                     this.ps.setInt(2, id_ins);
                     this.ps.executeUpdate();
                  }
               }
            }

            if (!this.hayStockSuficiente(p.getTipo(), p.getTotal_emp())) {
               this.con.rollback();
               boolean var39 = false;
               return var39;
            }

            String sqlUpdateProduccion = "UPDATE produccion SET fecha_produccion=?, total_emp=?, tipo=?, cantidad=?, id_prot=?, id_res=?, estado=? WHERE id_proc=?";
            this.ps = this.con.prepareStatement(sqlUpdateProduccion);
            this.ps.setDate(1, p.getFecha_produccion());
            this.ps.setInt(2, p.getTotal_emp());
            this.ps.setString(3, p.getTipo());
            this.ps.setInt(4, p.getCantidad());
            this.ps.setObject(5, p.getId_prot(), 4);
            this.ps.setObject(6, p.getId_res(), 4);
            this.ps.setString(7, p.getEstado());
            this.ps.setInt(8, p.getId_proc());
            this.ps.executeUpdate();
            Map<Integer, Double> recetaNueva = (Map)recetaEmpanadas.get(p.getTipo().toLowerCase());
            String insertSalida = "INSERT INTO inv_salidas (id_ins, cantidad, fecha) VALUES (?, ?, ?)";
            String insertDetalle = "INSERT INTO detalle_produccion (id_proc, id_sal) VALUES (?, ?)";
            String updateInsumo = "UPDATE insumos SET cantidad = cantidad - ? WHERE id_ins = ?";
            Iterator var16 = recetaNueva.entrySet().iterator();

            while(var16.hasNext()) {
               Map.Entry<Integer, Double> entry = (Map.Entry)var16.next();
               int id_ins = (Integer)entry.getKey();
               double cantidadTotal = (Double)entry.getValue() * (double)p.getTotal_emp();
               this.ps = this.con.prepareStatement(insertSalida, 1);
               this.ps.setInt(1, id_ins);
               this.ps.setDouble(2, cantidadTotal);
               this.ps.setDate(3, new Date(System.currentTimeMillis()));
               this.ps.executeUpdate();
               this.rs = this.ps.getGeneratedKeys();
               int idSalida = 0;
               if (this.rs.next()) {
                  idSalida = this.rs.getInt(1);
               }

               this.ps = this.con.prepareStatement(insertDetalle);
               this.ps.setInt(1, p.getId_proc());
               this.ps.setInt(2, idSalida);
               this.ps.executeUpdate();
               this.ps = this.con.prepareStatement(updateInsumo);
               this.ps.setDouble(1, cantidadTotal);
               this.ps.setInt(2, id_ins);
               this.ps.executeUpdate();
            }

            this.con.commit();
            System.out.println("✅ Producción actualizada con éxito.");
            boolean var44 = true;
            return var44;
         }

         System.out.println("Producción no encontrada para actualizar");
         var3 = false;
      } catch (Exception var34) {
         try {
            if (this.con != null) {
               this.con.rollback();
            }
         } catch (Exception var33) {
            var33.printStackTrace();
         }

         System.out.println("❌ Error al actualizar producción completa: " + var34.getMessage());
         var3 = false;
         return var3;
      } finally {
         try {
            if (this.con != null) {
               this.con.setAutoCommit(true);
            }

            this.cerrarRecursos();
         } catch (Exception var32) {
            var32.printStackTrace();
         }

      }

      return var3;
   }

   public boolean eliminar(int id) {
      String sql = "DELETE FROM produccion WHERE id_proc=?";

      boolean var4;
      try {
         this.con = this.cn.conexion();
         this.ps = this.con.prepareStatement(sql);
         this.ps.setInt(1, id);
         boolean var3 = this.ps.executeUpdate() > 0;
         return var3;
      } catch (SQLException var8) {
         System.out.println("Error al eliminar producción: " + var8.getMessage());
         var4 = false;
      } finally {
         this.cerrarRecursos();
      }

      return var4;
   }

   public boolean hayStockSuficiente(String tipo, int cantidadEmpanadas) {
      Map<String, Map<Integer, Double>> recetaEmpanadas = this.getRecetas();
      Map<Integer, Double> receta = (Map)recetaEmpanadas.get(tipo.toLowerCase());
      if (receta == null) {
         System.out.println("No se encontró receta para el tipo: " + tipo);
         return false;
      } else {
         try {
            this.con = this.cn.conexion();
            Iterator var5 = receta.entrySet().iterator();

            int id_ins;
            double cantidadNecesaria;
            double disponible;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               Map.Entry<Integer, Double> entry = (Map.Entry)var5.next();
               id_ins = (Integer)entry.getKey();
               cantidadNecesaria = (Double)entry.getValue() * (double)cantidadEmpanadas;
               String sql = "SELECT cantidad FROM insumos WHERE id_ins = ?";
               this.ps = this.con.prepareStatement(sql);
               this.ps.setInt(1, id_ins);
               this.rs = this.ps.executeQuery();
               if (!this.rs.next()) {
                  System.out.println("❌ Insumo con ID " + id_ins + " no encontrado.");
                  boolean var20 = false;
                  return var20;
               }

               disponible = this.rs.getDouble("cantidad");
            } while(!(disponible < cantidadNecesaria));

            System.out.println("❌ Insuficiente stock para insumo " + id_ins);
            boolean var13 = false;
            return var13;
         } catch (SQLException var17) {
            System.out.println("Error al verificar stock: " + var17.getMessage());
            boolean var6 = false;
            return var6;
         } finally {
            this.cerrarRecursos();
         }
      }
   }

   private Map<String, Map<Integer, Double>> getRecetas() {
      Map<String, Map<Integer, Double>> recetas = new HashMap();
      Map<Integer, Double> carne = new HashMap();
      carne.put(1, 0.1);
      carne.put(2, 0.01);
      carne.put(3, 0.05);
      recetas.put("carne", carne);
      Map<Integer, Double> pollo = new HashMap();
      pollo.put(1, 0.12);
      pollo.put(2, 0.015);
      pollo.put(3, 0.04);
      recetas.put("pollo", pollo);
      return recetas;
   }

   private void cerrarRecursos() {
      try {
         if (this.rs != null) {
            this.rs.close();
         }

         if (this.ps != null) {
            this.ps.close();
         }

         if (this.con != null) {
            this.con.close();
         }
      } catch (SQLException var2) {
         var2.printStackTrace();
      }

   }
}
