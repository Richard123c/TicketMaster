/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ticketmaster;

import javafx.application.Application;
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
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ticketmaster/view/login.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setRoot(String fxmlPath) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(TicketMaster.class.getResource(fxmlPath));
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
