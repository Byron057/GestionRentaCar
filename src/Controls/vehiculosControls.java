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
        this.vista2=vista2;
        dao = new vehiculosDAO();
        mostrarTabla();
    }
    
    //cargar en los comboBox 
    public void cargarMarcas(JComboBox cbxMarcaVehiculo){
        cbxMarcaVehiculo.removeAllItems();
        for (marcas m : dao.listarMarcasActivas()){
            cbxMarcaVehiculo.addItem(m);
        }
    mostrarTabla();
     }
    
    public void cargarModelos(JComboBox cbxModeloVehiculo){
        cbxModeloVehiculo.removeAllItems();
        for (modelos mod : dao.listarModelosActivos()){
            cbxModeloVehiculo.addItem(mod);
        }
    mostrarTabla();
     }
    
    public void cargarTipos(JComboBox cbxTipoVehiculo){
        cbxTipoVehiculo.removeAllItems();
        for (tipos t : dao.listarTiposActivos()){
            cbxTipoVehiculo.addItem(t);
        }
    mostrarTabla();
     }
    
    public void cargarColores(JComboBox cbxColorVehiculo){
        cbxColorVehiculo.removeAllItems();
        for (colores c : dao.listarColoresActivos()){
            cbxColorVehiculo.addItem(c);
        }
    mostrarTabla();
     }

public void insertar(){
    vehiculos v = new vehiculos();
    String placa = vista.flPlaca.getText().trim().toUpperCase();
    // 3. Validamos el formato (
    if (!placa.matches("^[A-Z]{3}-\\d{3,4}$")) {
        JOptionPane.showMessageDialog(
            vista, 
            "Formato de placa inválido.\nDebe ser similar a: ABC-1234", 
            "Error de Formato", 
            JOptionPane.ERROR_MESSAGE
        );
        return; // Detiene el guardado
    }
    
    v.setPlaca(placa);
   
    
    // CASGING (Conversión de tipos): Extrae el objeto seleccionado del ComboBox y hace un casting forzado 
    // a la clase 'marcas' para poder invocar su método getId() y obtener la llave foránea numérica.
    v.setIdMarca(((marcas) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
    
    //Revisa que el combobox no este vacio
    //evitando una excepción de tipo NullPointerException si el usuario no seleccionó nada.
    if (vista.cbxModeloVehiculo.getSelectedItem() != null) {
    modelos modeloSeleccionado = (modelos) vista.cbxModeloVehiculo.getSelectedItem();
    v.setIdModelo(modeloSeleccionado.getId());
    } else {
    JOptionPane.showMessageDialog(vista, "Por favor, seleccione un modelo de vehículo.");
    return; // Detiene la ejecución para evitar el error
    }
   
        if (vista.cbxTipoVehiculo.getSelectedItem() != null) {
        tipos tipoSeleccionado = (tipos) vista.cbxTipoVehiculo.getSelectedItem();
        v.setIdTipo(tipoSeleccionado.getId());
        } else {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione un tipo de vehículo.");
        return;// Detiene el proceso para evitar que la aplicación falle
        } 

            if (vista.cbxColorVehiculo.getSelectedItem() != null) {
            colores colorSeleccionado = (colores) vista.cbxColorVehiculo.getSelectedItem();
            v.setIdColor(colorSeleccionado.getId());
            } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione un color.");
            return;
            }

 
                if (vista.cbxEstadoCliente.getSelectedItem() != null) {
                   String valor = vista.cbxEstadoCliente.getSelectedItem().toString();
                   v.setEstado(valor);    
                   } else {
                   JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.");
                   return;
                   }
                
  
if(dao.insertarVehiculo(v)){
     JOptionPane.showMessageDialog(null,"Vehiculo Registrado");
      mostrarTabla();
}else{
      JOptionPane.showMessageDialog(null,"Error");
}
    
}

public void mostrarTabla(){
//Validación de que la vista y la tabla no estén vacías o nulas
    if (vista2 != null && vista2.tableVehiculos != null) {
            vista2.tableVehiculos.limpiarTabla();
            
             // Llenamos la lista global
            listaVehiculos = dao.listarVehiculo();
            
        for(vehiculos x:dao.listarVehiculo()){
            vista2.tableVehiculos.agregarFila(new Object[]{
               //RELLENO MULTITABLA: Pinta los datos obtenidos con los JOINs
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



public void editar(int idVehiculos){

            vehiculos v = new vehiculos();
            v.setIdVehiculo(idVehiculos);
            v.setPlaca(vista.flPlaca.getText());
            // CASTING DE LAS RELACIONES MULTITABLA: 
            // Como el ComboBox guarda objetos completos (marcas, modelos, tipos, colores), 
            // aquí obligamos a Java a convertir (hacer casting) el ítem seleccionado 
            // a su clase respectiva para poder extraer únicamente su ID numérico y actualizarlo.
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

       
            

}



public void eliminar(int idVehiculo){
// CONDICIONAL DE RESPUESTA:
            if(dao.eliminarVehiculo(idVehiculo)){
                
                JOptionPane.showMessageDialog(null,
                        "Vehiculo eliminado");
                
                  mostrarTabla();

            }else{
                JOptionPane.showMessageDialog(null,
                        "No se pudo eliminar");

            }

        }  
}
   

    
    

