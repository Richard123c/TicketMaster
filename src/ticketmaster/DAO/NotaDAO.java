/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.model.Nota;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class NotaDAO {
    private static final String RUTA = "notas.txt";
    
    private static boolean guardarNota(Nota nota) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA, true))) {
            writer.write(String.join("|",
                    nota.ticketIdProperty().get(),
                    nota.contenidoProperty().get(),
                    nota.fechaProperty().get(),
                    nota.autorProperty().get(),
                    nota.adjuntoProperty().get()));
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static List<Nota> cargarNotasPorTicket(String ticketId) {
        List<Nota> notas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 5 && partes[0].equals(ticketId)) {
                    notas.add(new Nota(partes[0], partes[1], partes[2], partes[3], partes[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notas;
    }
}
