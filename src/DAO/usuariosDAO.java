/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Models.usuarios;

public class usuariosDAO {

    public boolean login(usuarios usuario){

        String sql = "SELECT * FROM usuario WHERE usuario=? AND contraseña=?";

        try{
            conexion c = new conexion();
            Connection con = c.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContraseña());

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){

            System.out.println(e.getMessage());

        }

        return false;
    }

}

