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
import java.util.Set;

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
    public void cargarMarcas(JComboBox cbxMarcaVehiculo){
        cbxMarcaVehiculo.removeAllItems();
        for (marcas m : dao.listarMarcas()){
            cbxMarcaVehiculo.addItem(m);
        }
    mostrarTabla();
     }
    
    public void cargarModelos(JComboBox cbxModeloVehiculo){
        cbxModeloVehiculo.removeAllItems();
        for (modelos mod : dao.listarModelos()){
            cbxModeloVehiculo.addItem(mod);
        }
    mostrarTabla();
     }
    
    public void cargarTipos(JComboBox cbxTipoVehiculo){
        cbxTipoVehiculo.removeAllItems();
        for (Tipos t : dao.listarTipos()){
            cbxTipoVehiculo.addItem(t);
        }
    mostrarTabla();
     }
    
    public void cargarColores(JComboBox cbxColorVehiculo){
        cbxColorVehiculo.removeAllItems();
        for (colores c : dao.listarColores()){
            cbxColorVehiculo.addItem(c);
        }
    mostrarTabla();
     }

public void insertar(){
    vehiculos v = new vehiculos();
    v.setPlaca(vista.flPlaca.getText());
    v.setIdMarca(((marca) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
    v.setIdModelo(((modelo) vista.cbxModeloVehiculo.getSelectedItem()).getId());
    v.setIdTipo(((tipo) vista.cbxTipoVehiculo.getSelectedItem()).getId());
    v.setIdColor(((color) vista.cbxColorVehiculo.getSelectedItem()).getId());
    v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
    
    if(dao.insertarVehiculo(v)){
        JOptionPane.showMessageDialog(null,"Vehiculo Registrado");
    }else{
        JOptionPane.showMessageDialog(null,"Error");
    }
    
}

public void mostrarTabla(){
        DefaultTableModel model = (DefaultTableModel) vista.tableVehiculos.getModel();
        model.setRowCount(0);
        for(vehiculos x:dao.listarVehiculo()){
            model.addRow(new Object[]{
              x.getIdVehiculo(),
              x.getPlaca(),
              x.getnombreMarca(),
              x.getnombreModelo(),
              x.getnombreTipo(),
              x.getnombreColor(),
              x.getEstado()
              
              
            });
        }
        
    }
    
}
    
    

