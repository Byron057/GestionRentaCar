package Controls;

import DAO.catalogoDAO;
import Models.colores;
import Models.tipos;
import Views.panels.ColoresForm;
import Views.panels.ColoresPanel;
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
    
    // ================= TIPOS =================
    
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
    
    public void eliminarTipos(int id){
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea eliminar este registro?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            if (dao.eliminarTipos(id)) {
                JOptionPane.showMessageDialog(null, "Tipo eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el registro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // ================= COLORES =================
        
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
                    c.getId_color(),
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
    
    public void eliminarColor(int id){
        int opc = JOptionPane.showConfirmDialog(
                null,
                "¿Desea eliminar este registro?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opc == JOptionPane.YES_OPTION) {
            if (dao.eliminarColores(id)) {
                JOptionPane.showMessageDialog(null, "Color eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el registro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}