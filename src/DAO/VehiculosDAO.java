/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Conexion.conexion;
import Models.vehiculos;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VehiculosDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean insertarVehiculo(vehiculos v){
        String sql ="INSERT INTO vehiculos (placa, fk_id_marca, fk_id_modelo, fk_id_tipo, fk_id_color, estado) VALUES(?,?,?,?,?,?)";
        try{
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           
           ps.setString(1, v.getPlaca());
           ps.setInt(2, v.getIdMarca());
           ps.setInt(3, v.getIdModelo());
           ps.setInt(4, v.getIdTipo());
           ps.setInt(5, v.getIdColor());
           ps.setString(6, v.getEstado());
           
           ps.executeUpdate();
           return true;
              
        }catch(Exception e){
            System.out.println("error.VehiculosDAO (insetarVehiculos)"+e.toString());
            return false;   
        }
    }
public List<vehiculos>listarVehiculo(){
    List<vehiculos> lista = new ArrayList<>();
    
    String sql = "SELECT v.id_vehiculo, " +
                "v.placa, " +
                "v.fk_id_marca, " +
                "m.nombre_marca, " +
                "v.fk_id_modelo, " +
                "mod.nombre_modelo, " +
                "v.fk_id_tipo, " +
                "t.nombre_tipo, " +
                "v.fk_id_color, " +
                "c.nombre_color, " +
                "v.estado " +
                "FROM vehiculos v " +
                "INNER JOIN marcas_vehiculos m ON v.fk_id_marca = m.id_marca " +
                "INNER JOIN modelos mod ON v.fk_id_modelo = mod.id_modelo " +
                "INNER JOIN tipos t ON v.fk_id_tipo = t.id_tipo " +
                "INNER JOIN colores c ON v.fk_id_color = c.id_color " +
                "ORDER BY v.id_vehiculo DESC";
    

    try{
     con = cn.getConnection();
     ps = con.prepareStatement(sql);
     rs = ps.executeQuery();
        while(rs.next()){
            vehiculos v = new vehiculos();
            v.setIdVehiculo(rs.getInt("id_vehiculo"));
            v.setPlaca(rs.getString("placa"));
            v.setIdMarca(rs.getInt("fk_id_marca"));
            v.setIdModelo(rs.getInt("fk_id_modelo"));
            v.setIdTipo(rs.getInt("fk_id_tipo"));
            v.setIdColor(rs.getInt("fk_id_color"));
            v.setEstado(rs.getString("estado"));
            //auxiliares
            v.setNombreMarca(rs.getString("nombre_marca"));
            v.setNombreModelo(rs.getString("nombre_modelo"));
            v.setNombreTipo(rs.getString("nombre_tipo"));
            v.setNombreColor(rs.getString("nombre_color"));

            lista.add(v);  
        }   
    }catch(Exception e){
        System.out.println("error.vehiculosDAO (listarVehiculos)"+e.toString());
    }
    return lista;
}

public boolean eliminarVehiculo(int idVehiculo){
    String sql="DELETE FROM vehiculos WHERE id_vehiculo=?";
    try{
        con=cn.getConnection();
        ps=con.prepareStatement(sql);
        ps.setInt(1, idVehiculo);
        
        ps.executeUpdate();
        return true;
        
    }catch(Exception e){
        System.out.println("error.vehiculosDAO (eliminarVehiculos)"+e.toString());
        return false;
    }
}

public boolean editarVehiculo(vehiculos v){
    String sql = "UPDATE vehiculos SET "
            +"placa=?,"
            +"fk_id_marca=?,"
            +"fk_id_modelo=?,"
            +"fk_id_tipo=?,"
            +"fk_id_color=?,"
            +"estado=? "
            +"WHERE id_vehiculo=?";
    try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        
        ps.setString(1,v.getPlaca());
        ps.setInt(2,v.getIdMarca());
        ps.setInt(3,v.getIdModelo());
        ps.setInt(4,v.getIdTipo());
        ps.setInt(5,v.getIdColor());
        ps.setString(6, v.getEstado());
        ps.setInt(7,v.getIdVehiculo());
        
        ps.executeUpdate();
        return true;

    }catch(Exception e){
        System.out.println("error.VehiculosDAO (editarVehiculo)"+e.toString());
        return false;
    }
}

}
