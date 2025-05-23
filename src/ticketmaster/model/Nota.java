/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Lenovo
 */
public class Nota {
    private final StringProperty ticketId;
    private final StringProperty contenido;
    private final StringProperty fecha;
    private final StringProperty autor;
    private final StringProperty adjunto;

    public Nota(String ticketId, String contenido, String fecha, String autor, String adjunto) {
        this.ticketId = new SimpleStringProperty(ticketId);
        this.contenido = new SimpleStringProperty(contenido);
        this.fecha = new SimpleStringProperty(fecha);
        this.autor = new SimpleStringProperty(autor);
        this.adjunto = new SimpleStringProperty(adjunto);
    }

    public StringProperty ticketIdProperty() {
        return ticketId;
    }
    
    public StringProperty contenidoProperty() {
        return contenido;
    }
    
    public StringProperty fechaProperty() {
        return fecha;
    }
    
    public StringProperty autorProperty() {
        return autor;
    }
    
    public StringProperty adjuntoProperty() {
        return adjunto;
    }
}
