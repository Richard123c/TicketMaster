/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.DAO.FlujoTrabajoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ticketmaster.model.FlujoTrabajo;
import ticketmaster.model.EstadoTicket;
import ticketmaster.DAO.EstadoTicketDAO;

import java.util.*;
import javafx.beans.property.SimpleStringProperty;
/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class GestionFlujosController {

    @FXML private ListView<FlujoTrabajo> lvFlujos;
    @FXML private TextField txtNombreFlujo;
    @FXML private ListView<String> lvEstadosDisponibles;
    @FXML private ListView<String> lvEstadosDelFlujo;
    @FXML private TableView<Transicion> tablaTransiciones;
    @FXML private TableColumn<Transicion, String> colOrigen;
    @FXML private TableColumn<Transicion, String> colDestino;
    @FXML private ComboBox<String> cmbOrigen;
    @FXML private ComboBox<String> cmbDestino;
    @FXML private Label lblEstado;
    
    private ObservableList<FlujoTrabajo> listaFlujos;
    private ObservableList<String> listaEstados;
    private ObservableList<String> estadosSeleccionados;
    private ObservableList<Transicion> transiciones;
    
    @FXML
    public void initialize() {
        listaFlujos = FXCollections.observableArrayList(FlujoTrabajoDAO.obtenerFlujo());
        lvFlujos.setItems(listaFlujos);
        
        listaEstados = FXCollections.observableArrayList();
        for (EstadoTicket est : EstadoTicketDAO.obtenerEstado()) {
            listaEstados.add(est.getNombre());
        }
        lvEstadosDisponibles.setItems(listaEstados);
        
        estadosSeleccionados = FXCollections.observableArrayList();
        lvEstadosDelFlujo.setItems(estadosSeleccionados);
        
        transiciones = FXCollections.observableArrayList();
        tablaTransiciones.setItems(transiciones);
        colOrigen.setCellValueFactory(data -> data.getValue().origenProperty());
        colDestino.setCellValueFactory(data -> data.getValue().destinoProperty());
        
        lvFlujos.setOnMouseClicked(this::seleccionarFlujo);
    }
    
    @FXML
    private void agregarEstadoAlFlujo() {
        String estado = lvEstadosDisponibles.getSelectionModel().getSelectedItem();
        if (estado != null && !estadosSeleccionados.contains(estado)) {
            estadosSeleccionados.add(estado);
            cmbOrigen.getItems().add(estado);
            cmbDestino.getItems().add(estado);
        }
    }
    
    @FXML
    private void agregarTransicion() {
        String origen = cmbOrigen.getSelectionModel().getSelectedItem();
        String destino = cmbDestino.getSelectionModel().getSelectedItem();
        if (origen != null && destino != null && !origen.equals(destino)) {
            transiciones.add(new Transicion(origen, destino));
        }
    }
    
    @FXML
    private void guardarFlujo() {
        String nombre = txtNombreFlujo.getText().trim();
        if (nombre.isEmpty() || estadosSeleccionados.isEmpty()) {
            lblEstado.setText("Nombre y estados requeridos");
            return;
        }
        
        FlujoTrabajo flujo = new FlujoTrabajo(nombre);
        flujo.setEstados(new ArrayList<>(estadosSeleccionados));
        
        for (Transicion tr : transiciones) {
            flujo.agregarTransicion(tr.getOrigen().get(), tr.getDestino().get());
        }
        
        if (FlujoTrabajoDAO.guardarFlujo(flujo)) {
            listaFlujos.add(flujo);
            limpiar();
            lblEstado.setText("Flujo guardado correctamente");
        } else {
            lblEstado.setText("Error al guardar");
        }
    }
    
    @FXML
    private void eliminarFlujo() {
        FlujoTrabajo flujo = lvFlujos.getSelectionModel().getSelectedItem();
        if (flujo == null) 
        return;
        
        if (FlujoTrabajoDAO.estaEnUso(flujo.getNombre())) {
            lblEstado.setText("No se puede eliminar: esta en uso.");
            return;
        }
        
        if (FlujoTrabajoDAO.eliminarFlujo(flujo.getNombre())) {
            listaFlujos.remove(flujo);
            limpiar();
            lblEstado.setText("Eliminado correctamente");
        }
    }
    
    @FXML
    private void seleccionarFlujo(MouseEvent e) {
        FlujoTrabajo f = lvFlujos.getSelectionModel().getSelectedItem();
        if (f == null)
            return;
        
        txtNombreFlujo.setText(f.getNombre());
        txtNombreFlujo.setDisable(true);
        
        estadosSeleccionados.setAll(f.getEstados());
        cmbOrigen.getItems().setAll(estadosSeleccionados);
        cmbDestino.getItems().setAll(estadosSeleccionados);
        
        transiciones.clear();
        f.getTransiciones().forEach((origen, destinos) -> {
            for (String destino : destinos) {
                transiciones.add(new Transicion(origen, destino));
            }
        });
    }
    
    @FXML
    private void limpiar() {
        txtNombreFlujo.clear();
        txtNombreFlujo.setDisable(false);
        estadosSeleccionados.clear();
        cmbOrigen.getItems().clear();
        cmbDestino.getItems().clear();
        transiciones.clear();
        lvFlujos.getSelectionModel().clearSelection();
        lblEstado.setText("");
    }
    
    public static class Transicion {
        private final javafx.beans.property.SimpleStringProperty origen;
        private final javafx.beans.property.SimpleStringProperty destino;
        
        public Transicion(String origen, String destino) {
            this.origen = new javafx.beans.property.SimpleStringProperty(origen);
            this.destino = new javafx.beans.property.SimpleStringProperty(destino);
        }

        public SimpleStringProperty getOrigen() {
            return origen;
        }

        public SimpleStringProperty getDestino() {
            return destino;
        }
        
        public javafx.beans.property.StringProperty origenProperty() {
            return origen;
        }
        
        public javafx.beans.property.StringProperty destinoProperty() {
            return destino;
        }

    }
}
 