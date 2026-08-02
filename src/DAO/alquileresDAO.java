/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.conexion;
import Models.alquileres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class alquileresDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // INSERTAR
    public boolean insertarAlquiler(alquileres a) {

        String sql = "INSERT INTO alquileres (fk_id_cliente, fk_id_vehiculo, fecha_alquiler, total, estado, dias) VALUES (?,?,?,?,?,?)";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, a.getFkIdCliente());
            ps.setInt(2, a.getFkIdVehiculo());
            ps.setString(3, a.getFechaAlquiler());
            ps.setDouble(4, a.getTotal());
            ps.setString(5, a.getEstado());
            ps.setInt(6, a.getDias());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error: " + e.toString());
            return false;

        }
    }
     // LISTAR
    public List<Object[]> listarAlquileres() {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT a.id_alquiler, " +
        "c.nombre_cli, " +
        "v.nombre_veh, " +
        "a.fecha_alquiler, " +
        "a.total, " +
        "a.dias, " +
        "a.estado " +
        "FROM alquileres a " +
        "INNER JOIN clientes c ON a.fk_id_cliente = c.id_cliente " +
        "INNER JOIN vehiculos v ON a.fk_id_vehiculo = v.id_vehiculo";

    try {

        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {

            lista.add(new Object[]{
                rs.getInt("id_alquiler"),
                rs.getString("nombre_cli"),
                rs.getString("nombre_veh"),
                rs.getString("fecha_alquiler"),
                rs.getDouble("total"),
                rs.getInt("dias"),
                rs.getString("estado")
            });

        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.toString());
    }

    return lista;
}
     // ACTUALIZAR
    public boolean actualizarAlquiler(alquileres a) {

        String sql = "UPDATE alquileres SET fk_id_cliente=?, fk_id_vehiculo=?, fecha_alquiler=?, total=?, estado=?, dias=? WHERE id_alquiler=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, a.getFkIdCliente());
            ps.setInt(2, a.getFkIdVehiculo());
            ps.setString(3, a.getFechaAlquiler());
            ps.setDouble(4, a.getTotal());
            ps.setString(5, a.getEstado());
            ps.setInt(6, a.getDias());
            ps.setInt(7, a.getIdAlquiler());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error: " + e.toString());
            return false;

        }
    }
    // ELIMINAR
    public boolean eliminarAlquiler(int id) {

        String sql = "DELETE FROM alquileres WHERE id_alquiler=?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error: " + e.toString());
            return false;

        }

    }
}
