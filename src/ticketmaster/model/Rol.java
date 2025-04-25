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
public class Rol {
    private String nombre;
    private String descripion;
    private List<Permiso> permisos;
    
    public Rol(String nombre, String descripion, List<Permiso> permisos) {
        this.nombre = nombre;
        this.descripion = descripion;
        this.permisos = permisos;
    }
    
    public Rol(String nombre, String descripion) {
        this.nombre = nombre;
        this.descripion = descripion;
        this.permisos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }
    
    public void agregarPermiso(Permiso permiso) {
        if(!permisos.contains(permiso)) {
            permisos.add(permiso);
        }
    }
    
    public void eliminarPermiso(Permiso permiso) {
        permisos.remove(permiso);
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
