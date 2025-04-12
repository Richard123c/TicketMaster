/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ticketmaster.model.ConfiguracionSistema;
import ticketmaster.model.ParametroSistema;
import javafx.scene.control.Label;
import ticketmaster.model.Usuario;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ConfiguracionController {
    @FXML private TextField txtNombreEmpresa;
    @FXML private ComboBox<String> cbIdioma;
    @FXML private ComboBox<String> cbZonaHoraria;
    @FXML private Spinner<Integer> spTiempoVencimiento;
    @FXML private ListView<String> lvPrioridades;
    @FXML private Label lblMensaje;
    
    
    private ConfiguracionSistema configuracion = new ConfiguracionSistema();
    
    
    @FXML
    public void initialize() {
        //Carga nombre de la empresa
        ParametroSistema paramNombre = configuracion.getParametro("nombreEmpresa");
        if (paramNombre != null) {
            txtNombreEmpresa.setText(paramNombre.getValor());
        }
        
        //configuracion ComboBox de idioma
        cbIdioma.getItems().addAll("Español", "Ingles", "Frances");
        ParametroSistema paramIdioma = configuracion.getParametro("idioma");
        if (paramIdioma != null) {
            cbIdioma.setValue(paramIdioma.getValor());
        }
        
        // Configuracion ComboBox de zona horaria
        cbZonaHoraria.getItems().addAll("UTC-5 (Peru/Mexico)", "UTC-6 (Centroamerica)", "UTC-3 (Argentina");
        ParametroSistema paramZona = configuracion.getParametro("idioma");
        if (paramZona != null) {
            cbZonaHoraria.setValue(paramZona.getValor());
        }
        
        // Configuracion Spinner de tiempo de vencimiento
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 30);
        spTiempoVencimiento.setValueFactory(factory);
        
        ParametroSistema paramTiempo = configuracion.getParametro("tiempoVencimiento");
        if (paramTiempo != null) {
            try {
                factory.setValue(Integer.parseInt(paramTiempo.getValor()));
            } catch (NumberFormatException e) {
                factory.setValue(30);
            }
        }
        
        // Configuracion ListView de prioridades
        lvPrioridades.getItems().addAll("Alta", "Media", "Baja");
        ParametroSistema paramPrioridades = configuracion.getParametro("nivelesPrioridad");
        if (paramPrioridades != null) {
            String[] prioridades = paramPrioridades.getValor().split(",");
            lvPrioridades.getSelectionModel().selectAll();
        } 
    }
    
    @FXML
    private void guardarConfiguracion() {
        if (configuracion.actualizarParametro("nombreEmpresa", txtNombreEmpresa.getText())) {
            lblMensaje.setText("Configuracion guardado exitosamente!");
        } else {
            lblMensaje.setText("Error: Nombre invalido (3-100 caracteres)");
            return;
        }
        
        // Guarda parametros
        configuracion.actualizarParametro("idioma", cbIdioma.getValue());
        configuracion.actualizarParametro("zonaHoraria", cbZonaHoraria.getValue());
        configuracion.actualizarParametro("tiempoVencimiento", spTiempoVencimiento.getValue().toString());
        
        String prioridades = String.join(",", lvPrioridades.getSelectionModel().getSelectedItems());
        
        configuracion.actualizarParametro("nivelesPrioridad", prioridades);
        
        lblMensaje.setText("Configuracion guardada exitosamente!");
    }
}
