 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.DAO.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ticketmaster.model.Usuario;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class GestionUsuariosController  {

    @FXML private ListView<Usuario> lvUsuarios;
    @FXML private TextField txtNombreCompleto;
    @FXML private TextField  txtCorreo;
    @FXML private TextField txtNombreUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private ComboBox<String> cmbRol;
    @FXML private ComboBox<String> cmbDepartamento;
    @FXML private Button btnGuardarUsuario;
    @FXML private Button btnModificarUsuario;
    @FXML private Button btnEliminarUsuario;
    @FXML private Label lblEstado;
    
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        cmbRol.getItems().addAll("Administrador", "Tecnico", "Usuario");
        cmbDepartamento.getItems().addAll("Soporte", "Desarrollo", "Redes");
        usuarios.setAll(UsuarioDAO.obtenerUsuarios());
        lvUsuarios.setItems(usuarios);
        
        lvUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> mostrarUsuarioSeleccionado(newVal));
        
    }
    
    private void mostrarUsuarioSeleccionado(Usuario u) {
        if (u != null) {
            txtNombreCompleto.setText(u.getNombreCompleto());
            txtCorreo.setText(u.getCorreo());
            txtNombreUsuario.setText(u.getNombreUsuario());
            txtContraseña.setText(u.getContraseña());
            cmbRol.setValue(u.getRol());
            cmbDepartamento.setValue(u.getDepartamento());
        }
    }
    
    @FXML
    private void guardarUsuario() {
        String nombre = txtNombreCompleto.getText();
        String correo = txtCorreo.getText();
        String usuario = txtNombreUsuario.getText();
        String contraseña = txtContraseña.getText();
        String rol = cmbRol.getValue();
        String depto = cmbDepartamento.getValue();
        
        if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || rol == null) {
            lblEstado.setText("Todos los campos obligatorios deben estar llenos");
            return;
        }
        
        Usuario nuevo = new Usuario(nombre, correo, usuario, contraseña, rol, depto);
        if (UsuarioDAO.guardarUsuario(nuevo)) {
            usuarios.setAll(UsuarioDAO.obtenerUsuarios());
            lblEstado.setText("Usuario registrado exitosamente");
            limpiarCampos();
        } else {
            lblEstado.setText("Error al guardar usuario");
        }
    }
    
    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado = lvUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (UsuarioDAO.eliminarUsuario(seleccionado.getNombreUsuario())) {
                usuarios.setAll(UsuarioDAO.obtenerUsuarios());
                lblEstado.setText("Usuario eliminado");
                limpiarCampos();
            } else {
                lblEstado.setText("No se puede eliminar");
            }
        }
    }
    
    @FXML
    private void modificarUsuario() {
        Usuario seleccionado = lvUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblEstado.setText("Selecciona un usuario para modificar");
            return;
        }
        
        String nombre = txtNombreCompleto.getText();
        String correo = txtCorreo.getText();
        String usuario = txtNombreUsuario.getText();
        String contraseña = txtContraseña.getText();
        String rol = cmbRol.getValue();
        String depto = cmbDepartamento.getValue();
        
        if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || rol == null) {
            lblEstado.setText("Todos los campos obligatorios deben estar llenos");
            return;
        }
        
        seleccionado.setNombreCompleto(nombre);
        seleccionado.setCorreo(correo);
        seleccionado.setNombreUsuario(usuario);
        seleccionado.setContraseña(contraseña);
        seleccionado.setRol(rol);
        seleccionado.setDepartamento(depto);
        
        if (UsuarioDAO.actualizarUsuario(seleccionado)) {
            usuarios.setAll(UsuarioDAO.obtenerUsuarios());
            lblEstado.setText("Usuario actualizado exitosamente");
            limpiarCampos();
        } else {
            lblEstado.setText("Error al actualizar usuario");
        }
    }
    
    private void limpiarCampos() {
        txtNombreCompleto.clear();
        txtCorreo.clear();
        txtNombreUsuario.clear();
        txtContraseña.clear();
        cmbRol.setValue(null);
        cmbDepartamento.setValue(null);
        lvUsuarios.getSelectionModel().clearSelection();
    }
}
