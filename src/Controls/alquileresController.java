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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class alquileresController {

    private RentasForm vista;
    private RentasPanel vista2;
    private alquileresDAO dao;
    private List<alquileres> listaAlquileres;

    public alquileresController(RentasForm vista, RentasPanel vistaPanel){
        this.vista = vista;
        this.vista2 = vistaPanel;
        dao = new alquileresDAO();

        if(this.vista != null){
            cargarCombos();
        }
        if(this.vista2 != null){
            mostrarTabla();
        }
    }

    public void mostrarTabla(){
        if(vista2 != null && vista2.tableClientes != null){
            vista2.tableClientes.limpiarTabla();

            listaAlquileres = dao.listarAlquileres();

            for(alquileres x : listaAlquileres){
                vista2.tableClientes.agregarFila(new Object[]{
                    x.getIdAlquiler(),
                    x.getNombreCliente(),
                    x.getPlaca(),
                    x.getFechaAlquiler(),
                    "$"+x.getTotal(),
                    x.getDias(),
                    x.getEstado()
                });
            }
        }
    }

    public void cargarClientes(JComboBox<Object> cbxAlquilerCliente){
        cbxAlquilerCliente.removeAllItems();
        for (clientes c : dao.listarClientesActivos()){
            cbxAlquilerCliente.addItem(c);
        }
        cbxAlquilerCliente.setSelectedIndex(-1); 
    }
    
    public void cargarVehiculos(JComboBox<Object> cbxAlquilerVehiculo){
        cbxAlquilerVehiculo.removeAllItems();
        for (vehiculos v : dao.listarVehiculosDisponibles()){
            cbxAlquilerVehiculo.addItem(v);
        }
        cbxAlquilerVehiculo.setSelectedIndex(-1); 
    }
    
    public void cargarCombos(){
        if (vista != null) {
            cargarClientes(vista.cbxAlquilerCliente);
            cargarVehiculos(vista.cbxAlquilerVehiculo);
        }
    }

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

        String fechaTexto = vista.flFecha.getText().trim();
        try {
            LocalDate.parse(fechaTexto);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    vista,
                    "La fecha es inválida o no existe.\nDebe tener el formato exacto: AAAA-MM-DD\nEjemplo: 2026-08-03",
                    "Error en la Fecha",
                    JOptionPane.ERROR_MESSAGE
            );
            return false; 
        }

        int dias = 0;
        double total = 0.0;
        
        try {
            dias = Integer.parseInt(vista.flDias.getText().trim());
            total = Double.parseDouble(vista.flTotal.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Los campos 'Días' y 'Total' solo aceptan números.\nNo ingrese letras ni caracteres especiales.",
                    "Error de Formato Numérico",
                    JOptionPane.ERROR_MESSAGE
            );
            return false; 
        }

        if(dias <= 0){
            JOptionPane.showMessageDialog(vista, "La cantidad de días debe ser mayor a cero.", "Dato Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(total <= 0){
            JOptionPane.showMessageDialog(vista, "El total a cobrar debe ser mayor a cero.", "Dato Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        a.setFkIdCliente(((clientes) vista.cbxAlquilerCliente.getSelectedItem()).getId_cliente());
        a.setFkIdVehiculo(((vehiculos) vista.cbxAlquilerVehiculo.getSelectedItem()).getIdVehiculo());
        a.setFechaAlquiler(fechaTexto);
        a.setDias(dias);
        a.setTotal(total);
        a.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());

        if(dao.insertarAlquiler(a)){
            JOptionPane.showMessageDialog(vista, "Alquiler registrado correctamente.");
            mostrarTabla(); 
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el alquiler.", "Error en Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

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

        String fechaTexto = vista.flFecha.getText().trim();
        try {
            LocalDate.parse(fechaTexto);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    vista,
                    "La fecha es inválida o no existe.\nDebe tener el formato exacto: AAAA-MM-DD\nEjemplo: 2026-08-03",
                    "Error en la Fecha",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        int dias = 0;
        double total = 0.0;
        
        try {
            dias = Integer.parseInt(vista.flDias.getText().trim());
            total = Double.parseDouble(vista.flTotal.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Los campos 'Días' y 'Total' solo aceptan números.\nNo ingrese letras ni caracteres especiales.",
                    "Error de Formato Numérico",
                    JOptionPane.ERROR_MESSAGE
            );
            return false; 
        }

        if(dias <= 0){
            JOptionPane.showMessageDialog(vista, "La cantidad de días debe ser mayor a cero.", "Dato Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(total <= 0){
            JOptionPane.showMessageDialog(vista, "El total a cobrar debe ser mayor a cero.", "Dato Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        a.setFkIdCliente(((clientes) vista.cbxAlquilerCliente.getSelectedItem()).getId_cliente());
        a.setFkIdVehiculo(((vehiculos) vista.cbxAlquilerVehiculo.getSelectedItem()).getIdVehiculo());
        a.setFechaAlquiler(fechaTexto);
        a.setDias(dias);
        a.setTotal(total);
        a.setEstado(vista.cbxEstadoCliente.getSelectedItem().toString());

        if(dao.editarAlquiler(a)){
            JOptionPane.showMessageDialog(vista, "Alquiler actualizado correctamente.");
            mostrarTabla(); 
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el alquiler.", "Error en Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean finalizarAlquiler(int idAlquiler) {
        int respuesta = JOptionPane.showConfirmDialog(
                (vista2 != null) ? vista2 : null,
                "¿Está seguro de que desea finalizar esta renta?",
                "Confirmar finalización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta != JOptionPane.YES_OPTION) {
            return false;
        }

        boolean exito = dao.cambiarEstadoAlquiler(idAlquiler, "Finalizado");
        if (exito) {
            JOptionPane.showMessageDialog(
                    (vista2 != null) ? vista2 : null,
                    "Renta finalizada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            if (vista2 != null) {
                mostrarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(
                    (vista2 != null) ? vista2 : null,
                    "Error al finalizar la renta.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return exito;
    }

    public void initEvents(){
        if(vista == null) return;

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