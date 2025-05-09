/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class EstadoTicket {
    private String nombre;
    private String descripcion;
    private boolean esFinal;
    private List<String> siguientesEstados;

    public EstadoTicket(String nombre, String descripcion, boolean esFinal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esFinal = esFinal;
        this.siguientesEstados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

    public List<String> getSiguientesEstados() {
        return siguientesEstados;
    }

    public void setSiguientesEstados(List<String> siguientesEstados) {
        this.siguientesEstados = siguientesEstados;
    }
    
    public void agregarTransicion(String estadoDestino) {
        if (!siguientesEstados.contains(estadoDestino)) {
            siguientesEstados.add(estadoDestino);
        }
    }
    
    public void eliminarTransicion(String estadoDestino) {
        siguientesEstados.remove(estadoDestino);
    }
    
    @Override
    public String toString() {
        return nombre + (esFinal ? " (Final)" : "");
    }
    
}
