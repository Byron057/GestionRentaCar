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

    private alquileresDAO dao;
    private RentasPanel vista;

    public alquileresController(RentasPanel vista) {
        this.vista = vista;
        this.dao = new alquileresDAO();
    }

    public void listar() {

        vista.tableClientes.limpiarTabla();

        for (Object[] fila : dao.listarAlquileres()) {
            vista.tableClientes.agregarFila(fila);
        }
    }

    public boolean eliminar(int id) {
        return dao.eliminarAlquiler(id);
    }

    public boolean insertar(alquileres a) {

        boolean resultado = dao.insertarAlquiler(a);

        if (resultado) {
            listar();
        }

        return resultado;
    }

    public boolean actualizar(alquileres a) {

        boolean resultado = dao.actualizarAlquiler(a);

        if (resultado) {
            listar();
        }

        return resultado;
    }
}