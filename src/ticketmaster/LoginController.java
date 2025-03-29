/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class LoginController extends Application{
    
    @Override
   public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
       
       primaryStage.setTitle("Login");
       primaryStage.setScene(new Scene(root));
       primaryStage.show();
   }
       
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
       
}
