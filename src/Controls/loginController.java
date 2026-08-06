package Controls;

import DAO.usuariosDAO;
import Models.usuarios;
import javax.swing.JOptionPane; 
public class loginController {

    private usuariosDAO dao = new usuariosDAO();

    public boolean iniciarSesion(String usuario, String contraseña){

        if(usuario == null || usuario.trim().isEmpty() || 
           contraseña == null || contraseña.trim().isEmpty()){
            JOptionPane.showMessageDialog(
                null, 
                "Por favor, ingrese el usuario y la contraseña.", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
      
        usuarios u = new usuarios(usuario.trim(), contraseña.trim());
        boolean accesoConcedido = dao.login(u);

        if(accesoConcedido){
            JOptionPane.showMessageDialog(
                null, 
                "¡Bienvenido al sistema!", 
                "Acceso Correcto", 
                JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } else {
            JOptionPane.showMessageDialog(
                null, 
                "Usuario o contraseña incorrectos.", 
                "Error de Autenticación", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }
}