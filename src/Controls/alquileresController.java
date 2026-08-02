/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.alquileresDAO;
import Models.alquileres;
import Views.panels.RentasPanel;
import javax.swing.JOptionPane;
import Views.panels.RentasForm;

public class alquileresController {
    private RentasForm vista;
    private alquileresDAO dao;
    private RentasPanel vistap;
     public alquileresController(RentasPanel vistaP){

        this.vistap=vistaP;
        dao=new alquileresDAO();

    }
    public alquileresController(RentasForm vista){

        this.vista=vista;
        dao=new alquileresDAO();

    }
    
    public void insertar() {
       
        alquileres a = new alquileres();
        a.setFkIdCliente(Integer.parseInt(vista.cbxAlquilerCliente.getSelectedItem().toString()));
        a.setFkIdVehiculo(Integer.parseInt(vista.cbxAlquilerVehiculo.getSelectedItem().toString()));
        a.setFechaAlquiler(vista.flFecha.getText());
        a.setTotal(Double.parseDouble(vista.flTotal.getText()));
        a.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
        a.setDias(Integer.parseInt(vista.flDias.getText()));
        
        if (dao.insertarAlquiler(a)) {
            JOptionPane.showMessageDialog(null, "Alquiler registrado");
            listar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar");
        }
    }


    //listar
    public void listar(){

        vistap.tableClientes.limpiarTabla();

        for (alquileres a : dao.listarAlquileres()) {

            vistap.tableClientes.agregarFila(new Object[]{
                a.getIdAlquiler(),
                a.getFkIdCliente(),
                a.getFkIdVehiculo(),
                a.getFechaAlquiler(),
                a.getTotal(),
                a.getDias(),
                a.getEstado()
            });
        }
    }
    
}
