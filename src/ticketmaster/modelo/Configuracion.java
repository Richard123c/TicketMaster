/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.modelo;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class Configuracion {
    private String nombreEmpresa;
    private String rutaLogo;
    private String idioma;
    private String zonaHoraria;
    private int diasVencimiento;
    private List<String> nivelesPrioridad;
    
    public Configuracion() {
        this.nivelesPrioridad = new ArrayList<>();
    }
    
    public String getNombreEmpresa() { 
        return nombreEmpresa; 
    }
    
    public void setNombreEmpresa(String nombreEmpresa) {
        if(nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        if(rutaLogo == null || rutaLogo.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede estar vacio");
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        if(idioma == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un idioma");
        }
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        if(zonaHoraria == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar la zona horaria");
        }
    }

    public int getDiasVencimiento() {
        return diasVencimiento;
    }

    public void setDiasVencimiento(int diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    public List<String> getNivelesPrioridad() {
        return nivelesPrioridad;
    }

    public void setNivelesPrioridad(List<String> nivelesPrioridad) {
        this.nivelesPrioridad = nivelesPrioridad;
    }
    
    
}
