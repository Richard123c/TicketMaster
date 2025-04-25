/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ticketmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


/**
 *
 * @author Lenovo
 */
public class TicketMaster extends Application {
    
    @Override
    public void start(Stage primaryStage){
    try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/login.fxml"));
    Scene scene = new Scene(loader.load());
    primaryStage.setTitle("Sistema de Tickets - Login");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
        
    public static void main(String[] args) {
        launch(args);
    }
    
}
