package DAO;

import Conexion.conexion;
import Models.alquileres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dashboardDao {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private int contarPorEstado(String tabla, String columnaEstado, String estadoBuscado) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + columnaEstado + " = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, estadoBuscado);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al contar registros en " + tabla + ": " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
        return total;
    }

    public int contarVehiculosActivos() {
        return contarPorEstado("vehiculos", "estado", "Activo");
    }

    public int contarRentasFinalizadas() {
        return contarPorEstado("alquileres", "estado", "Finalizado");
    }

    public int contarClientesActivos() {
        return contarPorEstado("clientes", "estado", "Activo");
    }

    public String obtenerNombreUsuario() {
        String nombre = "";
        String sql = "SELECT nombre FROM usuario LIMIT 1";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
        return nombre;
    }

    public List<alquileres> listarAlquileresActivos() {
        List<alquileres> lista = new ArrayList<>();
        String sql = "SELECT "
                + "a.id_alquiler,"
                + "a.fk_id_cliente,"
                + "CONCAT(c.nombre,' ',c.apellido) AS cliente,"
                + "a.fk_id_vehiculo,"
                + "v.placa,"
                + "a.fecha_alquiler,"
                + "a.total,"
                + "a.dias,"
                + "a.estado "
                + "FROM alquileres a "
                + "INNER JOIN clientes c "
                + "ON a.fk_id_cliente=c.id_cliente "
                + "INNER JOIN vehiculos v "
                + "ON a.fk_id_vehiculo=v.id_vehiculo "
                + "WHERE a.estado = 'Activo' "
                + "ORDER BY a.id_alquiler DESC";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                alquileres a = new alquileres();

                a.setIdAlquiler(rs.getInt("id_alquiler"));
                a.setFkIdCliente(rs.getInt("fk_id_cliente"));
                a.setFkIdVehiculo(rs.getInt("fk_id_vehiculo"));
                a.setFechaAlquiler(rs.getString("fecha_alquiler"));
                a.setTotal(rs.getDouble("total"));
                a.setDias(rs.getInt("dias"));
                a.setEstado(rs.getString("estado"));

                a.setNombreCliente(rs.getString("cliente"));
                a.setPlaca(rs.getString("placa"));

                lista.add(a);
            }
        } catch (Exception e) {
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
        return lista;
    }
}