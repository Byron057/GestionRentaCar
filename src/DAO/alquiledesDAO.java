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

        } catch (Exception e) {

            System.out.println("Error: " + e.toString());
            return false;

        }
    }
    
}
