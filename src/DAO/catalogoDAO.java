/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Conexion.conexion;
import Models.tipos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class catalogoDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List<tipos> listarTipos(){
        List<tipos> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos ";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                tipos t = new tipos();
                t.setId(rs.getInt("id_tipo"));
                t.setTipo(rs.getString("tipo"));
                t.setEstado(rs.getString("estado"));
            }
            
        }catch(Exception e){
            System.out.println(e);
        }   
    return lista;
    }
    
}
