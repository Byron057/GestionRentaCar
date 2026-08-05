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
    private String marca;
    private String modelo;
    private String estado;

    public int getFk_id_marca() {
        return fk_id_marca;
    }

    public void setFk_id_marca(int fk_id_marca) {
        this.fk_id_marca = fk_id_marca;
    }
    
    public modelos() {
        
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
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

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    
}
