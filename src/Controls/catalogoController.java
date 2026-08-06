package Controls;

import DAO.catalogoDAO;
import Models.colores;
import Models.tipos;
import Models.marcas;
import Models.modelos;
import Views.panels.ColoresForm;
import Views.panels.ColoresPanel;
import Views.panels.MarcasForm;
import Views.panels.MarcasPanel;
import Views.panels.ModelosForm;
import Views.panels.ModelosPanel;
import Views.panels.TiposForm;
import Views.panels.TiposPanel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class catalogoController {
    
    private TiposForm vista;
    private catalogoDAO dao;
    private TiposPanel panel;
    private ColoresForm vistaColores;
    private ColoresPanel panelColores;
    private MarcasPanel panelMarcas;
    private MarcasForm vistaMarcas;
    private ModelosPanel panelModelos;
    private ModelosForm vistaModelos;
    
    public catalogoController(TiposForm vista){
        this.vista = vista;
        dao = new catalogoDAO();
    }
    public catalogoController(TiposPanel panel){
        this.panel = panel;
        dao = new catalogoDAO();
        listarTipo(panel);
    }
    
    public catalogoController(ColoresForm vista){
        this.vistaColores = vista;
        dao = new catalogoDAO();
    }
    public catalogoController(ColoresPanel panel){
        this.panelColores = panel;
        dao = new catalogoDAO();
        listarColores(panel);
    }
    public catalogoController(MarcasForm vista){
        this.vistaMarcas = vista;
        dao = new catalogoDAO();
    }
    public catalogoController(MarcasPanel panel){
        this.panelMarcas = panel;
        dao = new catalogoDAO();
        listarMarcas(panel);
    }
    public catalogoController(ModelosPanel panel){
        this.panelModelos = panel;
        dao = new catalogoDAO();
    }

    public catalogoController(ModelosForm vista){
        this.vistaModelos = vista;
        dao = new catalogoDAO();
    }
    
    // TIPOS 
    
    public boolean insertarTipo(TiposForm form){
        String tipoTexto = form.flTipo.getText().trim();
        
        if(tipoTexto.isEmpty() || tipoTexto.equals("Ingrese el Tipo")){
            JOptionPane.showMessageDialog(form, "Por favor, ingresa el nombre del tipo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxTipo.getSelectedIndex() == -1 || form.cbxTipo.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        tipos t = new tipos();
        t.setTipo(tipoTexto);
        t.setEstado(form.cbxTipo.getSelectedItem().toString());
         
        if (dao.guardarTipos(t)){
            JOptionPane.showMessageDialog(form, "Tipo registrado correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al registrar tipo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void listarTipo(TiposPanel panelTipos){
        if (panelTipos != null && panelTipos.tableTipos != null) {
            panelTipos.tableTipos.limpiarTabla();
            
            List<tipos> lista = dao.listarTipos();
            
            for(tipos t : lista){
                panelTipos.tableTipos.agregarFila(new Object[]{
                    t.getId(),
                    t.getTipo(),
                    t.getEstado()
                });
            }
        }
    }
    
    public boolean editarTipo(TiposForm form, int id){
        String tipoTexto = form.flTipo.getText().trim();
        
        if(tipoTexto.isEmpty() || tipoTexto.equals("Ingrese el Tipo")){
            JOptionPane.showMessageDialog(form, "El campo del tipo no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxTipo.getSelectedIndex() == -1 || form.cbxTipo.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        tipos t = new tipos();
        t.setId(id);
        t.setTipo(tipoTexto);
        t.setEstado(form.cbxTipo.getSelectedItem().toString());
        
        if(dao.editarTipos(t)){
            JOptionPane.showMessageDialog(form, "Tipo actualizado correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al actualizar tipo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean cambiarEstadoTipos(int id, String nuevoEstado){
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea cambiar el estado de este registro a " + nuevoEstado + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            boolean exito = false;
            if (nuevoEstado.equalsIgnoreCase("Activo")) {
                exito = dao.activarTipos(id);
            } else {
                exito = dao.eliminarTipos(id);
            }

            if (exito) {
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente a " + nuevoEstado);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
    
    //COLORES
        
    public boolean insertarColor(ColoresForm form){
        String colorTexto = form.flColores.getText().trim();
        
        if(colorTexto.isEmpty() || colorTexto.equals("Ingrese el Color")){
            JOptionPane.showMessageDialog(form, "Por favor, ingresa el nombre del color.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxColores.getSelectedIndex() == -1 || form.cbxColores.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        colores c = new colores();
        c.setColor(colorTexto);
        c.setEstado(form.cbxColores.getSelectedItem().toString());
        
        if (dao.guardarColores(c)){
            JOptionPane.showMessageDialog(form, "Color registrado correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al registrar color", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void listarColores(ColoresPanel panelColoresRef){
   
        if (panelColoresRef != null && panelColoresRef.tableColores != null) {
            panelColoresRef.tableColores.limpiarTabla();
            
            List<colores> lista = dao.listarColores();
            
            for(colores c : lista){
                panelColoresRef.tableColores.agregarFila(new Object[]{
                    c.getId(),
                    c.getColor(),
                    c.getEstado()
                });
            }
        }
    }
    
    public boolean editarColor(ColoresForm form, int id){
        String colorTexto = form.flColores.getText().trim();
        
        if(colorTexto.isEmpty() || colorTexto.equals("Ingrese el Color")){
            JOptionPane.showMessageDialog(form, "El campo del color no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxColores.getSelectedIndex() == -1 || form.cbxColores.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        colores c = new colores();
        c.setId_color(id);
        c.setColor(colorTexto);
        c.setEstado(form.cbxColores.getSelectedItem().toString());
        
        if(dao.editarColores(c)){
            JOptionPane.showMessageDialog(form, "Color actualizado correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al actualizar color", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean cambiarEstadoColores(int id, String nuevoEstado){
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea cambiar el estado de este registro a " + nuevoEstado + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            boolean exito = false;
            if (nuevoEstado.equalsIgnoreCase("Activo")) {
                exito = dao.activarColores(id);
            } else {
                exito = dao.eliminarColores(id);
            }

            if (exito) {
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente a " + nuevoEstado);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public boolean insertarMarca(MarcasForm form){
        String marcaTexto = form.flMarca.getText().trim();
        
        if(marcaTexto.isEmpty() || marcaTexto.equals("Ingrese la Marca")){
            JOptionPane.showMessageDialog(form, "Por favor, ingresa el nombre de la marca.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxMarca.getSelectedIndex() == -1 || form.cbxMarca.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        marcas m = new marcas();
        m.setMarca(marcaTexto);
        m.setEstado(form.cbxMarca.getSelectedItem().toString());
         
        if (dao.guardarMarcas(m)){
            JOptionPane.showMessageDialog(form, "Marca registrada correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al registrar marca", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void listarMarcas(MarcasPanel panelMarcasRef){
        if (panelMarcasRef != null && panelMarcasRef.tableMarcas != null) {
            panelMarcasRef.tableMarcas.limpiarTabla();
            
            List<marcas> lista = dao.listarMarcas();
            
            for(marcas m : lista){
                panelMarcasRef.tableMarcas.agregarFila(new Object[]{
                    m.getId(),
                    m.getMarca(),
                    m.getEstado()
                });
            }
        }
    }
    
    public boolean editarMarca(MarcasForm form, int id){
        String marcaTexto = form.flMarca.getText().trim();
        
        if(marcaTexto.isEmpty() || marcaTexto.equals("Ingrese la Marca")){
            JOptionPane.showMessageDialog(form, "El campo de la marca no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if(form.cbxMarca.getSelectedIndex() == -1 || form.cbxMarca.getSelectedItem() == null){
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        marcas m = new marcas();
        m.setId(id);
        m.setMarca(marcaTexto);
        m.setEstado(form.cbxMarca.getSelectedItem().toString());
        
        if(dao.editarMarcas(m)){
            JOptionPane.showMessageDialog(form, "Marca actualizada correctamente");
            return true;
        }else{
            JOptionPane.showMessageDialog(form, "Error al actualizar marca", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean cambiarEstadoMarcas(int id, String nuevoEstado){
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea cambiar el estado de este registro a " + nuevoEstado + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            boolean exito = false;
            if (nuevoEstado.equalsIgnoreCase("Activo")) {
                exito = dao.activarMarcas(id);
            } else {
                exito = dao.eliminarMarcas(id);
            }

            if (exito) {
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente a " + nuevoEstado);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    public void cargarMarcasEnCombo(assets.ComboBoxRound cbx) {
        List<marcas> marcas = dao.listarMarcasActivas();
        StringBuilder sb = new StringBuilder();
        for (marcas m : marcas) {
            sb.append(m.getMarca()).append("\n");
        }
        if (sb.length() > 0) {
            cbx.setOpciones(sb.toString());
        }
    }

    public void listarModelos(ModelosPanel panelModelosRef, String filtroMarca) {
        if (panelModelosRef != null && panelModelosRef.tableModelos != null) {
            panelModelosRef.tableModelos.limpiarTabla();
            
            List<modelos> lista = dao.listarModelosConMarca();
            
            for (modelos m : lista) {
                if (m.getMarca() != null && m.getMarca().equalsIgnoreCase(filtroMarca)) {
                    panelModelosRef.tableModelos.agregarFila(new Object[]{
                        m.getId(),
                        m.getMarca(),
                        m.getModelo(),
                        m.getEstado()
                    });
                }
            }
        }
    }

    public boolean insertarModelo(ModelosForm form, String nombreMarcaSeleccionada) {
        String modeloTexto = form.flMarca.getText().trim();
        
        if (modeloTexto.isEmpty() || modeloTexto.equals("Ingrese el Modelo")) {
            JOptionPane.showMessageDialog(form, "Por favor, ingresa el nombre del modelo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (form.cbxModelo.getSelectedIndex() == -1 || form.cbxModelo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        List<marcas> marcas = dao.listarMarcasActivas();
        int idMarca = 0;
        for (marcas m : marcas) {
            if (m.getMarca().equalsIgnoreCase(nombreMarcaSeleccionada)) {
                idMarca = m.getId();
                break;
            }
        }
        
        if (idMarca == 0) {
            JOptionPane.showMessageDialog(form, "Error: No se pudo identificar la marca seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        modelos m = new modelos();
        m.setFk_id_marca(idMarca);
        m.setModelo(modeloTexto);
        m.setEstado(form.cbxModelo.getSelectedItem().toString());
        
        if (dao.guardarModelo(m)) {
            JOptionPane.showMessageDialog(form, "Modelo registrado correctamente");
            return true;
        } else {
            JOptionPane.showMessageDialog(form, "Error al registrar modelo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean editarModelo(ModelosForm form, int idModelo, String nombreMarcaSeleccionada) {
        String modeloTexto = form.flMarca.getText().trim();
        
        if (modeloTexto.isEmpty() || modeloTexto.equals("Ingrese el Modelo")) {
            JOptionPane.showMessageDialog(form, "El campo del modelo no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (form.cbxModelo.getSelectedIndex() == -1 || form.cbxModelo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(form, "Por favor, selecciona un estado válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        List<marcas> marcas = dao.listarMarcasActivas();
        int idMarca = 0;
        for (marcas m : marcas) {
            if (m.getMarca().equalsIgnoreCase(nombreMarcaSeleccionada)) {
                idMarca = m.getId();
                break;
            }
        }
        
        if (idMarca == 0) {
            JOptionPane.showMessageDialog(form, "Error: No se pudo identificar la marca seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        modelos m = new modelos();
        m.setId(idModelo);
        m.setFk_id_marca(idMarca);
        m.setModelo(modeloTexto);
        m.setEstado(form.cbxModelo.getSelectedItem().toString());
        
        if (dao.editarModelo(m)) {
            JOptionPane.showMessageDialog(form, "Modelo actualizado correctamente");
            return true;
        } else {
            JOptionPane.showMessageDialog(form, "Error al actualizar modelo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean cambiarEstadoModelo(int id, String nuevoEstado) {
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea cambiar el estado de este registro a " + nuevoEstado + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            boolean exito = false;
            if (nuevoEstado.equalsIgnoreCase("Activo")) {
                exito = dao.activarModelo(id);
            } else {
                exito = dao.eliminarModelo(id);
            }

            if (exito) {
                JOptionPane.showMessageDialog(null, "Estado actualizado correctamente a " + nuevoEstado);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
}