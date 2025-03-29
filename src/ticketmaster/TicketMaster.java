/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ticketmaster;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Lenovo
 */
public class TicketMaster extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    
    {
        abrirVentanaConfiguracion();
    }
    
    public static void abrirVentanaConfiguracion() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(TicketMaster.class.getResource("ticketmaster/modelo/configuracion.fxml"));
            
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Configuracion del Sistema");
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Error al abrir configuracion");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
