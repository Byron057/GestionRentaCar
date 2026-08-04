package Controls;

import DAO.catalogoDAO;
import Models.colores;
import Models.tipos;
import java.util.List;
import javax.swing.JOptionPane;
import Views.panels.TiposForm;
import Views.panels.TiposPanel;
import javax.swing.table.DefaultTableModel;
import Views.panels.ColoresForm;
import Views.panels.ColoresPanel;

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
    }
    
    public catalogoController(ColoresForm vista){
        this.vistaColores = vista;
        dao = new catalogoDAO();
    }
    public catalogoController(ColoresPanel panel){
        this.panelColores = panel;
        dao = new catalogoDAO();
    }
    
    // ================= TIPOS =================
    
    public void insertarTipo(TiposForm form){
        tipos t = new tipos();
        
        // Usamos el 'form' recibido por parámetro de forma segura
        t.setTipo(form.flTipo.getText());
        t.setEstado(form.cbxTipo.getSelectedItem().toString());
         
        if (dao.guardarTipos(t)){
            JOptionPane.showMessageDialog(null, "Tipo registrado correctamente");
        }else{
            JOptionPane.showMessageDialog(null, "Error al registrar tipo");
        }
    }
    
    public void listarTipo(TiposPanel panel){
        DefaultTableModel modelo = (DefaultTableModel) panel.tableMarcas.getTabla().getModel();
        modelo.setRowCount(0);
        
        List<tipos> lista = dao.listarTipos();
        
        for(tipos t : lista){
            modelo.addRow(new Object[]{
                t.getId(),
                t.getTipo(),
                t.getEstado()
            });
        }
    }
    
    // Renombrado a editarTipo para evitar confusiones
    public void editarTipo(TiposForm form, int id){
        tipos t = new tipos();
        
        t.setId(id);
        t.setTipo(form.flTipo.getText());
        t.setEstado(form.cbxTipo.getSelectedItem().toString());
        
        if(dao.editarTipos(t)){
            JOptionPane.showMessageDialog(null, "Tipo actualizado correctamente");
            form.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Error al actualizar tipo");
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
                JOptionPane.showMessageDialog(null, "Tipo eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        }
    }
    
    // ================= COLORES =================
        
    public void insertarColor(ColoresForm form){
        colores c = new colores();
        
        // Usamos el 'form' recibido por parámetro de forma segura
        c.setColor(form.flColores.getText());
        c.setEstado(form.cbxColores.getSelectedItem().toString());
        
        if (dao.guardarColores(c)){
            JOptionPane.showMessageDialog(null, "Color registrado correctamente");
        }else{
            JOptionPane.showMessageDialog(null, "Error al registrar color");
        }
    }
    
    public void listarColores(ColoresPanel panel){
        DefaultTableModel modelo = (DefaultTableModel) panel.tableMarcas.getTabla().getModel();
        modelo.setRowCount(0);
        
        List<colores> lista = dao.listarColores();
        
        for(colores c : lista){
            modelo.addRow(new Object[]{
                c.getId_color(),
                c.getColor(),
                c.getEstado()
            });
        }
    }
    
    // Renombrado a editarColor para evitar confusiones
    public void editarColor(ColoresForm form, int id){
        colores c = new colores();
        
        c.setId_color(id);
        c.setColor(form.flColores.getText());
        c.setEstado(form.cbxColores.getSelectedItem().toString());
        
        if(dao.editarColores(c)){
            JOptionPane.showMessageDialog(null, "Color actualizado correctamente");
            form.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Error al actualizar color");
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
                JOptionPane.showMessageDialog(null, "Color eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        }
    }
}