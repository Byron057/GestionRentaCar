package Controls;

import DAO.dashboardDao;
import Models.alquileres;
import Views.frames.Dashboard;
import Views.panels.InicioPanel;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class dashboardController {

    private InicioPanel inicioVista;
    private Dashboard dashboardVista;
    private dashboardDao dao;
    private List<alquileres> listaAlquileres;

    public dashboardController(InicioPanel inicioVista) {
        this.inicioVista = inicioVista;
        this.dao = new dashboardDao();
        cargarEstadisticas();
        mostrarTabla();
    }

    public dashboardController(Dashboard dashboardVista) {
        this.dashboardVista = dashboardVista;
        this.dao = new dashboardDao();
        cargarNombreUsuario();
        cargarFechaActual();
        
    }

    public void cargarEstadisticas() {
        if (inicioVista != null) {
            inicioVista.txtVehiculosNumero.setText(String.valueOf(dao.contarVehiculosActivos()));
            inicioVista.txtVehiculoClientes.setText(String.valueOf(dao.contarClientesActivos()));
            inicioVista.txtAlquierelesFinalizados.setText(String.valueOf(dao.contarRentasFinalizadas()));
        }
    }

    public void cargarNombreUsuario() {
        if (dashboardVista != null) {
            String nombre = dao.obtenerNombreUsuario();
            dashboardVista.txtAdministrador.setText(nombre);
        }
    }

    public void cargarFechaActual() {
        if (dashboardVista != null) {
            LocalDate fecha = LocalDate.now();
            Locale spanishLocale = new Locale("es", "ES");
 
            String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, spanishLocale);
            diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);

            String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, spanishLocale);

            String fechaFormateada = diaSemana + " " + fecha.getDayOfMonth() + " de " + mes + " del " + fecha.getYear();
            
            dashboardVista.txtFecha.setText(fechaFormateada);
        }
    }
    public void mostrarTabla(){

        if(inicioVista != null && inicioVista.tableInicio != null){
            inicioVista.tableInicio.limpiarTabla();

            listaAlquileres = dao.listarAlquileresActivos();

            for(alquileres x : listaAlquileres){
                inicioVista.tableInicio.agregarFila(new Object[]{
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
}
