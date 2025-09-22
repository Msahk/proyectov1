package dao;

import modelo.clientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clientesDao {
    private final String URL = "jdbc:mysql://localhost:3306/pae?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Listar todos
    public List<clientes> listar() {
        List<clientes> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes c = new clientes();
                c.setId_Cliente(rs.getInt("id_Cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Filtrar
    public List<clientes> filtrar(String nombre, String telefono, String correo) {
        List<clientes> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM clientes WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (nombre != null && !nombre.isEmpty()) {
            sql.append("AND nombre LIKE ? ");
            params.add("%" + nombre + "%");
        }
        if (telefono != null && !telefono.isEmpty()) {
            sql.append("AND telefono LIKE ? ");
            params.add("%" + telefono + "%");
        }
        if (correo != null && !correo.isEmpty()) {
            sql.append("AND correo LIKE ? ");
            params.add("%" + correo + "%");
        }
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes c = new clientes();
                    c.setId_Cliente(rs.getInt("id_Cliente"));
                    c.setNombre(rs.getString("nombre"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

  
    public int agregar(clientes c) {
        String sql = "INSERT INTO clientes (nombre, telefono, correo) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    
   public boolean actualizarEnCascada(clientes c) {
    Connection con = null;
    try {
        con = getConnection();
        con.setAutoCommit(false);

        
        String sqlCliente = "UPDATE clientes SET nombre=?, telefono=?, correo=? WHERE id_Cliente=?";
        try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.setInt(4, c.getId_Cliente());
            ps.executeUpdate();
        }

        con.commit();
        return true;
    } catch (SQLException e) {
        if (con != null) try { con.rollback(); } catch (SQLException ignored) {}
        e.printStackTrace();
        return false;
    } finally {
        if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException ignored) {}
    }
}

    
    public boolean eliminarEnCascada(int idCliente) {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            String sqlPedidos = "DELETE FROM pedidos WHERE id_Cliente=?";
            try (PreparedStatement ps = con.prepareStatement(sqlPedidos)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }

            String sqlVentas = "DELETE FROM ventas WHERE id_Cliente=?";
            try (PreparedStatement ps = con.prepareStatement(sqlVentas)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }

            String sqlCliente = "DELETE FROM clientes WHERE id_Cliente=?";
            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) try { con.setAutoCommit(true); con.close(); } catch (SQLException ignored) {}
        }
    }

    
    public boolean actualizar(clientes c) {
        String sql = "UPDATE clientes SET nombre=?, telefono=?, correo=? WHERE id_Cliente=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.setInt(4, c.getId_Cliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_Cliente=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public int obtenerUltimoId() {
        String sql = "SELECT MAX(id_Cliente) as id FROM clientes";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}