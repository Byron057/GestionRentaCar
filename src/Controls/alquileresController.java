package Controls;


import DAO.alquileresDAO;
import Models.alquileres;
import Views.panels.RentasForm;

import javax.swing.JOptionPane;


public class alquileresController {


    private RentasForm vista;
    private alquileresDAO dao;



    public alquileresController(RentasForm vista){

        this.vista = vista;
        dao = new alquileresDAO();

        cargarCombos();

    }




    // =========================================
    // CARGAR COMBOS
    // =========================================

    public void cargarCombos(){

    vista.cbxAlquilerCliente.removeAllItems();

    for(Object[] dato : dao.listarClientesActivos()){
        vista.cbxAlquilerCliente.addItem(
                dato[0]+" - "+dato[1]
        );
    }

    vista.cbxAlquilerVehiculo.removeAllItems();

    System.out.println("Vehiculos:");

    for(Object[] dato : dao.listarVehiculosDisponibles()){

        System.out.println(dato[0]+" - "+dato[1]);

        vista.cbxAlquilerVehiculo.addItem(
                dato[0]+" - "+dato[1]
        );
    }

    System.out.println("Items cargados: "
            + vista.cbxAlquilerVehiculo.getItemCount());
}





    // =========================================
    // INSERTAR
    // =========================================

    public boolean insertar(){


        try{


            alquileres a = new alquileres();



            if(vista.cbxAlquilerCliente.getSelectedItem()==null ||
               vista.cbxAlquilerVehiculo.getSelectedItem()==null){


                JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione cliente y vehículo"
                );

                return false;

            }



            String cliente =
                    vista.cbxAlquilerCliente
                    .getSelectedItem()
                    .toString();



            String vehiculo =
                    vista.cbxAlquilerVehiculo
                    .getSelectedItem()
                    .toString();




            a.setFkIdCliente(
                Integer.parseInt(
                    cliente.split(" - ")[0]
                )
            );



            a.setFkIdVehiculo(
                Integer.parseInt(
                    vehiculo.split(" - ")[0]
                )
            );



            a.setFechaAlquiler(
                    vista.flFecha.getText()
            );



            a.setDias(
                Integer.parseInt(
                    vista.flDias.getText()
                )
            );



            a.setTotal(
                Double.parseDouble(
                    vista.flTotal.getText()
                )
            );



            a.setEstado(
                vista.cbxEstadoCliente
                .getSelectedItem()
                .toString()
            );





            if(dao.insertarAlquiler(a)){


                JOptionPane.showMessageDialog(
                        vista,
                        "Alquiler registrado"
                );


                return true;


            }else{


                JOptionPane.showMessageDialog(
                        vista,
                        "Error al registrar"
                );


                return false;

            }




        }catch(Exception e){


            JOptionPane.showMessageDialog(
                    vista,
                    "Error: "+e.getMessage()
            );


            return false;

        }



    }






    // =========================================
    // EDITAR
    // =========================================

    public boolean editar(int idAlquiler){


        try{


            alquileres a = new alquileres();


            a.setIdAlquiler(idAlquiler);



            String cliente =
                vista.cbxAlquilerCliente
                .getSelectedItem()
                .toString();



            String vehiculo =
                vista.cbxAlquilerVehiculo
                .getSelectedItem()
                .toString();




            a.setFkIdCliente(
                Integer.parseInt(
                    cliente.split(" - ")[0]
                )
            );



            a.setFkIdVehiculo(
                Integer.parseInt(
                    vehiculo.split(" - ")[0]
                )
            );



            a.setFechaAlquiler(
                    vista.flFecha.getText()
            );


            a.setDias(
                Integer.parseInt(
                    vista.flDias.getText()
                )
            );



            a.setTotal(
                Double.parseDouble(
                    vista.flTotal.getText()
                )
            );



            a.setEstado(
                    vista.cbxEstadoCliente
                    .getSelectedItem()
                    .toString()
            );




            if(dao.editarAlquiler(a)){


                JOptionPane.showMessageDialog(
                        vista,
                        "Alquiler actualizado"
                );


                return true;


            }else{


                return false;

            }




        }catch(Exception e){

            JOptionPane.showMessageDialog(
                    vista,
                    e.getMessage()
            );

            return false;

        }

    }






    // =========================================
    // ELIMINAR
    // =========================================

    public boolean eliminar(int idAlquiler){


    if(dao.eliminarAlquiler(idAlquiler)){

        return true;

    }else{

        return false;

    }

}



}