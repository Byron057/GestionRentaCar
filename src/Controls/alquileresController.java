/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.alquileresDAO;
import Models.alquileres;
import Views.panels.RentasPanel;
import javax.swing.JOptionPane;
public class alquileresController {
    private RentasPanel vista;
    private alquileresDAO dao;

    public alquileresController(RentasPanel vista){

        this.vista=vista;
        dao=new alquileresDAO();

    }
    //listar
    public void listar(){

    vista.tableClientes.limpiarTabla();

    for(alquileres a : dao.listarAlquileres()){

        vista.tableClientes.agregarFila(new Object[]{

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
