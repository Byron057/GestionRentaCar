/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author USER
 */
public class modelos {
    private int id;
    private int fk_id_marca;
    private String modelo;
    private String estado;
    
    //cambio o borrar
    private String nombreModelo;
    
    public modelos() {
        
    }

    public int getId() {
        return id;
    }

    public int getFk_id_marca() {
        return fk_id_marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFk_id_marca(int fk_id_marca) {
        this.fk_id_marca = fk_id_marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }
    
    
    
    

  @Override
public String toString() {
    return this.nombreModelo; // Asegúrate de que esta variable sea la que almacena el texto
}    
    
    
}
