/*package models;

import config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class detalle_ventaDao {

    Connection con;  // Variable para la conexión activa con la base de datos
    PreparedStatement ps;  // Variable para la sentencia preparada SQL
    ResultSet rs;  // Variable para almacenar los resultados de una consulta
    conexion cn = new conexion();  // Instancia para obtener conexión a la base de datos

    produccionDao pDao = new produccionDao();  // Instancia de DAO de producción (relacionado con detalle_venta)

    // Método para listar todos los detalles de venta
    public List<detalle_venta> listar() {
        List<detalle_venta> lista = new ArrayList();  // Lista donde guardaremos los detalles
        String sql = "SELECT * FROM detalle_venta order by id_ven"; // Consulta SQL para obtener datos

        try {
            this.con = this.cn.conexion();           // Abrir conexión a BD
            this.ps = this.con.prepareStatement(sql); // Preparar sentencia con la consulta
            this.rs = this.ps.executeQuery();        // Ejecutar consulta y guardar resultado

            while (this.rs.next()) {                   // Recorrer todos los resultados
                detalle_venta i = new detalle_venta(); // Crear nuevo objeto detalle_venta
                i.setId_detalle(this.rs.getInt("id_detalle")); // Setear id_detalle desde BD
                i.setId_ven(this.rs.getInt("id_ven"));          // Setear id_ven desde BD
                i.setId_proc(this.rs.getInt("id_proc"));        // Setear id_proc desde BD
                i.setCantidad(this.rs.getInt("cantidad"));      // Setear cantidad desde BD

                produccion p = pDao.obtenerPorId(i.getId_proc());

                if (p != null) {
                    i.setNombreProducto(p.getTipo());
                } else {
                     i.setNombreProducto("DESCONOCIDO");
                }

                lista.add(i);  // Agregar objeto a la lista
            }
        } catch (SQLException var4) {
            System.out.println("Error al listar detalle_venta: " + var4.getMessage());  // Mostrar error si falla la consulta
        }

        return lista;  // Devolver la lista con los detalles
    }

    // Método para agregar un nuevo detalle_venta
    public boolean agregar(detalle_venta i) {
        String sql = "INSERT INTO detalle_venta ( id_ven, id_proc, cantidad) VALUES ( ?, ?, ?)"; // SQL para insertar datos

        try {
            this.con = this.cn.conexion();  // Abrir conexión
            this.ps = this.con.prepareStatement(sql);  // Preparar sentencia
            this.ps.setInt(1, i.getId_ven());  // Setear id_ven en la sentencia
            this.ps.setInt(2, i.getId_proc());  // Setear id_proc en la sentencia
            this.ps.setInt(3, i.getCantidad());  // Setear cantidad en la sentencia
            this.ps.executeUpdate();  // Ejecutar inserción
            return true;  // Retornar éxito
        } catch (SQLException var4) {
            System.out.println("Error al agregar detalle_venta: " + var4.getMessage());  // Mostrar error si falla inserción
            return false;  // Retornar fallo
        }
    }

    // Método para obtener un detalle_venta por su id_detalle
    public detalle_venta obtenerPorId(int id) {
        String sql = "SELECT * FROM detalle_venta WHERE id_detalle = ?";  // SQL para buscar por id_detalle
        detalle_venta i = null;  // Inicializar variable resultado

        try {
            this.con = this.cn.conexion();  // Abrir conexión
            this.ps = this.con.prepareStatement(sql);  // Preparar sentencia
            this.ps.setInt(1, id);  // Setear parámetro id_detalle
            this.rs = this.ps.executeQuery();  // Ejecutar consulta
            if (this.rs.next()) {  // Si hay resultado
                i = new detalle_venta();  // Crear objeto detalle_venta
                i.setId_detalle(this.rs.getInt("id_detalle"));  // Setear id_detalle
                i.setId_ven(this.rs.getInt("id_ven"));  // Setear id_ven
                i.setId_proc(this.rs.getInt("id_proc"));  // Setear id_proc
                i.setCantidad(this.rs.getInt("cantidad"));  // Setear cantidad
            }
        } catch (SQLException var5) {
            System.out.println("Error al obtener detalle_venta: " + var5.getMessage());  // Mostrar error si falla consulta
        }

        return i;  // Retornar detalle encontrado o null
    }

    // Método para actualizar un detalle_venta existente
    public boolean actualizar(detalle_venta i) {
        String sql = "UPDATE detalle_venta SET id_ven = ?, id_proc = ?, cantidad = ? WHERE id_detalle = ?";  // SQL para actualizar

        try {
            this.con = this.cn.conexion();  // Abrir conexión
            this.ps = this.con.prepareStatement(sql);  // Preparar sentencia
            this.ps.setInt(1, i.getId_ven());  // Setear nuevo id_ven
            this.ps.setInt(2, i.getId_proc());  // Setear nuevo id_proc
            this.ps.setInt(3, i.getCantidad());  // Setear nueva cantidad
            this.ps.setInt(4, i.getId_detalle());  // Setear id_detalle a actualizar
            this.ps.executeUpdate();  // Ejecutar actualización
            return true;  // Retornar éxito
        } catch (SQLException var4) {
            System.out.println("Error al actualizar detalle_venta: " + var4.getMessage());  // Mostrar error si falla
            return false;  // Retornar fallo
        }
    }

    // Método para eliminar un detalle_venta por id_detalle
    public boolean eliminar(int id) {
        String sql = "DELETE FROM detalle_venta WHERE id_detalle = ?";  // SQL para eliminar registro

        try {
            this.con = this.cn.conexion();  // Abrir conexión
            this.ps = this.con.prepareStatement(sql);  // Preparar sentencia
            this.ps.setInt(1, id);  // Setear id_detalle a eliminar
            int filasAfectadas = this.ps.executeUpdate();  // Ejecutar eliminación y obtener filas afectadas
            if (filasAfectadas > 0) {  // Si borró alguna fila
                return true;  // Retornar éxito
            } else {
                System.out.println("No se encontró detalle_venta con id: " + id);  // Mensaje si no existe registro
                return false;  // Retornar fallo
            }
        } catch (SQLException var4) {
            System.out.println("Error al eliminar detalle_venta: " + var4.getMessage());  // Mostrar error si falla
            return false;  // Retornar fallo
        }
    }
}
*/