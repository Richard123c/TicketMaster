/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import ticketmaster.model.Departamento;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class GestionDepartamentosController {
    
      @FXML private ListView<Departamento> lvDepartamentos;
      @FXML private TextField txtNombreDepartamento;
      @FXML private TextArea txtDescripcionDepartamento;
      @FXML private ListView<String> lvTecnicosDisponibles;
      @FXML private ListView<String> lvTecnicosAsignados;
      @FXML private Button btnAsignarTecnico;
      @FXML private Button btnQuitarTecnico;
      @FXML private Button btnGuardarDepartamento;
      @FXML private Button btnEliminarDepartamento;
      @FXML private Label lblEstado;
      
      private final DepartamentoController gestor = new DepartamentoController();
      private final ObservableList<Departamento> departamentosObservable = FXCollections.observableArrayList();
      private final ObservableList<String> tecnicosDisponibles = FXCollections.observableArrayList();
      private final ObservableList<String> tecnicosAsignados = FXCollections.observableArrayList();
      
      @FXML
      public void initialize() {
          //Carga los tecnicos disponibles
          tecnicosDisponibles.addAll("Giovanni", "Pablo", "Yeimy", "Steven", "Ricky");
          
          lvDepartamentos.setItems(departamentosObservable);
          lvTecnicosDisponibles.setItems(tecnicosDisponibles);
          lvTecnicosAsignados.setItems(tecnicosAsignados);
          
          lvDepartamentos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> cargarDepartamentoSeleccionado(newVal));
      }
      
      private void cargarDepartamentoSeleccionado(Departamento depto) {
          if (depto != null) {
              txtNombreDepartamento.setText(depto.getNombre());
              txtDescripcionDepartamento.setText(depto.getDescripcion());
              tecnicosAsignados.setAll(depto.getTecnicosAsignados());
          }
      }
      
      @FXML
      private void asignarTecnico() {
          String tecnico = lvTecnicosDisponibles.getSelectionModel().getSelectedItem();
          if (tecnico != null && !tecnicosAsignados.contains(tecnico)) {
              tecnicosAsignados.add(tecnico);
          }
      }
      
      @FXML
      private void quitarTecnico() {
          String tecnico = lvTecnicosAsignados.getSelectionModel().getSelectedItem();
          if (tecnico != null) {
              tecnicosAsignados.remove(tecnico);
          }
      }
      
      @FXML
      private void guardarDepartamento() {
          String nombre = txtNombreDepartamento.getText().trim();
          String descripcion = txtDescripcionDepartamento.getText().trim();
          
          if (nombre.isEmpty() || nombre.length() < 3 || nombre.length() > 50) {
              lblEstado.setText("Nombre del departamento requerido (3-50 caracteres).");
              return;
          }
          
          Departamento departamento = new Departamento(nombre, descripcion);
          departamento.getTecnicosAsignados().addAll(tecnicosAsignados);
          
          gestor.agregarDepartamento(departamento);
          departamentosObservable.setAll(gestor.getListaDepartamentos());
          lblEstado.setText("Departamento guardado correctamente.");
          limpiarCampos();
      }
    
      @FXML
      private void eliminarDepartamento() {
          Departamento depto = lvDepartamentos.getSelectionModel().getSelectedItem();
          if (depto != null) {
              gestor.eliminarDepartamento(depto);
              departamentosObservable.setAll(gestor.getListaDepartamentos());
              lblEstado.setText("Departamento eliminado.");
              limpiarCampos();
          }
      }
      
      private void limpiarCampos() {
          txtNombreDepartamento.clear();
          txtDescripcionDepartamento.clear();
          tecnicosAsignados.clear(); 
          lvDepartamentos.getSelectionModel().clearSelection();
      }
}
