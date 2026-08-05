/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author USER
 */
public class marcas {
    
    private int id;
    private String marca;
    private String estado;
    
     public marcas() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getEstado() {
        return estado;
    }

   @Override
   public String toString(){
       return marca;
   }
    
}
