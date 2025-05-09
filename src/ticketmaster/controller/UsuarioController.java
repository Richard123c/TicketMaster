/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class UsuarioController  {

    private List<Usuario> listaUsuarios;
    
    public UsuarioController() {
        listaUsuarios = new ArrayList<>();
    }
    
    public List<Usuario> getlistaUsuarios() {
        return listaUsuarios;
    }
    
    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }
    
    public boolean eliminarUsuario(String nombreUsuario) {
        return listaUsuarios.removeIf(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario));
    }
    
    public Usuario buscarUsuario(String nombreUsuario) {
        return listaUsuarios.stream()
                .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
                .findFirst()
                .orElse(null);
    }
    
    public boolean nombreUsuarioExiste(String nombreUsuario) {
        return listaUsuarios.stream()
                .anyMatch(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario));
    }
    
    public boolean validarDatosUsuarios(String nombreCompleto, String correo, String nombreUsuario, String contraseña) {
        return nombreCompleto != null && nombreCompleto.length() >=  3 &&
                correo != null && correo.contains("@") &&
                nombreUsuario != null && nombreUsuario.length() >= 3 &&
                contraseña != null && contraseña.length() >= 6;
    }
    
    public void desactivarUsuario(Usuario usuario) {
        usuario.setActivo(false);
    }
}
