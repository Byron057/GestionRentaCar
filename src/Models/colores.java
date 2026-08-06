
package Models;


public class colores {
    private int id;
    private String color;
    private String estado;
    private String nombreColor;
   
    public colores() {
  
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

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }
    
    @Override
public String toString() {
    return this.nombreColor; 
}
    
}
