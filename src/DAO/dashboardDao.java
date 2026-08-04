package DAO;

import Conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dashboardDao {

    // Método genérico para contar por estado
    private int contarPorEstado(String tabla, String columnaEstado, String estadoBuscado) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + columnaEstado + " = ?";

        conexion c = new conexion();
        try (Connection con = c.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estadoBuscado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al contar registros en " + tabla + ": " + e.getMessage());
        }
        return total;
    }

    // Contar Vehículos Activos
    public int contarVehiculosActivos() {
        return contarPorEstado("vehiculos", "estado", "Activo");
    }

    // Contar Rentas / Alquileres Finalizados
    public int contarRentasFinalizadas() {
        return contarPorEstado("alquileres", "estado", "Finalizado");
    }
    
    // Contar Clientes Activos
    public int contarClientesActivos() {
        return contarPorEstado("clientes", "estado", "Activo");
    }

    // Obtener el nombre del único usuario registrado en la tabla
    public String obtenerNombreUsuario() {
        String nombre = "";
        // Cambiamos "usuario" por "nombre" para que traiga "Byron Velastegui"
        String sql = "SELECT nombre FROM usuario LIMIT 1"; 

        conexion c = new conexion();
        try (Connection con = c.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                nombre = rs.getString("nombre"); // Obtenemos el campo de la columna 'nombre'
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
        }
        return nombre;
    }
}