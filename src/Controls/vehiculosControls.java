/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.vehiculosDAO;
import Views.panels.VehiculosForm;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTable;
        
//imortar models

import Models.vehiculos;
import java.util.Set;

import Models.marcas;
import Models.modelos;
import Models.tipos;
import Models.colores;
import Views.panels.VehiculosPanel;

public class vehiculosControls {
    private VehiculosForm vista;
    private VehiculosPanel vista2;
    private vehiculosDAO dao;
    
    private List<vehiculos> listaVehiculos;

    public vehiculosControls(VehiculosForm vista , VehiculosPanel vista2){
        this.vista=vista;
        dao = new vehiculosDAO();
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
    
    //borrar en caso de error; 
    if (vista.cbxModeloVehiculo.getSelectedItem() != null) {
    modelos modeloSeleccionado = (modelos) vista.cbxModeloVehiculo.getSelectedItem();
    v.setIdModelo(modeloSeleccionado.getId());
} else {
    JOptionPane.showMessageDialog(vista, "Por favor, seleccione un modelo de vehículo.");
    return; // Detiene la ejecución para evitar el error
}
    //v.setIdModelo(((modelos) vista.cbxModeloVehiculo.getSelectedItem()).getId());
    
    //borrar igual:; 
    if (vista.cbxTipoVehiculo.getSelectedItem() != null) {
    tipos tipoSeleccionado = (tipos) vista.cbxTipoVehiculo.getSelectedItem();
    v.setIdTipo(tipoSeleccionado.getId());
} else {
    JOptionPane.showMessageDialog(vista, "Por favor, seleccione un tipo de vehículo.");
    return; // Detiene el proceso para evitar que la aplicación falle
}
    //v.setIdTipo(((tipos) vista.cbxTipoVehiculo.getSelectedItem()).getId());
    //borrar igual; 
    if (vista.cbxColorVehiculo.getSelectedItem() != null) {
    colores colorSeleccionado = (colores) vista.cbxColorVehiculo.getSelectedItem();
    v.setIdColor(colorSeleccionado.getId());
} else {
    JOptionPane.showMessageDialog(vista, "Por favor, seleccione un color.");
    return;
}
 //   v.setIdColor(((colores) vista.cbxColorVehiculo.getSelectedItem()).getId());
 //borra igual
 
 if (vista.cbxEstadoCliente.getSelectedItem() != null) {
    // Tu código actual de la línea 106 que usa el item seleccionado
    String valor = vista.cbxEstadoCliente.getSelectedItem().toString();
    v.setEstado(valor);
    
} else {
    JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.");
    return;
}
    //v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
    
    if(dao.insertarVehiculo(v)){
        JOptionPane.showMessageDialog(null,"Vehiculo Registrado");
        mostrarTabla();
    }else{
        JOptionPane.showMessageDialog(null,"Error");
    }
    
}

public void mostrarTabla(){

    if (vista2 != null && vista2.tableVehiculos != null) {
            vista2.tableVehiculos.limpiarTabla();
            
             // Llenamos la lista global
            listaVehiculos = dao.listarVehiculo();
            
        for(vehiculos x:dao.listarVehiculo()){
            vista2.tableVehiculos.agregarFila(new Object[]{
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
            }


/*
public void editar(){
            try{

            int fila = vista2.tableVehiculos.getSelectedRow();


            if(fila == -1){
                JOptionPane.showMessageDialog(null,
                        "Seleccione un Vehiculo");
                return;

            }

            
            //obtener id del vehiculo seleccionado
            int idVehiculoSeleccionado = listaVehiculos.get(fila).getIdVehiculo();
            vehiculos v = new vehiculos();
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
    
    int fila = vista2.tableVehiculos.getSelectedRow();
    
    if(fila== -1){
        JOptionPane.showMessageDialog(null, "Seleccion un vehiculo");
        return;    
    }
    
    // ID directamente de la lista global
    int id = listaVehiculos.get(fila).getIdVehiculo();
    
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
 */   
}
    
    

