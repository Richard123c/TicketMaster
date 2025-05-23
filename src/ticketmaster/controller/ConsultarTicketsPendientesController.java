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

import java.util.List;
import java.util.stream.Collectors;

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
public class ConsultarTicketsPendientesController  {
    
    @FXML private TableView<Ticket> tablaTickets;
    @FXML private TableColumn<Ticket, String> colTitulo;
    @FXML private TableColumn<Ticket, String> colPrioridad;
    @FXML private TableColumn<Ticket, String> colFecha;
    @FXML private TableColumn<Ticket, String> colDepartamento;
    @FXML private TableColumn<Ticket, String> colEstado;
    
    @FXML private TextField txtBuscar;
    @FXML private ComboBox<String> cbFiltroPrioridad;
    @FXML private ComboBox<String> cbFiltroDepartamento;
    
    private ObservableList<Ticket> listaOriginal;
    private ObservableList<Ticket> listaFiltrada;
    
    @FXML
    public void initialize() {
        //Inicializa las columnas
        colTitulo.setCellValueFactory(data -> data.getValue().tituloProperty());
        colPrioridad.setCellValueFactory(data -> data.getValue().prioridadProperty());
        colFecha.setCellValueFactory(data -> data.getValue().fechaCreacionProperty());
        colDepartamento.setCellValueFactory(data -> data.getValue().departamentoProperty());
        colEstado.setCellValueFactory(data -> data.getValue().estadoProperty());
        
        //Carga los tickets pendientes desde DAO
        List<Ticket> tickets = TicketDAO.obtenerTicketsPendientes();
        listaOriginal = FXCollections.observableArrayList(tickets);
        listaFiltrada = FXCollections.observableArrayList(tickets);
        tablaTickets.setItems(listaFiltrada);
        
        //Cargar filtros
        cbFiltroPrioridad.getItems().addAll("Alta", "Media", "Baja");
        cbFiltroDepartamento.getItems().addAll(obtenerDepartamentosUnicos(tickets));
        
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrar());
        cbFiltroPrioridad.setOnAction(e -> filtrar());
        cbFiltroDepartamento.setOnAction(e -> filtrar());
        
    }
    
    private void filtrar() {
        String keyword = txtBuscar.getText().toLowerCase();
        String prioridad = cbFiltroPrioridad.getValue();
        String departamento = cbFiltroDepartamento.getValue();
        
        listaFiltrada.setAll(listaOriginal.stream()
        .filter(t -> (t.getTitulo().toLowerCase().contains(keyword) || t.getDescripcion().toLowerCase().contains(keyword)))
        .filter(t -> (prioridad == null || t.getPrioridad().equals(prioridad)))
        .filter(t -> (departamento == null || t.getDepartamento().equals(departamento)))
        .collect(Collectors.toList()));
    }
    
    private List<String> obtenerDepartamentosUnicos(List<Ticket> tickets) {
        return tickets.stream()
                .map(Ticket::getDepartamento)
                .distinct()
                .collect(Collectors.toList());
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
