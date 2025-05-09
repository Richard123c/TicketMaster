/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import ticketmaster.DAO.TicketDAO;
import ticketmaster.model.Ticket;
import java.io.File;
import java.util.Collections;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class TicketController {

    @FXML
    private TextField campoTitulo;
    
    @FXML
    private TextArea campoDescripcion;
    
    @FXML
    private ComboBox<String> comboDepartamento;
    
    @FXML
    private ComboBox<String> comboPrioridad;
    
    @FXML
    private TextField campoAdjunto;
    
    @FXML
    private Label mensajeEstado;
    
    @FXML
    public void initialize() {
        comboDepartamento.getItems().addAll("Soporte Tecnico", "Recursos Humanos", "Contabilidad", "Desarrollo");
        comboPrioridad.getItems().addAll("Baja", "Media", "Alta");
    }
    
    @FXML
    private void seleccionarAdjunto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo adjunto");
        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            campoAdjunto.setText(archivo.getAbsolutePath());
        }
    }
    
    @FXML
    private void crearTicket() {
        String titulo = campoTitulo.getText().trim();
        String descripcion = campoDescripcion.getText().trim();
        String departamento = comboDepartamento.getValue();
        String prioridad = comboPrioridad.getValue();
        String adjunto = campoAdjunto.getText().trim();
        
        if (titulo.isEmpty() || descripcion.isEmpty() || departamento == null || prioridad == null) {
            mensajeEstado.setText("Complete todos los campos obligatorios.");
            return;
        }
        
        Ticket ticket = new Ticket(titulo, descripcion, departamento, prioridad, adjunto);
        
        if (TicketDAO.guardarTicket(Collections.singletonList(ticket))) {
            mensajeEstado.setText("Ticket creado con exito.");
            limpiarFormulario();
        } else {
            mensajeEstado.setText("Error al guardar el ticket.");
        } 
    }
    
    private void limpiarFormulario() {
        campoTitulo.clear();
        campoDescripcion.clear();
        comboDepartamento.getSelectionModel().clearSelection();
        comboPrioridad.getSelectionModel().clearSelection();
        campoAdjunto.clear();
    }
}
