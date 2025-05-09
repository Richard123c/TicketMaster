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
public class Departamento {
    private String nombre;
    private String descripcion;
    private List<String> tecnicosAsignados;

    public Departamento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnicosAsignados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getTecnicosAsignados() {
        return tecnicosAsignados;
    }
    
    public void asignarTecnico(String tecnico) {
        if (!tecnicosAsignados.contains(tecnico)) {
            tecnicosAsignados.add(tecnico);
        }
    }
    
    public void quitarTecnico(String tecnico) {
        tecnicosAsignados.remove(tecnico);
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
