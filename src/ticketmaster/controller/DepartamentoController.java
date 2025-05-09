/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.model.Departamento;
import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class DepartamentoController {

    private List<Departamento> listaDepartamentos;
    
    public DepartamentoController() {
        listaDepartamentos = new ArrayList<>();
    }
    
    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }
    
    public void agregarDepartamento(Departamento depto) {
        listaDepartamentos.add(depto);
    }
    
    public void eliminarDepartamento(Departamento depto) {
        listaDepartamentos.remove(depto);
    }
    
    public boolean nombreDepartamentoExiste(String nombre) {
        return listaDepartamentos.stream()
                .anyMatch(d -> d.getNombre().equalsIgnoreCase(nombre));
    }
    
    public Departamento buscarDepartamento(String nombre) {
        return listaDepartamentos.stream()
                .filter(d -> d.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
    
    public boolean validarDatosDepartamento(String nombre, String descripcion) {
        return nombre != null && nombre.length() >= 3 && nombre.length() <= 50 &&
                (descripcion == null || descripcion.length() <= 200);
    }
}
