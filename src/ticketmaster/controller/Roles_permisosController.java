/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;


import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import ticketmaster.model.Rol;
import ticketmaster.dao.RolDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class Roles_permisosController  {
    @FXML private TableView<Rol> tablaRoles;
    @FXML private ListView<String> listaPermisos;
    
    private final RolDAO rolDAO = new RolDAO();
    
    @FXML
    public void initialize() {
        cargarRoles();
        cargarPermisosDisponibles();
    }
    
    private void cargarRoles() {
         try {
             tablaRoles.getItems().setAll(rolDAO.listarRoles());
         } catch (SQLException e) {
             mostrarError("Error al cargar roles: " + e.getMessage()); 
         }
    }
    
    private void cargarPermisosDisponibles() {
        //Ejemplo con datos estaticos - reemplazar con llamado a DAO
        listaPermisos.setItems(FXCollections.observableArrayList("Crear tickets", "Editar Tickets",
                "Gestionar usuarios", "Configurar sistema"));
        
    }
    
    @FXML
    private void handleAsignarPermisos() {
        Rol rolSeleccionado = tablaRoles.getSelectionModel().getSelectedItem();
        if (rolSeleccionado != null) {
            List<String> permisosSeleccionados = listaPermisos.getSelectionModel().getSelectedItems();
            
            if (!permisosSeleccionados.isEmpty()) {
                try {
                    List<Integer> permisoIds = convertirNombresAIds(permisosSeleccionados);
                    
                    for (Integer permisoId : permisoIds) {
                        rolDAO.asignarPermiso(rolSeleccionado.getId(), permisoId);
                    }
                    
                    mostrarExito("Permisos asignados correctamente");
        } catch (SQLException e) {
            mostrarError("Error al asignar permisos: " + e.getMessage());
        }
    }  else {
                mostrarError("Seleccione al menos un permiso");
            }
            
        } else {
            mostrarError("Seleccione un rol primero");
        }
    }
    
    private List<Integer> convertirNombresAIds(List<String> nombrePermisos) {
        List<Integer> ids = new ArrayList<>();
        for (String nombre : nombrePermisos) {
            if (nombre.equals("Crear tickets")) ids.add(1);
            else if (nombre.equals("Editar tickets")) ids.add(2);
        }
        return ids;
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

    public class ValidarRoles {
        public static void validarNombreRol(String nombre) throws IllegalArgumentException {
            if (nombre == null || nombre.length() < 3 || nombre.length() > 50) {
                throw new IllegalArgumentException("El nombre del rol debe tener entre 3 y 50 caracteres");
            }
        }
    }
    
}
