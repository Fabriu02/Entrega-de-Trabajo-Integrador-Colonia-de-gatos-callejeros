/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */
import dao.VisitaDAO;
import dao.UsuarioDAO; 
import dao.HogarDAO; 
import modelo.*; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;


public class Gestion_visitas {

    private VisitaDAO visitaDAO;
    private UsuarioDAO usuarioDAO;
    private HogarDAO hogarDAO;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Gestion_visitas(VisitaDAO visitaDAO, UsuarioDAO usuarioDAO, HogarDAO hogarDAO) {
        this.visitaDAO = visitaDAO;
        this.usuarioDAO = usuarioDAO;
        this.hogarDAO = hogarDAO;
    }

    //CRUD VISITAS 
    
    public void registrarVisita(Voluntario voluntario, Hogar_Adopcion hogar, String fechaStr, String observacion ) throws Exception { 
        if (voluntario == null) throw new Exception("Error: No se encontro el voluntario.");
        if (hogar == null) throw new Exception("Error: No se encontro el hogar.");
        if (fechaStr == null || fechaStr.trim().isEmpty()) throw new Exception("La fecha no puede estar vacia.");

        LocalDateTime fechaVisita;
        try {
            fechaVisita = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new Exception("Error: Formato de fecha invalido. Use dd/MM/yyyy HH:mm");
        }
        Visita nuevaVisita = new Visita(fechaVisita, observacion, voluntario, hogar);
        visitaDAO.guardar(nuevaVisita);
        
    }
    public void modificarVisita(Visita visita, Voluntario voluntario, Hogar_Adopcion hogar, String fechaStr, String observacion) throws Exception {
        if (visita == null) {
            throw new Exception("Error: No se encontró la visita a modificar.");
        }
            if (voluntario == null) {
                throw new Exception("Debe seleccionar un voluntario.");
            }
                if (hogar == null) {
                    throw new Exception("Debe seleccionar un hogar.");
                }
                    if (fechaStr == null || fechaStr.trim().isEmpty()) {
                        throw new Exception("La fecha no puede estar vacía.");
                    }
        LocalDateTime fechaVisita;
        try {
            fechaVisita = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new Exception("Error: Formato de fecha invalido. Use dd/MM/yyyy HH:mm");
        }
        visita.setVoluntario(voluntario);
        visita.setAdopcion(hogar);
        visita.setFechahora(fechaVisita);
        visita.setObservacion(observacion);
         visita.setCumplida(false); // si es modificada se resetea a falso ( lo veo lo mas logico)
        visitaDAO.actualizar(visita);
        
    }
    public List<Visita> listarVisitas() {
        return visitaDAO.listarTodas();
    }
    public Visita buscarVisitaPorId(int idVisita) {
        return visitaDAO.buscarPorId(idVisita);
    }
   public void borrarVisita(Visita visita) throws Exception {
        if (visita == null) {
            throw new Exception("No se ha seleccionado una visita valida.");
        }
        visitaDAO.eliminar(visita);
    }
    
    public void validarHogar(Hogar_Adopcion hogar) throws Exception {
        if (hogar == null) {
            throw new Exception("No se encontro un hogar seleccionado");
        }
            if (hogar.getFamilia() == null) {
                 throw new Exception("Error: El hogar no tiene una familia asociada.");
            }
                List<Visita> visitasDelHogar = visitaDAO.buscarPorHogar(hogar);
                int contadorVisitas = visitasDelHogar.size();
                int reputacion = hogar.getFamilia().getReputacion(); 
                if (contadorVisitas >= 3) { 
                    if (reputacion >= 3) { 
                        hogar.aprobado_adopcion(); 
                        hogarDAO.actualizar(hogar);
                    } else if (reputacion >= 0) {
                        throw new Exception("La reputación no es suficiente. Se requieren más visitas o mejor desempeño."); 
                    }
                        else {
                            throw new Exception("No apto para adopción debido a reputación negativa.");
                        }
                } 
                 else {
                   throw new Exception("Aún no cumple el mínimo de 3 visitas. Visitas actuales: " + contadorVisitas);
                 }
    }
    

    // Evaluar reputaciones -> admin
    public void evaluarVisita(Visita visita, boolean fueCumplida) throws Exception {
       if (visita == null) throw new Exception("Visita no encontrada.");
        if (visita.getVoluntario() == null || visita.getAdopcion() == null || visita.getAdopcion().getFamilia() == null) {
             throw new Exception("Datos incompletos para evaluar esta visita.");
        }

        Voluntario reputacionVol = visita.getVoluntario();
        Familia reputacionFam = visita.getAdopcion().getFamilia();

        if (fueCumplida) {
            visita.visitaCumplida(); 
            if (visita.getFechahora() != null && LocalDateTime.now().isBefore(visita.getFechahora())) {
                reputacionVol.aumentarReputacion(1);
                reputacionFam.aumentarReputacion(1);
            } else {
                reputacionVol.disminuirReputacion(1); 
                reputacionFam.disminuirReputacion(1); 
            }
        } else {
            visita.setCumplida(false); //por las dudas
            reputacionVol.disminuirReputacion(2); 
            reputacionFam.disminuirReputacion(2); 
        }
        visitaDAO.actualizar(visita);
        usuarioDAO.actualizar(reputacionVol);
        usuarioDAO.actualizar(reputacionFam);
    }
     public List<String> mostrarAlertasVisitas() {
        List<String> alertas = new ArrayList<>();
        List<Visita> todas = visitaDAO.listarTodas();
        
        for (Visita v : todas) {
            if (v.requiereAlerta()) {
                String alerta = String.format("ALERTA DE VISITA (ID: %d)\n Fecha: %s\n Evento: %s\n Estado: %s",
                    v.getCodvisita(),
                    v.getFecha().format(FORMATO_FECHA),
                    v.getDescripcion(),
                    (v.estaVencido() ? "**VENCIDA**" : "Proxima a vencer")
                );
                alertas.add(alerta);
            }
        }
        return alertas;
    }
     public List<String> mostrarAlertasVisitas(Voluntario voluntario) { //muestra las visitas por voluntario, para separar de lo que viene siendo la busqueda del admin
        List<String> alertas = new ArrayList<>();
        List<Visita> susVisitas = visitaDAO.buscarPorVoluntario(voluntario); 
        for (Visita v : susVisitas) {
            if (v.requiereAlerta()) { 
                String alerta = String.format("ALERTA DE VISITA (ID: %d)\n Fecha: %s\n Evento: %s\n Estado: %s",
                    v.getCodvisita(),
                    v.getFecha().format(FORMATO_FECHA),
                    v.getDescripcion(),
                    (v.estaVencido() ? "**VENCIDA**" : "Proxima a vencer")
                );
                alertas.add(alerta);
            }
        }
        return alertas;
    }
     public List<Hogar_Adopcion> listarHogares() {
        return hogarDAO.listarTodos();
    }
}
            
        

