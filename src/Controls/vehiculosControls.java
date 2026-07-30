/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.VehiculosDAO;
import Views.panels.VehiculosForm;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
//imortar models
import Models.vehiculos;
//import Models.marca;
//import Models.modelo;
//import Models.tipo;
//import Models.color;

public class vehiculosControls {
    private VehiculosForm vista;
    private VehiculosDAO dao;

    public vehiculosControls(VehiculosForm vista){
        this.vista=vista;
        dao = new VehiculosDAO();
        mostrarTabla();
    }
    
    //cargar en los comboBox 
    public cargarMarca(JCombo box cbxMarcaVehiculo){
        
    }
    
    
}
