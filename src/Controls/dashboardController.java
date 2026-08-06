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

    // Para el panel de inicio (estadísticas)
    public dashboardController(InicioPanel inicioVista) {
        this.inicioVista = inicioVista;
        this.dao = new dashboardDao();
        cargarEstadisticas();
        mostrarTabla();
    }

    // Para el Dashboard (nombre de usuario y fecha actual)
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
            
            // Día de la semana con primera letra en mayúscula (ej. "Martes")
            String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, spanishLocale);
            diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);
            
            // Mes en español (ej. "agosto")
            String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, spanishLocale);
            
            // Estructura final solicitada: "Martes 4 de Agosto del 2026"
            String fechaFormateada = diaSemana + " " + fecha.getDayOfMonth() + " de " + mes + " del " + fecha.getYear();
            
            dashboardVista.txtFecha.setText(fechaFormateada);
        }
    }
    public void mostrarTabla(){
        // Validación de que la vista y la tabla no estén vacías o nulas
        if(inicioVista != null && inicioVista.tableInicio != null){
            inicioVista.tableInicio.limpiarTabla();

            // Llenamos la lista global
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
