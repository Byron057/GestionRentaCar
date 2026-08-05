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
    public void cargarMarcas(JComboBox<Object> cbxMarcaVehiculo){
        cbxMarcaVehiculo.removeAllItems();
        for (marcas m : dao.listarMarcasActivas()){
            cbxMarcaVehiculo.addItem(m);
        }

        cbxMarcaVehiculo.setSelectedIndex(-1); 

     }
    
    public void cargarTipos(JComboBox<Object> cbxTipoVehiculo){
        cbxTipoVehiculo.removeAllItems();
        for (tipos t : dao.listarTiposActivos()){
            cbxTipoVehiculo.addItem(t);
        }


        cbxTipoVehiculo.setSelectedIndex(-1); 

     }
    
    public void cargarColores(JComboBox<Object> cbxColorVehiculo){
        cbxColorVehiculo.removeAllItems();
        for (colores c : dao.listarColoresActivos()){
            cbxColorVehiculo.addItem(c);
        }
        cbxColorVehiculo.setSelectedIndex(-1); 
     }
    
    
    //metodo para caragar modelos por marcas 
    public void cargarModeloPorMarcas(JComboBox cbxModeloVehiculo , int idMarca){
        
        // Limpia el combo de modelos antes de cargar los nuevos
        cbxModeloVehiculo.removeAllItems();
        //// Pide a la base de datos solo los modelos que pertenecen a esa marca
        List<modelos> modelosFiltrados = dao.listarModelosMarcas(idMarca);
        
        //// Llena el ComboBox con cada modelo encontrado
        for(modelos mod : modelosFiltrados){
            cbxModeloVehiculo.addItem(mod);
        }
        //// Des selecciona cualquier opción para que empiece en blanco
        cbxModeloVehiculo.setSelectedIndex(-1);
    
    }
    
    //controlar el compartimienteo dinamico entre lo comboxBox marca y modelos
    public void initEvents(){
        //// El combo de modelos empieza bloqueado hasta que elijan una marca
        vista.cbxModeloVehiculo.setEnabled(false);
        
        //// Detecta cuando el usuario cambia la marca seleccionada
        vista.cbxMarcaVehiculo.addActionListener(e ->{
            Object selectedItem = vista.cbxMarcaVehiculo.getSelectedItem();
        
            //// Si seleccionó una marca válida, busca sus modelos y desbloquea el combo
        if (selectedItem instanceof marcas) {
            int idMarca = ((marcas) selectedItem).getId();
            cargarModeloPorMarcas(vista.cbxModeloVehiculo, idMarca);
            vista.cbxModeloVehiculo.setEnabled(true);
            
            //// Si desmarcó o no hay nada válido, limpia y vuelve a bloquear el combo
        } else {
            vista.cbxModeloVehiculo.removeAllItems();
            vista.cbxModeloVehiculo.setEnabled(false);
        }
    });
        
    }
    

public boolean insertar(){
    vehiculos v = new vehiculos();

    String placa = vista.flPlaca.getText().trim().toUpperCase();
    
//  VALIDACIÓN GENERAL SI LOS CAMPOS ESTA VACIOS
    if (placa.isEmpty() ||
        vista.cbxMarcaVehiculo.getSelectedItem() == null || 
        vista.cbxModeloVehiculo.getSelectedItem() == null || 
        vista.cbxTipoVehiculo.getSelectedItem() == null || 
        vista.cbxColorVehiculo.getSelectedItem() == null || 
        vista.cbxEstadoCliente.getSelectedItem() == null) {
        
        JOptionPane.showMessageDialog(
            vista, 
            "Por favor, complete todos los campos obligatorios.", 
            "Campos Incompletos", 
            JOptionPane.WARNING_MESSAGE
        );
        return false; // Detiene la ejecución si falta cualquiera de los ComboBox
    }
    
    //  SI TODO ESTÁ COMPLETO, HACEMOS EL CASTING
   // CASTING: Cambia un dato genérico por uno específico para poder usarlo.
    v.setIdMarca(((marcas) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
    v.setIdModelo(((modelos) vista.cbxModeloVehiculo.getSelectedItem()).getId());
    v.setIdTipo(((tipos) vista.cbxTipoVehiculo.getSelectedItem()).getId());
    v.setIdColor(((colores) vista.cbxColorVehiculo.getSelectedItem()).getId());
    v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
    
    //validacion formato placa
    if (!placa.matches("^[A-Z]{3}-\\d{3,4}$")) {
        JOptionPane.showMessageDialog(
            vista, 
            "Formato de placa inválido.\n", 
            "Error de Formato", 
            JOptionPane.ERROR_MESSAGE
        );
        return false; // Detiene el guardado
    }
    v.setPlaca(placa);
  
    //validacion si la placa ya existe  
    if (dao.existePlaca(v.getPlaca())) {
        JOptionPane.showMessageDialog(
            vista, 
            "La placa '" + v.getPlaca() + "' ya se encuentra registrada.", 
            "Placa Duplicada", 
            JOptionPane.WARNING_MESSAGE
        );
        return false; // Detiene el guardado aquí mismo
    }
   
if(dao.insertarVehiculo(v)){
     JOptionPane.showMessageDialog(null,"Vehiculo Registrado");
      mostrarTabla();
      return true;
}else{
     JOptionPane.showMessageDialog(null,"Error al intentar registrar el vehículo");
       return false; 
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



public boolean editar(int idVehiculos){
    vehiculos v = new vehiculos();
    v.setIdVehiculo(idVehiculos);

    String placa = vista.flPlaca.getText().trim().toUpperCase();
    
    if (placa.isEmpty() ||
        vista.cbxMarcaVehiculo.getSelectedItem() == null || 
        vista.cbxModeloVehiculo.getSelectedItem() == null || 
        vista.cbxTipoVehiculo.getSelectedItem() == null || 
        vista.cbxColorVehiculo.getSelectedItem() == null || 
        vista.cbxEstadoCliente.getSelectedItem() == null) {
        
        JOptionPane.showMessageDialog(
            vista, 
            "Por favor, complete todos los campos obligatorios.", 
            "Campos Incompletos", 
            JOptionPane.WARNING_MESSAGE
        );
        return false; 
    }
    
    v.setIdMarca(((marcas) vista.cbxMarcaVehiculo.getSelectedItem()).getId());
    v.setIdModelo(((modelos) vista.cbxModeloVehiculo.getSelectedItem()).getId());
    v.setIdTipo(((tipos) vista.cbxTipoVehiculo.getSelectedItem()).getId());
    v.setIdColor(((colores) vista.cbxColorVehiculo.getSelectedItem()).getId());
    v.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());
    
    if (!placa.matches("^[A-Z]{3}-\\d{3,4}$")) {
        JOptionPane.showMessageDialog(
            vista, 
            "Formato de placa inválido.\n", 
            "Error de Formato", 
            JOptionPane.ERROR_MESSAGE
        );
        return false; 
    }
    if (dao.existePlacaAlEditar(placa, v.getIdVehiculo())) {
        JOptionPane.showMessageDialog(
            vista, 
            "La placa '" + placa + "' ya se encuentra registrada en otro vehículo.", 
            "Placa Duplicada", 
            JOptionPane.WARNING_MESSAGE
        );
        return false; // Detiene el guardado
    }
    v.setPlaca(placa);

    // Ejecutar el update
    if(dao.editarVehiculo(v)){
        JOptionPane.showMessageDialog(null, "Vehículo actualizado exitosamente");
        mostrarTabla();
        return true;
    } else {
        JOptionPane.showMessageDialog(null, "Error al actualizar el vehículo");
        return false;
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
   

    
    

