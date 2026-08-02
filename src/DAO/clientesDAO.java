/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Models.clientes;
import Conexion.conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author pc
 */
public class clientesDAO {
        conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Boolean insertarCliente(clientes cli){
        String sql ="INSERT INTO clientes (cedula, nombre, apellido, telefono, direccion, estado) VALUES(?,?,?,?,?,?)";
        try{
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
        }catch(Exception e){
            System.out.println("error "+ e.toString());
            return false;
        }
    }
        public List<clientes> listarClientes() {
        List<clientes> listaCli = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
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
                
                System.out.println("Cantidad: " + listaCli.size());
            }
        } catch (Exception e) {
            System.out.println("error " + e.toString());
        }
        return listaCli;
    }
    public Boolean editarCliente(clientes cli) {

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

        System.out.println("error " + e.toString());
        return false;

    }

}    
        


}
