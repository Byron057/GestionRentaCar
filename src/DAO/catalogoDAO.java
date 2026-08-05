/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import Conexion.conexion;
import Models.tipos;
import java.util.ArrayList;
import java.util.List;
import Models.colores;
import Models.marcas;
import Models.modelos;

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
                lista.add(t);
            }
            
        }catch(Exception e){
            System.out.println("error"+e.toString());
        }finally {
            try{
                if(rs != null)rs.close();
                if(ps != null)ps.close();
                if(con != null)con.close();
                
            }catch(Exception e){     
            }
        } 
        return lista;
    }
    
    public boolean guardarTipos(tipos t){
        String sql = "INSERT INTO tipos(tipo,estado) VALUES(?,?)";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getEstado());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {

            } 
        }
    }
    
    public boolean editarTipos(tipos t){
        String sql = "UPDATE tipos SET tipo=?, estado=? WHERE id_tipo=?";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getEstado());
            ps.setInt(3, t.getId());
            
            ps.executeUpdate();
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();

            } catch (Exception e) {

            }
        }   
    
    }
    
    public boolean eliminarTipos(int id){
        String sql = "DELETE FROM tipos WHERE id_tipo=?";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
            
        }catch(Exception e ){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    
    
    
    // COLORES
    
    public List<colores> listarColores(){
        List<colores> lista = new ArrayList<>();
        String sql = "SELECT * FROM colores ";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                colores c = new colores();
                c.setId_color(rs.getInt("id_color"));
                c.setColor(rs.getString("color"));
                c.setEstado(rs.getString("estado"));
                lista.add(c);
            }
            
        }catch(Exception e){
            
        }finally {
            try{
                if(rs != null)rs.close();
                if(ps != null)ps.close();
                if(con != null)con.close();
                
            }catch(Exception e){     
            }
        } 
        return lista;
    }
   
   public boolean guardarColores(colores c){
        String sql = "INSERT INTO colores(color,estado) VALUES(?,?)";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, c.getColor());
            ps.setString(2, c.getEstado());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {

            } 
        }
    }
    
    public boolean editarColores(colores c){
        String sql = "UPDATE colores SET color=?, estado=? WHERE id_color=?";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, c.getColor());
            ps.setString(2, c.getEstado());
            ps.setInt(3, c.getId());
            
            ps.executeUpdate();
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();

            } catch (Exception e) {

            }
        }   
    
    }
    
    public boolean eliminarColores(int id){
        String sql = "DELETE FROM colores WHERE id_color=?";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
            
        }catch(Exception e ){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                return false;
            }
        }
    }
    public List<marcas> listarMarcas() {
        List<marcas> lista = new ArrayList<>();
        String sql = "SELECT * FROM marcas_vehiculos ";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                marcas m = new marcas();
                m.setId(rs.getInt("id_marca"));
                m.setMarca(rs.getString("marca"));
                m.setEstado(rs.getString("estado"));
                lista.add(m);
            }
            
        }catch(Exception e){
            
        }finally {
            try{
                if(rs != null)rs.close();
                if(ps != null)ps.close();
                if(con != null)con.close();
                
            }catch(Exception e){     
            }
        } 
        return lista;
    }
   
   public boolean guardarMarcas(marcas m){
        String sql = "INSERT INTO marcas_vehiculos(marca,estado) VALUES(?,?)";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getMarca());
            ps.setString(2, m.getEstado());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {

            } 
        }
    }
    
    public boolean editarMarcas(marcas m){
        String sql = "UPDATE marcas_vehiculos SET marca=?, estado=? WHERE id_marca=?";
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getMarca());
            ps.setString(2, m.getEstado());
            ps.setInt(3, m.getId());
            
            ps.executeUpdate();
            return true;
            
        }catch(Exception e){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();

            } catch (Exception e) {

            }
        }    
    
    }
    
    public boolean eliminarMarcas(int id){
        String sql = "DELETE FROM marcas_vehiculos WHERE id_marca=?";
        
        try{
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
            
        }catch(Exception e ){
            return false;
        }finally {

            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    public List<modelos> listarModelosConMarca() {
        List<modelos> lista = new ArrayList<>();
        String sql = "SELECT mo.id_modelo, mo.fk_id_marca, ma.marca, mo.modelo, mo.estado " +
                     "FROM modelos mo " +
                     "INNER JOIN marcas_vehiculos ma ON mo.fk_id_marca = ma.id_marca";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                modelos m = new modelos();
                m.setId(rs.getInt("id_modelo"));
                m.setFk_id_marca(rs.getInt("fk_id_marca"));
                m.setMarca(rs.getString("marca")); 
                m.setModelo(rs.getString("modelo"));
                m.setEstado(rs.getString("estado"));
                lista.add(m);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
        return lista;
    }
    public List<marcas> listarMarcasActivas() {
        List<marcas> lista = new ArrayList<>();
        String sql = "SELECT id_marca, marca, estado FROM marcas_vehiculos WHERE estado = 'Activo'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                marcas m = new marcas();
                m.setId(rs.getInt("id_marca"));
                m.setMarca(rs.getString("marca"));
                m.setEstado(rs.getString("estado"));
                lista.add(m);
            }
        } catch (Exception e) {
            System.out.println("Error al listar marcas activas con ID: " + e.toString());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
        return lista;
    }
    public boolean guardarModelo(modelos m) {
        String sql = "INSERT INTO modelos(fk_id_marca, modelo, estado) VALUES(?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, m.getFk_id_marca());
            ps.setString(2, m.getModelo());
            ps.setString(3, m.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
    }

    public boolean editarModelo(modelos m) {
        String sql = "UPDATE modelos SET fk_id_marca=?, modelo=?, estado=? WHERE id_modelo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, m.getFk_id_marca());
            ps.setString(2, m.getModelo());
            ps.setString(3, m.getEstado());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
    }

    public boolean eliminarModelo(int id) {
        String sql = "DELETE FROM modelos WHERE id_modelo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
    }
    
    
}
