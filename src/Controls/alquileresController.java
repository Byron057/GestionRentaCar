package Controls;

import DAO.alquileresDAO;
import Models.alquileres;
import Models.vehiculos;
import Models.clientes;
import Views.panels.RentasForm;
import Views.panels.RentasPanel;
import java.util.List;
import javax.swing.JComboBox;

import javax.swing.JOptionPane;

public class alquileresController {

    private RentasForm vista;
    private alquileresDAO dao;
    private RentasPanel vista2;

    public alquileresController(RentasForm vista, RentasPanel vistaPanel){

    this.vista = vista;
    dao = new alquileresDAO();

    if(this.vista != null){
        cargarCombos();
    }
}
    private List<alquileres> listaAlquileres;

public void mostrarTabla(){

    // Validación de que la vista y la tabla no estén vacías o nulas
    if(vista2 != null && vista2.tableClientes != null){

        vista2.tableClientes.limpiarTabla();

        // Llenamos la lista global
        listaAlquileres = dao.listarAlquileres();

        for(alquileres x : listaAlquileres){

            vista2.tableClientes.agregarFila(new Object[]{

                x.getIdAlquiler(),
                x.getNombreCliente(),
                x.getPlaca(),
                x.getFechaAlquiler(),
                x.getDias(),
                x.getTotal(),
                x.getEstado()

            });

        }

    }

}
public void cargarClientes(JComboBox<Object> cbxAlquilerCliente){
        cbxAlquilerCliente.removeAllItems();
        for (Object[] c : dao.listarClientesActivos()){
            cbxAlquilerCliente.addItem(c);
        }

        cbxAlquilerCliente.setSelectedIndex(-1); 

     }
    
    public void cargarVehiculos(JComboBox<Object> cbxAlquilerVehiculo){
        cbxAlquilerVehiculo.removeAllItems();
        for (Object[] v : dao.listarVehiculosDisponibles()){
            cbxAlquilerVehiculo.addItem(v);
        }


        cbxAlquilerVehiculo.setSelectedIndex(-1); 

     }
    //=========================================
    // CARGAR COMBOS
    //=========================================
    
    public void cargarCombos(){

        vista.cbxAlquilerCliente.removeAllItems();

        for(Object[] dato : dao.listarClientesActivos()){
            vista.cbxAlquilerCliente.addItem(
                    dato[0] + " - " + dato[1]
            );
        }

        vista.cbxAlquilerVehiculo.removeAllItems();

        for(Object[] dato : dao.listarVehiculosDisponibles()){
            vista.cbxAlquilerVehiculo.addItem(
                    dato[0] + " - " + dato[1]
            );
        }

        vista.cbxAlquilerCliente.setSelectedIndex(-1);
        vista.cbxAlquilerVehiculo.setSelectedIndex(-1);
    }

    //=========================================
    // INSERTAR
    //=========================================

    public boolean insertar(){

        alquileres a = new alquileres();

        if(vista.cbxAlquilerCliente.getSelectedItem() == null ||
           vista.cbxAlquilerVehiculo.getSelectedItem() == null ||
           vista.flFecha.getText().trim().isEmpty() ||
           vista.flDias.getText().trim().isEmpty() ||
           vista.flTotal.getText().trim().isEmpty() ||
           vista.cbxEstadoCliente.getSelectedItem() == null){

            JOptionPane.showMessageDialog(
                    vista,
                    "Complete todos los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try{

            String cliente = vista.cbxAlquilerCliente.getSelectedItem().toString();
            String vehiculo = vista.cbxAlquilerVehiculo.getSelectedItem().toString();

            int dias = Integer.parseInt(vista.flDias.getText().trim());
            double total = Double.parseDouble(vista.flTotal.getText().trim());

            if(dias <= 0){

                JOptionPane.showMessageDialog(
                        vista,
                        "Los días deben ser mayores a cero.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

            if(total <= 0){

                JOptionPane.showMessageDialog(
                        vista,
                        "El total debe ser mayor a cero.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

            a.setFkIdCliente(Integer.parseInt(cliente.split(" - ")[0]));
            a.setFkIdVehiculo(Integer.parseInt(vehiculo.split(" - ")[0]));
            a.setFechaAlquiler(vista.flFecha.getText().trim());
            a.setDias(dias);
            a.setTotal(total);
            a.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());

            if(dao.insertarAlquiler(a)){

                JOptionPane.showMessageDialog(
                        vista,
                        "Alquiler registrado correctamente."
                );

                return true;

            }else{

                JOptionPane.showMessageDialog(
                        vista,
                        "Error al registrar el alquiler."
                );

                return false;
            }

        }catch(NumberFormatException e){

            JOptionPane.showMessageDialog(
                    vista,
                    "Los campos Días y Total deben contener valores numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    //=========================================
    // EDITAR
    //=========================================

    public boolean editar(int idAlquiler){

        alquileres a = new alquileres();
        a.setIdAlquiler(idAlquiler);

        if(vista.cbxAlquilerCliente.getSelectedItem() == null ||
           vista.cbxAlquilerVehiculo.getSelectedItem() == null ||
           vista.flFecha.getText().trim().isEmpty() ||
           vista.flDias.getText().trim().isEmpty() ||
           vista.flTotal.getText().trim().isEmpty() ||
           vista.cbxEstadoCliente.getSelectedItem() == null){

            JOptionPane.showMessageDialog(
                    vista,
                    "Complete todos los campos obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try{

            String cliente = vista.cbxAlquilerCliente.getSelectedItem().toString();
            String vehiculo = vista.cbxAlquilerVehiculo.getSelectedItem().toString();

            int dias = Integer.parseInt(vista.flDias.getText().trim());
            double total = Double.parseDouble(vista.flTotal.getText().trim());

            if(dias <= 0){

                JOptionPane.showMessageDialog(
                        vista,
                        "Los días deben ser mayores a cero."
                );

                return false;
            }

            if(total <= 0){

                JOptionPane.showMessageDialog(
                        vista,
                        "El total debe ser mayor a cero."
                );

                return false;
            }

            a.setFkIdCliente(Integer.parseInt(cliente.split(" - ")[0]));
            a.setFkIdVehiculo(Integer.parseInt(vehiculo.split(" - ")[0]));
            a.setFechaAlquiler(vista.flFecha.getText().trim());
            a.setDias(dias);
            a.setTotal(total);
            a.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());

            if(dao.editarAlquiler(a)){

                JOptionPane.showMessageDialog(
                        vista,
                        "Alquiler actualizado correctamente."
                );

                return true;

            }else{

                JOptionPane.showMessageDialog(
                        vista,
                        "Error al actualizar el alquiler."
                );

                return false;
            }

        }catch(NumberFormatException e){

            JOptionPane.showMessageDialog(
                    vista,
                    "Los campos Días y Total deben contener valores numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    //=========================================
    // ELIMINAR
    //=========================================

    public void eliminar(int idAlquiler){

        if(dao.eliminarAlquiler(idAlquiler)){

            JOptionPane.showMessageDialog(
                    vista,
                    "Alquiler eliminado correctamente."
            );

        }else{

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo eliminar el alquiler."
            );
        }
    }

    public void initEvents(){

    vista.cbxAlquilerCliente.addActionListener(e -> {

        if(vista.cbxAlquilerCliente.getSelectedItem() != null){
            vista.cbxAlquilerCliente.setForeground(new java.awt.Color(60,60,60));
        }

    });

    vista.cbxAlquilerVehiculo.addActionListener(e -> {

        if(vista.cbxAlquilerVehiculo.getSelectedItem() != null){
            vista.cbxAlquilerVehiculo.setForeground(new java.awt.Color(60,60,60));
        }

    });

}
}