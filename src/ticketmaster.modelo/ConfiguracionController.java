/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ticketmaster.modelo.Configuracion; 
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ConfiguracionController extends Application {
    @FXML private TextField nombreEmpresaField;
    @FXML private ComboBox<String> idiomaComboBox;
    @FXML private ComboBox<String> zonaHorariaComboBox;
    @FXML private Spinner<Integer> diasVencimientoSpinner;
    @FXML private ListView<String> prioridadesListView;
    
    private Configuracion configuracion;
    
    @FXML
    public void initialize() {
        configuracion = new Configuracion();
        
        idiomaComboBox.getItems().addAll("Español", "Ingles", "Frances");
        zonaHorariaComboBox.getItems().addAll("UTC-5", "UTC-6", "UTC-7");
        diasVencimientoSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 30));
        prioridadesListView.getItems().addAll("Alta", "Media", "Baja");
    }
    
    @FXML
    private void guardar() {
        try {
            configuracion.setNombreEmpresa(nombreEmpresaField.getText());
            configuracion.setIdioma(idiomaComboBox.toString());
            configuracion.setZonaHoraria(zonaHorariaComboBox.toString());
            configuracion.setDiasVencimiento(diasVencimientoSpinner.hashCode()); 
            
            System.out.println("Configuracion guardada: " + configuracion);
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @Override
   public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("ticketmaster/modelo/configuracion.fxml"));
       
       primaryStage.setTitle("Configuracion");
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
