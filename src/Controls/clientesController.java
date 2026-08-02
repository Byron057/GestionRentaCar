/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import DAO.clientesDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Models.clientes;
import Views.panels.ClientesForm;
import Views.panels.ClientesPanel;

public class clientesController {

    private ClientesForm vista;
    private ClientesPanel vistaP;
    private clientesDAO dao;

    // Constructores (mantengo ambos para no romper compatibilidad)
    public clientesController(ClientesForm vista) {
        this.vista = vista;
        dao = new clientesDAO();
    }

    public clientesController(ClientesPanel vistaP) {
        this.vistaP = vistaP;
        this.dao = new clientesDAO();
    }

    // ---------- INSERTAR CON VALIDACIONES ----------
    public boolean insertar() {
    // Obtener datos
    String cedula = vista.flCedula.getText().trim();
    String nombre = vista.flNombreCliente.getText().trim();
    String apellido = vista.flApellidoCliente.getText().trim();
    String telefono = vista.flTelefonoCliente.getText().trim();
    String direccion = vista.flDireccionCliente.getText().trim();

    // --- VALIDACIONES ---

    // 1. Campos vacíos
    if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || 
        telefono.isEmpty() || direccion.isEmpty()) {
        JOptionPane.showMessageDialog(vista,
                "Todos los campos son obligatorios.",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // 2. Validar cédula (solo números, 10 dígitos)
    if (!cedula.matches("\\d{10}")) {
        JOptionPane.showMessageDialog(vista,
                "La cédula debe tener 10 dígitos numéricos.",
                "Formato inválido",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // 3. Validar teléfono (7-10 dígitos)
    if (!telefono.matches("\\d{7,10}")) {
        JOptionPane.showMessageDialog(vista,
                "El teléfono debe tener entre 7 y 10 dígitos.",
                "Formato inválido",
                JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // 4. Validar que el combo tenga selección
    Object estadoObj = vista.cbxsEstadoCliente.getSelectedItem();
    if (estadoObj == null) {
        JOptionPane.showMessageDialog(vista,
                "Debe seleccionar un estado para el cliente (Activo/Inactivo).",
                "Campo obligatorio",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }
    String estado = estadoObj.toString();

    // 5. Verificar cédula duplicada
    if (dao.existeCedula(cedula)) {
        JOptionPane.showMessageDialog(vista,
                "La cédula '" + cedula + "' ya está registrada.",
                "Cédula duplicada",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // --- GUARDAR ---
    clientes cl = new clientes();
    cl.setCedula(cedula);
    cl.setNombre(nombre);
    cl.setApellido(apellido);
    cl.setTelefono(telefono);
    cl.setDireccion(direccion);
    cl.setEstado(estado);

    if (dao.insertarCliente(cl)) {
        JOptionPane.showMessageDialog(vista, "Cliente registrado exitosamente.");
        if (vistaP != null) listar();
        return true;
    } else {
        JOptionPane.showMessageDialog(vista, "Error al registrar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

    // ---------- LISTAR ----------
    public void listar() {
        if (vistaP == null || vistaP.tableClientes == null) {
            System.out.println("La vista o la tabla no están inicializadas.");
            return;
        }
        vistaP.tableClientes.limpiarTabla();
        List<clientes> lista = dao.listarClientes();
        for (clientes cl : lista) {
            vistaP.tableClientes.agregarFila(new Object[]{
                cl.getId_cliente(),
                cl.getCedula(),
                cl.getNombre(),
                cl.getApellido(),
                cl.getTelefono(),
                cl.getDireccion(),
                cl.getEstado()
            });
        }
    }

    // ---------- EDITAR CON VALIDACIONES ----------
    public boolean editar(int id) {
        // 1. Obtener datos
        String cedula = vista.flCedula.getText().trim();
        String nombre = vista.flNombreCliente.getText().trim();
        String apellido = vista.flApellidoCliente.getText().trim();
        String telefono = vista.flTelefonoCliente.getText().trim();
        String direccion = vista.flDireccionCliente.getText().trim();
        String estado = vista.cbxsEstadoCliente.getSelectedItem().toString();

        // 2. Validar campos obligatorios
        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Todos los campos son obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 3. Validar formato de cédula
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(vista,
                    "La cédula debe contener 10 dígitos numéricos.",
                    "Formato inválido",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 4. Validar formato de teléfono
        if (!telefono.matches("\\d{7,10}")) {
            JOptionPane.showMessageDialog(vista,
                    "El teléfono debe contener entre 7 y 10 dígitos numéricos.",
                    "Formato inválido",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 5. Verificar cédula duplicada (excluyendo el propio registro)
        if (dao.existeCedulaAlEditar(cedula, id)) {
            JOptionPane.showMessageDialog(vista,
                    "La cédula '" + cedula + "' ya está registrada en otro cliente.",
                    "Cédula duplicada",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 6. Crear objeto y actualizar
        clientes cl = new clientes();
        cl.setId_cliente(id);
        cl.setCedula(cedula);
        cl.setNombre(nombre);
        cl.setApellido(apellido);
        cl.setTelefono(telefono);
        cl.setDireccion(direccion);
        cl.setEstado(estado);

        if (dao.editarCliente(cl)) {
            JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente.");
            if (vistaP != null) {
                listar();
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ---------- DESACTIVAR CLIENTE (eliminación lógica) ----------
    public boolean desactivarCliente(int idCliente) {
        // Preguntar confirmación
        int respuesta = JOptionPane.showConfirmDialog(
                (vistaP != null) ? vistaP : vista,
                "¿Está seguro de que desea desactivar (inactivar) este cliente?",
                "Confirmar desactivación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta != JOptionPane.YES_OPTION) {
            return false;
        }

        // Cambiar estado a "Inactivo"
        boolean exito = dao.cambiarEstadoCliente(idCliente, "Inactivo");
        if (exito) {
            JOptionPane.showMessageDialog(
                    (vistaP != null) ? vistaP : vista,
                    "Cliente desactivado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            if (vistaP != null) {
                listar(); // Refrescar tabla
            }
        } else {
            JOptionPane.showMessageDialog(
                    (vistaP != null) ? vistaP : vista,
                    "Error al desactivar el cliente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return exito;
    }

    // ---------- (Opcional) REACTIVAR CLIENTE ----------
    public boolean activarCliente(int idCliente) {
        boolean exito = dao.cambiarEstadoCliente(idCliente, "Activo");
        if (exito) {
            JOptionPane.showMessageDialog(
                    (vistaP != null) ? vistaP : vista,
                    "Cliente reactivado correctamente.");
            if (vistaP != null) {
                listar();
            }
        } else {
            JOptionPane.showMessageDialog(
                    (vistaP != null) ? vistaP : vista,
                    "Error al reactivar el cliente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return exito;
    }
}