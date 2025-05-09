/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ticketmaster.model.Ticket;
import ticketmaster.DAO.TicketDAO;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class CambiarEstadoController {

     @FXML private TableView<Ticket> tablaTickets;
     @FXML private TableColumn<Ticket, String> colId, colTitulo, colEstadoActual, colDepartamento, colTecnico;
     @FXML private ComboBox<String> cmbNuevoEstado;
     @FXML private TextArea txtComentario;
     @FXML private Button btnGuardar, btnCancelar;
     @FXML private Label lblEstado;
     
     private ObservableList<Ticket> listaTickets;
     
     @FXML
     public void initialize() {
         colId.setCellValueFactory(data -> data.getValue().idProperty());
         colTitulo.setCellValueFactory(data -> data.getValue().tituloProperty());
         colEstadoActual.setCellValueFactory(data -> data.getValue().estadoProperty());
         colDepartamento.setCellValueFactory(data -> data.getValue().departamentoProperty());
         colTecnico.setCellValueFactory(data -> data.getValue().tecnicoAsignadoProperty());
         
         cmbNuevoEstado.setItems(FXCollections.observableArrayList("En proceso", "Resuelto", "Cerrado", "Reabierto"));
         
         listaTickets = FXCollections.observableArrayList(TicketDAO.obtenerTicketsPendientes());
         tablaTickets.setItems(listaTickets);
     }
    
     @FXML
     public void guardarCambioEstado() {
         Ticket ticketSeleccionado = tablaTickets.getSelectionModel().getSelectedItem();
         String nuevoEstado = cmbNuevoEstado.getValue();
         String comentario = txtComentario.getText();
         
         if (ticketSeleccionado == null || nuevoEstado == null || nuevoEstado.isEmpty()) {
             lblEstado.setText("Debe seleccionar un ticket y un nuevo estado.");
             return;
         }
         
         boolean exito = TicketDAO.actualizarEstado(ticketSeleccionado.getId(), nuevoEstado, comentario);
         if (exito) {
             ticketSeleccionado.setEstado(nuevoEstado);
             tablaTickets.refresh();
             lblEstado.setText("Estado actualizado correctamente.");
         } else {
             lblEstado.setText("Error al actualizar el estado.");
         }
     }
     
     @FXML
     private void cancelar() {
         cmbNuevoEstado.getSelectionModel().clearSelection();
         txtComentario.clear();
         lblEstado.setText("");
         tablaTickets.getSelectionModel().clearSelection();
     }
}
