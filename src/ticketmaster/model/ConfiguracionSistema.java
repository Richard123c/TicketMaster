/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.model;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class ConfiguracionSistema {
    private Map<String, ParametroSistema> parametros;
    
    public ConfiguracionSistema() {
        parametros = new HashMap<>();
        cargarParametrosPorDefecto();
    }
    
    private void cargarParametrosPorDefecto() {
        parametros.put("nombreEmpresa", new ParametroTexto("Nombre Empresa", "", 3, 100));
        parametros.put("idioma", new ParametroTexto("Idioma", "Español", 1, 20));
        parametros.put("zonaHoraria", new ParametroTexto("Zona Horaria", "", 1, 20));
        parametros.put("tiempoVencimiento", new ParametroNumerico("Tiempo Vencimiento", "30", 1, 365));
        parametros.put("nivelesPrioridad", new ParametroTexto("Prioridades", "Alta,Media,Baja", 1, 100));
    }
    
    public boolean actualizarParametro(String clave, String valor) {
        ParametroSistema param = parametros.get(clave);
        if (param != null) {
            param.setValor(valor);
            return param.validar();
        }
        return false;
    }
    
    public ParametroSistema getParametro(String clave) {
        return parametros.get(clave);
    }
}
