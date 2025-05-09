/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import ticketmaster.DAO.EstadoTicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ticketmaster.model.EstadoTicket;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class GestionEstadosController {

    @FXML private ListView<EstadoTicket> lvEstados;
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private CheckBox chkEsFinal;
    @FXML private ListView<String> lvTransiciones;
    @FXML private Label lblEstado;
    
    private ObservableList<EstadoTicket> listaEstados;
    
    @FXML
    public void initialize() {
        listaEstados = FXCollections.observableArrayList(EstadoTicketDAO.obtenerEstado());
        lvEstados.setItems(listaEstados);
        actualizarListaTransiciones();
        
        lvEstados.setOnMouseClicked(this::seleccionarEstado);
    }
    
    private void actualizarListaTransiciones() {
        lvTransiciones.setItems(FXCollections.observableArrayList(
        listaEstados.stream().map(EstadoTicket::getNombre).toList()));
    }
    
    @FXML
    private void seleccionarEstado(MouseEvent event) {
        EstadoTicket seleccionado = lvEstados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtDescripcion.setText(seleccionado.getDescripcion());
            chkEsFinal.setSelected(seleccionado.isEsFinal());
            for (int i = 0; i < lvTransiciones.getItems().size(); i++) {
                String estado = lvTransiciones.getItems().get(i);
                lvTransiciones.getSelectionModel().selectIndices(seleccionado.getSiguientesEstados().contains(estado) ? i : -1);
            }
            txtNombre.setDisable(true);
        }
    }
    
    @FXML
    private void guardarEstado() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        boolean esFinal = chkEsFinal.isSelected();
        
        if (nombre.length() < 3 || nombre.length() > 50) {
            lblEstado.setText("El nombre debe tener entre 3 y 50 caracteres.");
            return;
        }
        
        EstadoTicket nuevo = new EstadoTicket(nombre, descripcion, esFinal);
        nuevo.setSiguientesEstados(lvTransiciones.getSelectionModel().getSelectedItems());
        
        if (EstadoTicketDAO.guardarEstado(nuevo)) {
            listaEstados.add(nuevo);
            actualizarListaTransiciones();
            limpiarFormulario();
            lblEstado.setText("Estado guardado correctamente.");
        } else {
            lblEstado.setText("Error al guardar el estado (¿ya existe?).");
        }
    }
    
    @FXML
    private void modificarEstado() {
        EstadoTicket seleccionado = lvEstados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblEstado.setText("Slecciona un estado para modificar.");
            return;
        } 
        
        seleccionado.setDescripcion(txtDescripcion.getText().trim());
        seleccionado.setEsFinal(chkEsFinal.isSelected());
        seleccionado.setSiguientesEstados(lvTransiciones.getSelectionModel().getSelectedItems());
        
        if (EstadoTicketDAO.actualizarEstado(seleccionado)) {
            lvEstados.refresh();
            lblEstado.setText("Estado actualizado.");
        } else {
            lblEstado.setText("Error al actualizar.");
        }
    }
    
    @FXML
    private void eliminarEstado() {
        EstadoTicket seleccionado = lvEstados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblEstado.setText("Selecciona un estado para eliminar.");
            return;
        }
        
        if (EstadoTicketDAO.estaEnUso(seleccionado.getNombre())) {
            lblEstado.setText("No se puede eliminar un estado en uso.");
            return;
        }
        
        if (EstadoTicketDAO.eliminarEstado(seleccionado.getNombre())) {
            listaEstados.remove(seleccionado);
            actualizarListaTransiciones();
            limpiarFormulario();
            lblEstado.setText("Estado eliminado.");
        } else {
            lblEstado.setText("Error al eliminar.");
        }
    }
    
    @FXML
    private void limpiarFormulario() {
        txtNombre.clear();
        txtDescripcion.clear();
        chkEsFinal.setSelected(false);
        lvTransiciones.getSelectionModel().clearSelection();
        lvEstados.getSelectionModel().clearSelection();
        txtNombre.setDisable(false);
        lblEstado.setText("");
    }
}

