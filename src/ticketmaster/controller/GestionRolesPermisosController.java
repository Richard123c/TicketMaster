/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ticketmaster.model.Rol;
import ticketmaster.model.Permiso;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class GestionRolesPermisosController {
    
    @FXML private ListView<Rol> lvRoles;
    @FXML private TextField txtNombreRol;
    @FXML private TextArea txtDescripcionRol;
    @FXML private ListView<Permiso> lvPermisosDisponibles;
    @FXML private ListView<Permiso> lvPermisosAsignados;
    @FXML private Button btnAgregarPermiso;
    @FXML private Button btnQuitarPermiso;
    @FXML private Button btnGuardarRol;
    @FXML private Button btnEliminarRol;
    @FXML private Label lblEstado;
    
    private final RolesPermisosController gestor = new RolesPermisosController();
    private final ObservableList<Rol> rolesObservable = FXCollections.observableArrayList();
    private final ObservableList<Permiso> permisosDisponibles = FXCollections.observableArrayList();
    private final ObservableList<Permiso> permisosAsignados = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        rolesObservable.setAll(gestor.getListaRoles());
        permisosDisponibles.setAll(gestor.getListaPermiso());
        
        lvRoles.setItems(rolesObservable);
        lvPermisosDisponibles.setItems(permisosDisponibles);
        lvPermisosAsignados.setItems(permisosAsignados);
        
        lvRoles.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> cargarRolSeleccionado(newVal));
    }
  
    
    private void cargarRolSeleccionado(Rol rol) {
        if (rol != null) {
            txtNombreRol.setText(rol.getNombre());
            txtDescripcionRol.setText(rol.getDescripion());
            permisosAsignados.setAll(rol.getPermisos());
            }
        }
        
        @FXML 
        private void agregarPermiso() {
        Permiso seleccionado = lvPermisosDisponibles.getSelectionModel().getSelectedItem();
        if (seleccionado != null && !permisosAsignados.contains(seleccionado)) {
            permisosAsignados.add(seleccionado);
        }
    }
        
        @FXML
        private void quitarPermiso() {
        Permiso seleccionado = lvPermisosAsignados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            permisosAsignados.remove(seleccionado);
        }
    }
        
        @FXML
        private void guardarRol() {
        String nombre = txtNombreRol.getText();
        String descripcion = txtDescripcionRol.getText();
        
        if (nombre.length() < 3 || nombre.length() > 50){
        lblEstado.setText("El nombre del rol debe tener entre 3 y 50 caracteres.");
        return;
    }
        
        Rol nuevoRol = new Rol(nombre, descripcion);
        nuevoRol.getPermisos().addAll(permisosAsignados);
        lblEstado.setText("Rol guardado correctamente.");
        limpiarCampos();
    
        
    }
    
    @FXML
    private void eliminarRol() {
        Rol seleccionado = lvRoles.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            boolean eliminado = gestor.eliminarRol(seleccionado.getNombre(), false);
            if (eliminado) {
                rolesObservable.setAll(gestor.getListaRoles());
                lblEstado.setText("Rol eliminado correctamente.");
                limpiarCampos();
            } else {
                lblEstado.setText("No se puede eliminar un rol con usuarios asociados.");
            }
        }
    }
    
    private void limpiarCampos() {
        txtNombreRol.clear();
        txtDescripcionRol.clear();
        permisosAsignados.clear();
        lvRoles.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void volverAPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/ConfigParametros.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Parametros del Sistema");
                    stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
