/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class UsuarioDAO {
    public static boolean guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios_iniciales (nombre_completo, correo, nombre_usuario, contraseña, rol, departamento, activo)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getNombreUsuario());
            stmt.setString(4, usuario.getContraseña());
            stmt.setString(5, usuario.getRol());
            stmt.setString(6, usuario.getDepartamento());
            stmt.setBoolean(7, usuario.isActivo());
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }  
    
    public static List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios_iniciales";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                rs.getString("nombre_completo"),
                rs.getString("correo"),
                rs.getString("nombre_usuario"),
                rs.getString("contraseña"),
                rs.getString("rol"),
                rs.getString("departamento"));
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    public static boolean eliminarUsuario(String nombreUsuario) {
        String sql = "DELETE FROM usuarios WHERE nombre_usuario = ?";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombreUsuario);
            int filas = stmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios_iniciales SET nombre_completo = ?, correo = ?, contraseña = ?, rol = ?, departamento = ?, activo = ? WHERE nombre_usuario = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getDepartamento());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setString(7, usuario.getNombreUsuario());
            
            int filas = stmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
