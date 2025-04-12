/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import java.util.List;
/**
 *
 * @author Lenovo
 */
public class Rol {
    private int id;
    private String nombre;
    private String descripcion;
    private List<Permiso> permisos;
    
    public boolean tienePermiso(String nombrePermiso) {
        return permisos.stream().anyMatch(p -> p.getNombre().equals(nombrePermiso));
    }

    public Rol(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
    
    
}
