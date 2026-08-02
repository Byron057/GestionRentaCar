/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Nikholov
 */
public class alquileres {
    private int idAlquiler;
    private int fkIdCliente;
    private int fkIdVehiculo;
    private String fechaAlquiler;
    private double total;
    private String estado;
    private int dias;
    
    //get

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public int getFkIdCliente() {
        return fkIdCliente;
    }

    public int getFkIdVehiculo() {
        return fkIdVehiculo;
    }

    public String getFechaAlquiler() {
        return fechaAlquiler;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public int getDias() {
        return dias;
    }
    //setters

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public void setFkIdCliente(int fkIdCliente) {
        this.fkIdCliente = fkIdCliente;
    }

    public void setFkIdVehiculo(int fkIdVehiculo) {
        this.fkIdVehiculo = fkIdVehiculo;
    }

    public void setFechaAlquiler(String fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
    
}
