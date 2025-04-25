/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.model.Rol;
import ticketmaster.model.Permiso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class RolesPermisosController {
    private List<Permiso> listaPermisos;
    private List<Rol> listaRoles;
    private Map<String, List<String>> historialCambios;
    
    public RolesPermisosController() {
        listaPermisos = new ArrayList<>();
        listaRoles = new ArrayList<>();
        historialCambios = new HashMap<>();
        cargarPermisosPorDefecto();
        cargarRolesPorDefecto();
    }
    
    private void cargarPermisosPorDefecto() {
        listaPermisos.add(new Permiso("Crear tickets", "Permite crear tickets de soporte"));
        listaPermisos.add(new Permiso("Ver tickets", "Permite visualizar tickets existentes"));
        listaPermisos.add(new Permiso("Editar tickets", "Permite editar el contenido de los tickets"));
        listaPermisos.add(new Permiso("Eliminar tickets", "Permite eliminar tickets"));
        listaPermisos.add(new Permiso("Asignar tickets", "Permite asignar tickets a tecnicos"));
        listaPermisos.add(new Permiso("Cambiar estado de tickets", "Permite cambiar el estado de los tickets"));
        listaPermisos.add(new Permiso("Agregar notas a tickets", "Permite adjuntar notas adicionales"));
        listaPermisos.add(new Permiso("Gestionar usuarios", "Acceso completo a la gestion de usuarios"));
        listaPermisos.add(new Permiso("Gestionar departamentos", "Permite crear, editar o eliminar departamentos"));
        listaPermisos.add(new Permiso("Crear flujos de trabajo", "Permite modificar los flujos de trabajo"));
        listaPermisos.add(new Permiso("Crear parametros del sistema", "Acceso a configuracion del sistema"));
    }
    
    private void cargarRolesPorDefecto() {
        Rol admin = new Rol("Administrador", "Tiene acceso total al sistema");
        //Acceso total a todos los permisos
        admin.getPermisos().addAll(listaPermisos);
        
        Rol tecnico = new Rol("Tecnico", "Gestiona y resuelve tickets");
        tecnico.agregarPermiso(buscarPermiso("Ver tickets"));
        tecnico.agregarPermiso(buscarPermiso("Editar tickets"));
        tecnico.agregarPermiso(buscarPermiso("Cambiar estado de tickets"));
        tecnico.agregarPermiso(buscarPermiso("Agregar notas tickets"));
        tecnico.agregarPermiso(buscarPermiso("Asignar tickets"));
        
        Rol usuario = new Rol("Usuario", "Puede crear y consultar tickets");
        usuario.agregarPermiso(buscarPermiso("Crear tickets"));
        usuario.agregarPermiso(buscarPermiso("Ver tickets"));
        
        listaRoles.add(admin);
        listaRoles.add(tecnico);
        listaRoles.add(usuario);
    }
    
    public Permiso buscarPermiso(String nombre) {
        return listaPermisos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
    
    public void agregarRol(Rol rol) {
        listaRoles.add(rol);
        registrarCambio(rol.getNombre(), "Rol creado");
    }
    
    public boolean eliminarRol(String nombreRol, boolean tieneUsuariosAsociados) {
        if (tieneUsuariosAsociados) {
            return false;
        }
        listaRoles.removeIf(r -> r.getNombre().equalsIgnoreCase(nombreRol));
        registrarCambio(nombreRol, "Rol eliminado");
        return true;
    }
    
    public void modificarPermisosRol(String nombreRol, List<Permiso> nuevosPermisos) {
        for (Rol rol : listaRoles) {
            if (rol.getNombre().equalsIgnoreCase(nombreRol)) {
                rol.getPermisos().clear();
                rol.getPermisos().addAll(nuevosPermisos);
                registrarCambio(nombreRol, "Permisos modificados");
                break;
            }
        }
    }
    
    public void registrarCambio(String nombreRol, String descripcionCambio) {
        if (!historialCambios.containsKey(nombreRol)) {
            historialCambios.put(nombreRol, new ArrayList<>());
        }
        historialCambios.get(nombreRol).add(descripcionCambio + " - " + java.time.LocalDateTime.now());
    }
    
    public List<Rol> getListaRoles() {
        return listaRoles;
    }
    
    public List<Permiso> getListaPermiso() {
        return listaPermisos;
    }
    
    public List<String> getHistorialCambios(String nombreRol) {
        return historialCambios.getOrDefault(nombreRol, new ArrayList<>());
    }
}
