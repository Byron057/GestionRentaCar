/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Views.frames.Login;
import Conexion.conexion;
import java.sql.Connection;
/**
 *
 * @author PC
 */
public class main {
     public static void main(String[] args) {
         
        conexion conn = new conexion();
        Connection con = conn.getConnection();
        if(con !=null){
            Login login = new Login();
            login.setLocationRelativeTo(null); 
            login.setVisible(true);
            System.out.println("conexion Exitosa");
        }else{
            System.out.println("Error en la conexion");
        }
        
    }
}
