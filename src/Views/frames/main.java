
package Views.frames;
import Conexion.conexion;
import java.sql.Connection;

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

    

