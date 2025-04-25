/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.sql.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import ticketmaster.database.Conexion;
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private Label lblError;
    
    @FXML
    public void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        
        try (Connection conn = Conexion.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                lblError.setText("Inicio de sesion exitoso!");
                
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/ConfigParametros.fxml"));
                Parent root = loader.load();
                
                Stage nuevaVentana = new Stage();
                nuevaVentana.setTitle("Configuracion del Sistema");
                nuevaVentana.setScene(new Scene(root));
                nuevaVentana.show();
                
            } else {
                lblError.setText("Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            lblError.setText("Error de conexion");
            e.printStackTrace();
        }
    }
}
