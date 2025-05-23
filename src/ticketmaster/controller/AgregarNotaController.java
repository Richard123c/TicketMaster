/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ticketmaster.model.Ticket;
import ticketmaster.DAO.TicketDAO;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class AgregarNotaController {

     @FXML private TableView<Ticket> tablaTickets;
     @FXML private TableColumn<Ticket, String> colId;
     @FXML private TableColumn<Ticket, String> colTitulo;
     @FXML private TableColumn<Ticket, String> colEstado;
     @FXML private TextArea txtNota;
     @FXML private Label lblEstado;
     @FXML private Button btnGuardar;
     @FXML private Button btnCancelar;
     
     private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
     
     @FXML
     public void initialize() {
         tickets.setAll(TicketDAO.cargarTickets());
         
         colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
         colTitulo.setCellValueFactory(cellData -> cellData.getValue().idProperty());
         colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
         
         tablaTickets.setItems(tickets);
     }
    
     @FXML
     private void guardarNota() {
         Ticket ticketSeleccionado = tablaTickets.getSelectionModel().getSelectedItem();
         String nota = txtNota.getText();
         
         if (ticketSeleccionado == null) {
             lblEstado.setText("Debes seleccionar un ticket.");
             return;
         }
         
         if (nota.isEmpty()) {
             lblEstado.setText("La nota no puede estar vacia.");
             return;
         }
         
         boolean resultado = TicketDAO.agregarNota(ticketSeleccionado.getId(), nota);
         if (resultado) {
             lblEstado.setText("Nota agregada correctamente.");
             txtNota.clear();
         } else {
             lblEstado.setText("Ocurrio un error al agregar la nota.");
         }
     }
     
     @FXML
     private void cancelar() {
         txtNota.clear();
         lblEstado.setText("");
         tablaTickets.getSelectionModel().clearSelection();
     }
     
    @FXML
    private void volverAPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/ConfigParametros.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Parametros del Sistema");
                    stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
