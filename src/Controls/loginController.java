package Controls;

import DAO.usuariosDAO;
import Models.usuarios;
import javax.swing.JOptionPane; // Importante para las alertas

public class loginController {

    private usuariosDAO dao = new usuariosDAO();

    public boolean iniciarSesion(String usuario, String contraseña){

        // 1. Validación de campos vacíos
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

        // 2. Creamos el modelo y consultamos al DAO
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