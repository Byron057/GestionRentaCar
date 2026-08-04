/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.usuariosDAO;
import Models.usuarios;

public class loginController {

    usuariosDAO dao = new usuariosDAO();

    public boolean iniciarSesion(String usuario, String contraseña){

        usuarios u = new usuarios(usuario, contraseña);

        return dao.login(u);

    }

}
    

