/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class FlujoTrabajo {
    private String nombre;
    private List<String> estados;
    private Map<String, List<String>> transiciones;
    private Map<String, String> reglas;
    private Map<String, String> accionesAutomaticas;

    public FlujoTrabajo(String nombre) {
        this.nombre = nombre;
        this.estados = new ArrayList<>();
        this.transiciones = new HashMap<>();
        this.reglas = new HashMap<>();
        this.accionesAutomaticas = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public Map<String, List<String>> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Map<String, List<String>> transiciones) {
        this.transiciones = transiciones;
    }
    
    public void agregarTransicion(String origen, String destino) {
        transiciones.putIfAbsent(origen, new ArrayList<>());
        if (!transiciones.get(origen).contains(destino)) {
            transiciones.get(origen).add(destino);
        }  
    }
    
    public void eliminarTransicion(String origen, String destino) {
        if (transiciones.containsKey(origen)) {
            transiciones.get(origen).remove(destino);
        }
    }

    public void setReglas(Map<String, String> reglas) {
        this.reglas = reglas;
    }
    
    public void agregarRegla(String estado, String regla) {
        reglas.put(estado, regla);
    }

    public Map<String, String> getAccionesAutomaticas() {
        return accionesAutomaticas;
    }

    public void setAccionesAutomaticas(Map<String, String> accionesAutomaticas) {
        this.accionesAutomaticas = accionesAutomaticas;
    }
    
    public void agregarAccion(String estado, String accion) {
        accionesAutomaticas.put(estado, accion);
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
