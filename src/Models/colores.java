/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author USER
 */
public class colores {
    private int id;
    private String color;
    private String estado;
    
    public colores() {
    // Constructor vacío necesario para instanciar sin parámetros
}

    public colores(int id, String color, String estado) {
        this.id = id;
        this.color = color;
        this.estado = estado;
    }

    public void setId_color(int id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getEstado() {
        return estado;
    }
    
    
}
