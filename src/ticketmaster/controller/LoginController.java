/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;


/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class LoginController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private static final String ADMIN_EMAIL = "admin@ticketmaster.com";
    private static final String ADMIN_PASSWORD = "admin123";
    
    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            cargarVentanaConfiguracion();
        } else {
            lblError.setText("Credenciales incorrectas. Use admin@ticketmaster.com / admin123");
        }
    }
       
        private void cargarVentanaConfiguracion() {    
            try {
                Stage stage = (Stage) txtEmail.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("ticketmaster/view/configuracion.fxml"));
       
                stage.setScene(new Scene(root));
                stage.setTitle("Configuracion del Sistema");
                
            } catch (Exception e) {
                mostrarError("Error al cargar la interfaz" + e.getMessage());
            }
        }

      private void mostrarError(String mensaje) {
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText(mensaje);
           alert.showAndWait();
    } 
        
    
}
