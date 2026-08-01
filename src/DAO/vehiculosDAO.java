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

//modelos axuliares
import Models.colores;
import Models.marcas;
import Models.modelos;
import Models.tipos;

public class vehiculosDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
//metod para registrar un nuevo vehiculo
    public boolean insertarVehiculo(vehiculos v){
        String sql ="INSERT INTO vehiculos (placa, fk_id_marca, fk_id_modelo, fk_id_tipo, fk_id_color, estado) VALUES(?,?,?,?,?,?)";
        try{
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           //Asignando cada valor del objeto Java a los comodines '?' de la consulta en orden numérico
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
    
// Método principal para consultar y listar 
public List<vehiculos>listarVehiculo(){
    // lista vacía donde iremos guardando cada vehículo 
    List<vehiculos> lista = new ArrayList<>();
    
    // CONSULTA MULTITABLA (JOIN): Une la tabla principal de vehículos con tablas secundarias 
    // para transformar los IDs numéricos en nombres de texto legibles usando alias (AS)
    
    String sql = "SELECT v.id_vehiculo, " +
                "v.placa, " +
                "v.fk_id_marca, " +
                "m.marca AS nombre_marca, " + // Alias temporal para leer el nombre real de la marca
                "v.fk_id_modelo, " +
                "mo.modelo as nombre_modelo, " + // Alias temporal para leer el nombre real del modelo
                "v.fk_id_tipo, " +
                "t.tipo As nombre_tipo, " + //// Alias temporal para leer el nombre real del tipo
                "v.fk_id_color, " +
                "c.color as nombre_color, " + //// Alias temporal para leer el nombre real del color
                "v.estado " +
                "FROM vehiculos v " +
                "INNER JOIN marcas_vehiculos m ON v.fk_id_marca = m.id_marca " + //Une la tabla vehículos con marcas usando el ID como enlace
                "INNER JOIN modelos mo ON v.fk_id_modelo = mo.id_modelo " + // Conecta con su tabla correspondiente
                "INNER JOIN tipos t ON v.fk_id_tipo = t.id_tipo " + 
                "INNER JOIN colores c ON v.fk_id_color = c.id_color " + 
                "ORDER BY v.id_vehiculo DESC";//Ordena la lista mostrando los registros más nuevos primero
    

    try{
     // Establecemos la conexión con la base de datos
     con = cn.getConnection();
     // Preparamos la consulta SQL para ser ejecutada
     ps = con.prepareStatement(sql);
     // Ejecutamos la consulta y guardamos el resultado en el ResultSet (rs)
     rs = ps.executeQuery();
        while(rs.next()){
            // Instanciamos un nuevo objeto vehículo por cada fila encontrada
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

//Metodos para listar marcas,modelos,tipos y colores activo
public List<marcas> listarMarcasActivas() {
    List<marcas> lista = new ArrayList<>();
    String sql = "SELECT * FROM marcas_vehiculos WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            marcas m = new marcas();
            m.setId(rs.getInt("id_marca"));
            m.setNombreMarca(rs.getString("marca"));
            lista.add(m);
        }
    } catch (Exception e) {
        System.out.println("error.vehiculosDAO (listarMarcas) " + e.toString());
    }
    return lista;
}

public List<modelos> listarModelosActivos() {
    List<modelos> lista = new ArrayList<>();
    String sql = "SELECT * FROM modelos WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            modelos mod = new modelos();
            mod.setId(rs.getInt("id_modelo"));
            mod.setNombreModelo(rs.getString("modelo"));
            
            lista.add(mod);
        }
    } catch (Exception e) {
        System.out.println("error.vehiculosDAO (listarModelos) " + e.toString());
    }
    return lista;
}

public List<tipos> listarTiposActivos() {
    List<tipos> lista = new ArrayList<>();
    String sql = "SELECT * FROM tipos WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            tipos t = new tipos();
            t.setId(rs.getInt("id_tipo"));
            t.setNombreTipo(rs.getString("tipo"));
            
            lista.add(t);
        }
    } catch (Exception e) {
        System.out.println("error.vehiculosDAO (listarTipos) " + e.toString());
    }
    return lista;
}

public List<colores> listarColoresActivos() {
    List<colores> lista = new ArrayList<>();
    String sql = "SELECT * FROM colores WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            colores c = new colores();
            c.setId_color(rs.getInt("id_color"));
            c.setNombreColor(rs.getString("color"));
            
            lista.add(c);
        }
    } catch (Exception e) {
        System.out.println("error.vehiculosDAO (listarColores) " + e.toString());
    }
    return lista;
}

//metodo para eliminar un vehiculo usando su id
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
