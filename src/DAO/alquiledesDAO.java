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

public class alquiledesDAO {
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
    public List<alquileres> listarAlquileres() {

        List<alquileres> lista = new ArrayList<>();

        String sql = "SELECT * FROM alquileres";

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
                a.setEstado(rs.getString("estado"));
                a.setDias(rs.getInt("dias"));

                lista.add(a);

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
