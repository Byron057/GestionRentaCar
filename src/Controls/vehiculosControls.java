package Controls;

import DAO.vehiculosDAO;
import Views.panels.VehiculosForm;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTable;
        
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
    
    public void cargarModeloPorMarcas(JComboBox cbxModeloVehiculo , int idMarca){
        cbxModeloVehiculo.removeAllItems();
        List<modelos> modelosFiltrados = dao.listarModelosMarcas(idMarca);
        
        for(modelos mod : modelosFiltrados){
            cbxModeloVehiculo.addItem(mod);
        }
        cbxModeloVehiculo.setSelectedIndex(-1);
    
    }
    
    public void initEvents(){
        vista.cbxModeloVehiculo.setEnabled(false);
        
        vista.cbxMarcaVehiculo.addActionListener(e ->{
            Object selectedItem = vista.cbxMarcaVehiculo.getSelectedItem();
        
        if (selectedItem instanceof marcas) {
            int idMarca = ((marcas) selectedItem).getId();
            cargarModeloPorMarcas(vista.cbxModeloVehiculo, idMarca);
            vista.cbxModeloVehiculo.setEnabled(true);
            
        } else {
            vista.cbxModeloVehiculo.removeAllItems();
            vista.cbxModeloVehiculo.setEnabled(false);
        }
    });
        
    }

public boolean insertar(){
    vehiculos v = new vehiculos();

    String placa = vista.flPlaca.getText().trim().toUpperCase();
    
    if (placa.isEmpty() ||
        vista.cbxMarcaVehiculo.getSelectedItem() == null || 
        vista.cbxModeloVehiculo.getSelectedItem() == null || 
        vista.cbxTipoVehiculo.getSelectedItem() == null || 
        vista.cbxColorVehiculo.getSelectedItem() == null || 
        vista.cbxEstadoVehiculo.getSelectedItem() == null) {
        
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
    v.setEstado(vista.cbxEstadoVehiculo.getSelectedItem().toString());
    
    if (!placa.matches("^[A-Z]{3}-\\d{3,4}$")) {
        JOptionPane.showMessageDialog(
            vista, 
            "Formato de placa inválido.\n", 
            "Error de Formato", 
            JOptionPane.ERROR_MESSAGE
        );
        return false; 
    }
    v.setPlaca(placa);
  
    if (dao.existePlaca(v.getPlaca())) {
        JOptionPane.showMessageDialog(
            vista, 
            "La placa '" + v.getPlaca() + "' ya se encuentra registrada.", 
            "Placa Duplicada", 
            JOptionPane.WARNING_MESSAGE
        );
        return false; 
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
    if (vista2 != null && vista2.tableVehiculos != null) {
            vista2.tableVehiculos.limpiarTabla();
            
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

public boolean editar(int idVehiculos){
    vehiculos v = new vehiculos();
    v.setIdVehiculo(idVehiculos);

    String placa = vista.flPlaca.getText().trim().toUpperCase();
    
    if (placa.isEmpty() ||
        vista.cbxMarcaVehiculo.getSelectedItem() == null || 
        vista.cbxModeloVehiculo.getSelectedItem() == null || 
        vista.cbxTipoVehiculo.getSelectedItem() == null || 
        vista.cbxColorVehiculo.getSelectedItem() == null || 
        vista.cbxEstadoVehiculo.getSelectedItem() == null) {
        
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
    v.setEstado(vista.cbxEstadoVehiculo.getSelectedItem().toString());
    
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
        return false; 
    }
    v.setPlaca(placa);

    if(dao.editarVehiculo(v)){
        JOptionPane.showMessageDialog(null, "Vehículo actualizado exitosamente");
        mostrarTabla();
        return true;
    } else {
        JOptionPane.showMessageDialog(null, "Error al actualizar el vehículo");
        return false;
    }
}

public boolean desactivarVehiculo(int idVehiculo) {
    int respuesta = JOptionPane.showConfirmDialog(
            (vista2 != null) ? vista2 : null,
            "¿Está seguro de que desea desactivar este vehículo?",
            "Confirmar desactivación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

    if (respuesta != JOptionPane.YES_OPTION) {
        return false;
    }

    boolean exito = dao.cambiarEstadoVehiculo(idVehiculo, "Inactivo");
    if (exito) {
        JOptionPane.showMessageDialog(
                (vista2 != null) ? vista2 : null,
                "Vehículo desactivado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        if (vista2 != null) {
            mostrarTabla();
        }
    } else {
        JOptionPane.showMessageDialog(
                (vista2 != null) ? vista2 : null,
                "Error al desactivar el vehículo.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    return exito;
}

public boolean activarVehiculo(int idVehiculo) {
    int respuesta = JOptionPane.showConfirmDialog(
            (vista2 != null) ? vista2 : null,
            "¿Está seguro de que desea reactivar este vehículo?",
            "Confirmar reactivación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

    if (respuesta != JOptionPane.YES_OPTION) {
        return false;
    }

    boolean exito = dao.cambiarEstadoVehiculo(idVehiculo, "Activo");
    if (exito) {
        JOptionPane.showMessageDialog(
                (vista2 != null) ? vista2 : null,
                "Vehículo reactivado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        if (vista2 != null) {
            mostrarTabla();
        }
    } else {
        JOptionPane.showMessageDialog(
                (vista2 != null) ? vista2 : null,
                "Error al reactivar el vehículo.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    return exito;
}
}