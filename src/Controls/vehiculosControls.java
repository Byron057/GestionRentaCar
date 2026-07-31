/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.DAO;
import Views.panels.VehiculosForm;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTable;


import javax.swing.table.DefaultTableModel;
//imortar models

import Models.vehiculos;
import java.util.Set;

import Models.marcas;
import Models.modelos;
import Models.tipos;
import Models.colores;

public class vehiculosControls {
    private VehiculosForm vista;
    private VehiculosForm vista2;
    private DAO dao;

    public vehiculosControls(VehiculosForm vista){
        this.vista=vista;
        dao = new DAO();
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
        for (tipos t : dao.listarTipos()){
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
    v.setIdMarca(((marcas) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
    v.setIdModelo(((modelos) vista.cbxModeloVehiculo.getSelectedItem()).getId());
    v.setIdTipo(((tipos) vista.cbxTipoVehiculo.getSelectedItem()).getId());
    v.setIdColor(((colores) vista.cbxColorVehiculo.getSelectedItem()).getId());
    v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
    
    if(dao.insertarVehiculo(v)){
        JOptionPane.showMessageDialog(null,"Vehiculo Registrado");
        mostrarTabla();
    }else{
        JOptionPane.showMessageDialog(null,"Error");
    }
    
}

public void mostrarTabla(){
        
    
        DefaultTableModel model = (DefaultTableModel) vista2.tableVehiculos.getModel();
        model.setRowCount(0);
        for(vehiculos x:dao.listarVehiculo()){
            model.addRow(new Object[]{
              x.getIdVehiculo(),
              x.getPlaca(),
              x.getNombreMarca(),
              x.getNombreModelo(),
              x.getNombreTipo(),
              x.getNombreColor(),
              x.getEstado()
              
              
            });
        }
        
    }

public void editar(){
            try{

            int fila =
                vista.tblVehiculos.getSelectedRow();


            if(fila == -1){
                JOptionPane.showMessageDialog(null,
                        "Seleccione un Vehiculo");
                return;

            }

            vehiculos v = new vehiculos();
            //obtener id del vehiculo seleccionado
            v.setIdVehiculo(Integer.parseInt(vista.tableVehiculos.getValueAt(fila, 0).toString()));
            //id de las relaciones multitabla
            v.setIdMarca(((marcas) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
            v.setIdModelo(((modelos) vista.cbxModeloVehiculo.getSelectedItem()).getId());
            v.setIdTipo(((tipos) vista.cbxTipoVehiculo.getSelectedItem()).getId());
            v.setIdColor(((colores) vista.cbxColorVehiculo.getSelectedItem()).getId());

            v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
            if(dao.editarVehiculo(v)){
                JOptionPane.showMessageDialog(null, "Vehículo actualizado exitosamente");
                mostrarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el vehículo");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error.vehiculosControls (editar) " + e.getMessage());
        }
            

}

public void eliminar(){
    
    int fila = vista.tableVehiculos.getSelectedRow();
    
    if(fila== -1){
        JOptionPane.showMessageDialog(null, "Seleccion un vehiculo");
        return;    
    }
    
    int id= Integer.parseInt(vista.tableVehiculos.getValueAt(fila,0).toString());
    
        int confirmar =
                JOptionPane.showConfirmDialog(null,
                "¿Eliminar vehiculo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
                if(confirmar == JOptionPane.YES_OPTION){


            if(dao.eliminarVehiculo(id)){
                
                JOptionPane.showMessageDialog(null,
                        "Vehiculo eliminado");
                
                mostrarTabla();

            }else{
                JOptionPane.showMessageDialog(null,
                        "No se pudo eliminar");

            }

        }  
}
    
}
    
    

