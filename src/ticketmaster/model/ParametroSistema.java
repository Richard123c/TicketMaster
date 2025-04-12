/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

/**
 *
 * @author Lenovo
 */
   public abstract class ParametroSistema {
    private String nombre;
    private String valor;

    public ParametroSistema(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public abstract boolean validar();
}

 class ParametroTexto extends ParametroSistema{
    private int minCaracteres;
    private int maxCaracteres;

    public ParametroTexto(String nombre, String valor, int min, int max) {
        super(nombre, valor);
        this.minCaracteres = minCaracteres;
        this.maxCaracteres = maxCaracteres;
    }
    
    @Override
    public boolean validar() {
        return getValor() != null &&
                getValor().length() >= minCaracteres &&
                getValor().length() <= maxCaracteres;
    }
}

 class ParametroNumerico extends ParametroSistema {
    private int min;
    private int max;
    
    public ParametroNumerico(String nombre, String valor, int min, int max) {
        super(nombre, valor);
        this.min = min;
        this.max = max;
    }
    
    @Override
    public boolean validar() {
        try {
            int num = Integer.parseInt(getValor());
            return num >= min && num <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}