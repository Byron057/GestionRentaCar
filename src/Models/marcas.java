
package Models;

public class marcas {
    
    private int id;
    private String marca;
    private String estado;
    private String nombreMarca;
    
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

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    
    
  @Override
public String toString() {
    return this.nombreMarca; 
}

}
