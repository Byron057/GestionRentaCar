/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;
import DAO.clientesDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Models.clientes;
import Views.panels.ClientesForm;
import Views.panels.ClientesPanel;

/**
 *
 * @author pc
 */
public class clientesController {
    private ClientesForm vista;
    private ClientesPanel vistaP;
    private clientesDAO dao;
    public clientesController(ClientesForm vista){
        this.vista = vista;
        dao = new clientesDAO();
    }
    public clientesController(ClientesPanel vistaP) {
        this.vistaP = vistaP;
        this.dao = new clientesDAO();}
    public void insertar(){
        clientes cl = new clientes();
        cl.setCedula(vista.flCedula.getText());
        cl.setNombre(vista.flNombreCliente.getText());
        cl.setApellido(vista.flApellidoCliente.getText());
        cl.setDireccion(vista.flDireccionCliente.getText());
        cl.setTelefono(vista.flTelefonoCliente.getText());
        cl.setEstado(vista.cbxsEstadoCliente.getSelectedItem().toString());
        if(dao.insertarCliente(cl)!= null){
            JOptionPane.showMessageDialog(null, "Cliente  registrado");
            listar();
        }
        else{
            JOptionPane.showMessageDialog(null, "error");
        }
        
    }
    public void listar() { 
    System.out.println("listando");

    vistaP.tableClientes.limpiarTabla();

    List<clientes> lista = dao.listarClientes();

    for (clientes cl : lista) {

        vistaP.tableClientes.agregarFila(new Object[]{
            cl.getId_cliente(),
            cl.getCedula(),
            cl.getNombre(),
            cl.getApellido(),
            cl.getTelefono(),
            cl.getDireccion(),
            cl.getEstado()
        });

    }

    System.out.println("listando terminado");
}
    

    
}
   