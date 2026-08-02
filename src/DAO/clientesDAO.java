/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Models.clientes;
import Conexion.conexion;

public class clientesDAO {

    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // ---------- INSERTAR ----------
    public boolean insertarCliente(clientes cli) {
        String sql = "INSERT INTO clientes (cedula, nombre, apellido, telefono, direccion, estado) VALUES(?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getCedula());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getApellido());
            ps.setString(4, cli.getTelefono());
            ps.setString(5, cli.getDireccion());
            ps.setString(6, cli.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar: " + e.toString());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    // ---------- LISTAR TODOS ----------
    public List<clientes> listarClientes() {
        List<clientes> listaCli = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id_cliente DESC"; // opcional: ordenar
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                clientes cl = new clientes();
                cl.setId_cliente(rs.getInt("id_cliente"));
                cl.setCedula(rs.getString("cedula"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setEstado(rs.getString("estado"));
                listaCli.add(cl);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.toString());
        } finally {
            cerrarRecursos();
        }
        return listaCli;
    }

    // ---------- EDITAR ----------
    public boolean editarCliente(clientes cli) {
        String sql = "UPDATE clientes SET cedula=?, nombre=?, apellido=?, telefono=?, direccion=?, estado=? WHERE id_cliente=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getCedula());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getApellido());
            ps.setString(4, cli.getTelefono());
            ps.setString(5, cli.getDireccion());
            ps.setString(6, cli.getEstado());
            ps.setInt(7, cli.getId_cliente());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar: " + e.toString());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    // ---------- CAMBIAR ESTADO (eliminación lógica) ----------
    public boolean cambiarEstadoCliente(int idCliente, String nuevoEstado) {
        String sql = "UPDATE clientes SET estado = ? WHERE id_cliente = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idCliente);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al cambiar estado: " + e.toString());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    // ---------- VALIDACIONES DE CÉDULA DUPLICADA ----------
    public boolean existeCedula(String cedula) {
        boolean existe = false;
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar cédula: " + e.toString());
        } finally {
            cerrarRecursos();
        }
        return existe;
    }

    public boolean existeCedulaAlEditar(String cedula, int idCliente) {
        boolean existe = false;
        String sql = "SELECT * FROM clientes WHERE cedula = ? AND id_cliente != ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setInt(2, idCliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar cédula (editar): " + e.toString());
        } finally {
            cerrarRecursos();
        }
        return existe;
    }

    // ---------- MÉTODO PARA CERRAR RECURSOS (evita repetir código) ----------
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.toString());
        }
    }
}