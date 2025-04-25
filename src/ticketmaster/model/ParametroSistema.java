/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

/**
 *
 * @author Lenovo
 */
   public class ParametroSistema {
    private String nombreEmpresa;
    private byte[] logo;
    private String idioma;
    private String zonaHoraria;
    private int diasVencimientoTickets;
    private String prioridadAlta;
    private String prioridadMedia;
    private String prioridadBaja;
    
    public ParametroSistema() {
        
    }

    public ParametroSistema(String nombreEmpresa, byte[] logo, String idioma, String zonaHoraria, int diasVencimientoTickets, String prioridadAlta, String prioridadMedia, String prioridadBaja) {
        this.nombreEmpresa = nombreEmpresa;
        this.logo = logo;
        this.idioma = idioma;
        this.zonaHoraria = zonaHoraria;
        this.diasVencimientoTickets = diasVencimientoTickets;
        this.prioridadAlta = prioridadAlta;
        this.prioridadMedia = prioridadMedia;
        this.prioridadBaja = prioridadBaja;
    }

    

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public int getDiasVencimientoTickets() {
        return diasVencimientoTickets;
    }

    public void setDiasVencimientoTickets(int diasVencimientoTickets) {
        this.diasVencimientoTickets = diasVencimientoTickets;
    }

    public String getPrioridadAlta() {
        return prioridadAlta;
    }

    public void setPrioridadAlta(String prioridadAlta) {
        this.prioridadAlta = prioridadAlta;
    }

    public String getPrioridadMedia() {
        return prioridadMedia;
    }

    public void setPrioridadMedia(String prioridadMedia) {
        this.prioridadMedia = prioridadMedia;
    }

    public String getPrioridadBaja() {
        return prioridadBaja;
    }

    public void setPrioridadBaja(String prioridadBaja) {
        this.prioridadBaja = prioridadBaja;
    }
    
    
}