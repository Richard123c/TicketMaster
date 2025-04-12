/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.dao.queries;

/**
 *
 * @author Lenovo
 */
public final class RolQueries {
    public static final String INSERT_ROL =
            "INSERT INTO roles (nombre, descripcion) VALUES (?, ?)";
    
    public static final String LISTAR_ROLES =
            "SELECT id, nombre, descripcion FROM roles";
    
    public static final String ASIGNAR_PERMISO =
            "INSERT INTO roles_permisos (rol_id, permiso_id) VALUES (?, ?)";
}
