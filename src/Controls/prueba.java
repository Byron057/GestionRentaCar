package Controls;

import java.util.ArrayList;
import java.util.List;

public class prueba {

    public static class ModeloDTO {
        private String id;
        private String marca;
        private String modelo;
        private String estado;

        public ModeloDTO(String id, String marca, String modelo, String estado) {
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
            this.estado = estado;
        }

        public String getId() { return id; }
        public String getMarca() { return marca; }
        public String getModelo() { return modelo; }
        public String getEstado() { return estado; }
    }

    public List<ModeloDTO> obtenerModelosDesdeBD() {
        List<ModeloDTO> lista = new ArrayList<>();
        
        lista.add(new ModeloDTO("1", "Toyota", "Corolla", "Activo"));
        lista.add(new ModeloDTO("2", "Toyota", "Hilux", "Activo"));
        lista.add(new ModeloDTO("3", "Chevrolet", "Aveo", "Activo"));
        lista.add(new ModeloDTO("4", "Chevrolet", "Grand Vitara", "Activo"));
        lista.add(new ModeloDTO("5", "Mazda", "BT-50", "Activo"));

        return lista;
    }

    // AQUÍ ESTÁ EL CAMBIO: Se agregó "String estado"
    public boolean agregarModeloBD(String marca, String modelo, String estado) {
        System.out.println("Guardando en BD -> Marca: " + marca + ", Modelo: " + modelo + ", Estado: " + estado);
        return true; 
    }

    public boolean eliminarModeloBD(String id) {
        System.out.println("Cambiando a Inactivo en BD el ID: " + id);
        return true;
    }
}