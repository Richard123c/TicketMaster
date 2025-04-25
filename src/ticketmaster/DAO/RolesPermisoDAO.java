/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.Rol;
import ticketmaster.model.Permiso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class RolesPermisoDAO {
    
    public static List<Rol> obtenerRolesConPermisos() {
        List<Rol> roles = new ArrayList<>();
        String sqlRoles = "SELECT * FROM roles";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sqlRoles);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Rol rol = new Rol(rs.getString("nombre"), rs.getString("descripcion"));
                rol.getPermisos().addAll(obtenerPermisoPorRol(rs.getInt("id")));
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
    
    public static List<Permiso> obtenerTodosLosPermisos() {
        List<Permiso> permisos = new ArrayList<>();
        String sql = "SELECT * FROM permisos";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                permisos.add(new Permiso(rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permisos;
    }
    
    public static List<Permiso> obtenerPermisoPorRol(int rolId) {
        List<Permiso> permisos = new ArrayList<>();
        String sql = """
                     SELECT p.nombre, p.descripcion
                     FROM permisos p
                     JOIN roles_permisos rp ON p.id = rp.permiso_id
                     WHERE rp.rol_id = ?
                     """;
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rolId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                permisos.add(new Permiso(rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permisos;
    }
    public static boolean guardarRolConPermisos(Rol rol) {
        String insertRol = "INSERT INTO roles(nombre, descripcion) VALUES (?, ?) RETURNING id";
        String insertPermiso = "INSERT INTO roles_permiso(rol_id, permiso_id) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar()) {
            conn.setAutoCommit(false);
            
            PreparedStatement stmtRol = conn.prepareStatement(insertRol);
            stmtRol.setString(1, rol.getNombre());
            stmtRol.setString(2, rol.getDescripion());
            
            ResultSet rs = stmtRol.executeQuery();
            if (rs.next()) {
                int rolId = rs.getInt(1);
                
                for (Permiso permiso : rol.getPermisos()) {
                    int permisoId = obtenerIdPermisoPorNombre(conn, permiso.getNombre());
                    if (permisoId != -1) {
                        PreparedStatement stmtPermiso = conn.prepareStatement(insertPermiso);
                        stmtPermiso.setInt(1, rolId);
                        stmtPermiso.setInt(2, permisoId);
                        stmtPermiso.executeUpdate();
                    }
                }
                
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static int obtenerIdPermisoPorNombre(Connection conn, String nombrePermiso) throws SQLException {
        String sql = "SELECT id FROM permisos WHERE nombre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombrePermiso);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }
    
    public static boolean eliminarRol(String nombre) {
        String sql = "DELETE FROM roles WHERE nombre = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void insertarPermisosPorDefectoSiNoExisten() {
        String[] permisos = {
            "Crear tickets",
            "Ver tickets",
            "Editar tickets",
            "Eliminar tickets",
            "Asignar tickets",
            "Cambiar estado tickets",
            "Agregar notas a tickets",
            "Gestionar usuarios",
            "Gestionar departamentos",
            "Gestionar flujos de trabajo",
            "Configurar parametros del sistema"
        };
        
        String[] descripciones = {
            "Permite crear tickets de soporte",
            "Permite visualizar tickets existentes",
            "Permite editar el contenido de los tickets",
            "Permite eliminar tickets",
            "Permite asignar tickets a tecnicos",
            "Permite cambiar el estado de los tickets",
            "Permite adjuntar notas adicionales a un ticket",
            "Permite agregar, modificar y eliminar usuarios del sistema",
            "Permite administrar los departamentos de la organizacion",
            "Permite configurar pasos y reglas de los tickets",
            "Permite modificar la configuracion general del sistema"
        };
        
        String sqlBuscar = "SELECT COUNT(*) FROM permisos WHERE nombre = ?";
        String sqlInsertar = "INSERT INTO permisos (nombre, descripcion) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar()) {
            for (int i = 0; i < permisos.length; i++) {
                try (PreparedStatement buscarStmt = conn.prepareStatement(sqlBuscar)) {
                    buscarStmt.setString(1, permisos[i]);
                    ResultSet rs = buscarStmt.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    
                    if (count == 0) {
                        try (PreparedStatement insertarStmt = conn.prepareStatement(sqlInsertar)) {
                            insertarStmt.setString(1, permisos[i]);
                            insertarStmt.setString(2, descripciones[i]);
                            insertarStmt.executeUpdate();
                        }
                    }
                }
            }
            System.out.println("Permisos por defecto verificados/insertados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
