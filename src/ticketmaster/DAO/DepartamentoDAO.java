/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class DepartamentoDAO {
    public static boolean guardarDepartamento(Departamento departamento) {
        String sql = "INSERT INTO departamentos (nombre, descripcion) VALUES (?, ?)";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, departamento.getNombre());
            stmt.setString(2, departamento.getDescripcion());
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
    public static List<Departamento> obtenerDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamentos";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Departamento depto = new Departamento(
                rs.getString("nombre"),
                rs.getString("descripcion"));
                
                departamentos.add(depto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return departamentos;
    }
    
    public static boolean eliminarDepartamentos(String nombre) {
        String sql = "DELETE FROM departamentos WHERE nombre = ?";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            int filas = stmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
