/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import java.io.*;
import ticketmaster.model.Ticket;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
/**
 *
 * @author Lenovo
 */
public class TicketDAO {
    private static final String RUTA_ARCHIVO = "tickets.txt";
    
    public static boolean guardarTicket(List<Ticket> tickets) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            for (Ticket t : tickets) {
            writer.write(String.join("|",
                    t.getId(),
                    t.getTitulo(),
                    t.getDescripcion(),
                    t.getDepartamento(),
                    t.getPrioridad(),
                    t.getEstado(),
                    t.getFechaCreacion(),
                    t.getTecnicoAsignado(),
                    t.getAdjunto()
            ));
            writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Ticket> cargarTickets() {
        List<Ticket> tickets = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        
        if (!archivo.exists()) return tickets;
        
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 9) {
                    Ticket ticket = new Ticket(
                    partes[0], partes[1], partes[2], partes[3], partes[4], 
                            partes[5], partes[6], partes[7], partes[8]
                    );
                    tickets.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    
    public static List<Ticket> obtenerTicketsPendientes() {
        List<Ticket> ticketsPendientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 6 && partes[5].equalsIgnoreCase("Pendiente")) {
                    Ticket t = new Ticket(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7], partes.length > 8 ? partes[8] : "");
                    ticketsPendientes.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketsPendientes;
    }
    
    public static boolean actualizarEstado(String idTicket, String nuevoEstado, String comentario) {
        List<Ticket> tickets = cargarTickets();
        
        for (Ticket t : tickets) {
            if (t.getId().equals(idTicket)) {
                t.setEstado(nuevoEstado);
                break;
            }
        }
        return guardarTicket(tickets);
    }
    
    public static boolean agregarNota(String idTicket, String nota) {
        String rutaNotas = "notas_tickets.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaNotas, true))) {
            String lineaNota = idTicket + "|" + LocalDateTime.now() + "|" + nota;
            writer.write(lineaNota);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
