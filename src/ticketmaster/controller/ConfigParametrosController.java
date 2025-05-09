/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticketmaster.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import ticketmaster.model.ParametroSistema;
import ticketmaster.DAO.ParametrosDAO;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ConfigParametrosController {

    @FXML private TextField txtNombreEmpresa;
    @FXML private ComboBox<String> cmbIdioma;
    @FXML private ComboBox<String> cmbZonaHoraria;
    @FXML private Spinner<Integer> spnVencimiento;
    @FXML private TextField txtPrioridadAlta;
    @FXML private TextField txtPrioridadMedia;
    @FXML private TextField txtPrioridadBaja;
    @FXML private ImageView imglogo;
    @FXML private Label lblError;
    
    private byte[] logoBytes = null;
    
    @FXML
    public void initialize() {
        cmbIdioma.getItems().addAll("Español", "Ingles");
        cmbZonaHoraria.getItems().addAll("GMT-3", "GMT-5", "UTC", "GMT+1", "GMT+2");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 30);
        spnVencimiento.setValueFactory(valueFactory);
        
        cargarParametros();
    }
    
    private void cargarParametros() {
        ParametroSistema p = ParametrosDAO.obtenerParametros();
        if (p != null) {
            txtNombreEmpresa.setText(p.getNombreEmpresa());
            cmbIdioma.setValue(p.getIdioma());
            cmbZonaHoraria.setValue(p.getZonaHoraria());
            spnVencimiento.getValueFactory().setValue(p.getDiasVencimientoTickets());
            txtPrioridadAlta.setText(p.getPrioridadAlta());
            txtPrioridadMedia.setText(p.getPrioridadMedia());
            txtPrioridadBaja.setText(p.getPrioridadBaja());
            
            logoBytes = p.getLogo();
            if (logoBytes != null) {
                imglogo.setImage(new Image(new java.io.ByteArrayInputStream(logoBytes)));
            }
        }
    }
    
    @FXML
    private void cargarLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Imagenes JPG Y PNG", "*.jpg", "*.png"));
        
        File file = fileChooser.showOpenDialog(null);
        if (file != null && file.length() <= 2 * 1024 * 1024) {
            try {
                logoBytes = Files.readAllBytes(file.toPath());
                imglogo.setImage(new Image(file.toURI().toString()));
            } catch (IOException e) {
                lblError.setText("Error al cargar la imagen.");
            }
        } else {
            lblError.setText("Archivo invalido o muy grande (max. 2MB)");
        }
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        lblError.setText("");
        
        if (!validar()) return;
        
        ParametroSistema p = new ParametroSistema();
        p.setNombreEmpresa(txtNombreEmpresa.getText());
        p.setIdioma(cmbIdioma.getValue());
        p.setZonaHoraria(cmbZonaHoraria.getValue());
        p.setDiasVencimientoTickets(spnVencimiento.getValue());
        p.setPrioridadAlta(txtPrioridadAlta.getText());
        p.setPrioridadMedia(txtPrioridadMedia.getText());
        p.setPrioridadBaja(txtPrioridadBaja.getText());
        p.setLogo(logoBytes);
        
        boolean exito = ParametrosDAO.guardarParametros (p, "admin");
        
        if (exito) {
            lblError.setText("¡Parametros guardados con exito!"); 
        } else {
            lblError.setText("Error al guardar en la base de datos");
        }
    }
    
    private boolean validar() {
        if (txtNombreEmpresa.getText().length() < 3) {
            lblError.setText("El nombre de la empresa debe tener al menos 3 caracteres.");
            return false;
        }
        if (cmbIdioma.getValue() == null || cmbZonaHoraria.getValue() == null) {
            lblError.setText("Seleccione idioma y zona horaria.");
            return false;
        }
        if (txtPrioridadAlta.getText().isEmpty() || txtPrioridadMedia.getText().isEmpty() || txtPrioridadBaja.getText().isEmpty()) {
            lblError.setText("Debe ingresar las tres prioridades.");
            return false;
        }
        return true;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        cargarParametros();
        lblError.setText("Cambios cancelados.");
    }
    
    @FXML
    private void abrirGestionRolesPermisos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GestionRolesPermisos.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion de Roles y Permisos");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionDepartamentos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GestionDepartamentos.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion de Departamentos");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GestionUsuarios.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion de Usuarios");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionEstados(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GestionEstados.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion de Estados de Tickets");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionFlujos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/GestionFlujos.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion de Flujos de Trabajo");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirGestionTickets(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/Ticket.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Gestion Tickets");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirConsultasTicketsPendientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/ConsultarTicketsPendientes.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Consultar tickets pendientes");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirCambioEstadoTicket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/CambiarEstado.fxml"));
            Parent root = loader.load();
            
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("Cambiar estados tickets");
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();
            
            Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventanaActual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
