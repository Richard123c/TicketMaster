/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import java.time.LocalDate;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Lenovo
 */
public class Ticket {
    private final StringProperty id;
    private final StringProperty titulo;
    private final StringProperty descripcion;
    private final StringProperty departamento;
    private final StringProperty prioridad;
    private final StringProperty estado;
    private final StringProperty tecnicoAsignado;
    private final StringProperty fechaCreacion;
    private final StringProperty adjunto;

    public Ticket(String titulo, String descripcion, String departamento, String prioridad, String adjunto) {
        this.id = new SimpleStringProperty(UUID.randomUUID().toString());
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.departamento = new SimpleStringProperty(departamento);
        this.prioridad = new SimpleStringProperty(prioridad);
        this.estado = new SimpleStringProperty("Abierto");
        this.tecnicoAsignado = new SimpleStringProperty("");
        this.fechaCreacion = new SimpleStringProperty(LocalDate.now().toString());
        this.adjunto = new SimpleStringProperty(adjunto);
    }
    
    
    

    public Ticket(String id, String titulo, String descripcion, String prioridad, String estado, String fechaCreacion, String departamento, String tecnicoAsignado, String adjunto) {
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.departamento = new SimpleStringProperty(departamento);
        this.prioridad = new SimpleStringProperty(prioridad);
        this.estado = new SimpleStringProperty(estado);
        this.tecnicoAsignado = new SimpleStringProperty(tecnicoAsignado);
        this.fechaCreacion = new SimpleStringProperty(fechaCreacion);
        this.adjunto = new SimpleStringProperty(adjunto);
    }

    

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getDepartamento() {
        return departamento.get();
    }

    public void setDepartamento(String departamento) {
        this.departamento.set(departamento);
    }

    public String getPrioridad() {
        return prioridad.get();
    }

    public void setPrioridad(String prioridad) {
        this.prioridad.set(prioridad);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado.get();
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado.set(tecnicoAsignado);
    }

    public String getFechaCreacion() {
        return fechaCreacion.get();
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    public String getAdjunto() {
        return adjunto.get();
    }

    public void setAdjunto(String adjunto) {
        this.adjunto.set(adjunto);
    }
    
    public StringProperty idProperty() {
        return id;
    }
    
    public StringProperty tituloProperty() {
        return titulo;
    }
    
    public StringProperty descripcionProperty() {
        return descripcion;
    }
    
    public StringProperty departamentoProperty() {
        return departamento;
    }
    
    public StringProperty prioridadProperty() {
        return prioridad;
    }
    
    public StringProperty estadoProperty() {
        return estado;
    }
    
    public StringProperty tecnicoAsignadoProperty() {
        return tecnicoAsignado;
    }
    
    public StringProperty fechaCreacionProperty() {
        return fechaCreacion;
    }
}
