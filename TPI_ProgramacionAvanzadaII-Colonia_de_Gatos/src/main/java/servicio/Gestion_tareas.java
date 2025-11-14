/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */
import dao.TareaDAO;
import dao.GatoDAO;
import dao.UsuarioDAO;
import modelo.*; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Gestion_tareas {

    private TareaDAO tareaDAO;
    private GatoDAO gatoDAO;
    private UsuarioDAO usuarioDAO;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Gestion_tareas(TareaDAO tareaDAO, GatoDAO gatoDAO, UsuarioDAO usuarioDAO) {
        this.tareaDAO = tareaDAO;
        this.gatoDAO = gatoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    //CRUD TAREAS
    
    public void registrarTarea(Voluntario voluntario, Gato gato, String fechaStr, int tipo, String coordenadas)throws Exception { 
        if (gato == null) {
            throw new Exception("Gato no encontrado");
        }
            if (fechaStr == null || fechaStr.trim().isEmpty()) {
                throw new Exception("La fecha límite no puede estar vacia.");
            }
                if (coordenadas == null || coordenadas.trim().isEmpty()) {
                    throw new Exception("Las coordenadas no pueden estar vacias.");
                }
        LocalDateTime fechaLimite;
        try {
            fechaLimite = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new Exception("Error: Formato de fecha invalido. Use dd/MM/yyyy HH:mm");
        }
        Tarea nuevatarea = new Tarea(fechaLimite, tipo, coordenadas, gato, voluntario); // el voluntario puede ser null si se va a asignar automáticamente
        tareaDAO.guardar(nuevatarea);
    }

    //evalua tarea y actualiza repu, resp -> admin
    public void evaluarTarea(Tarea tarea, int nuevoEstado) throws Exception {
       if (tarea == null) { 
            throw new Exception("Tarea no encontrada");
        }
        if (tarea.getVoluntario() == null) { 
            throw new Exception("Tarea sin voluntario asignado");
        }

        Voluntario vol = tarea.getVoluntario(); 

        if (nuevoEstado == 1) { 
            tarea.tareaCompletada(); 
            if (tarea.getFechahora() != null && LocalDateTime.now().isBefore(tarea.getFechahora())) {
                System.out.println("Tarea realizada a tiempo. Reputacion +1"); 
                vol.aumentarReputacion(1);
                } else {
                    System.out.println("Tarea realizada fuera de plazo. Reputacion -1"); 
                    vol.disminuirReputacion(1); 
                }
                    tareaDAO.actualizar(tarea); 
                    usuarioDAO.actualizar(vol); 
            
        } else if (nuevoEstado == 2) { 
            tarea.setEstado(2); 
            System.out.println("Tarea incompleta. Reputacion -2"); 
            vol.disminuirReputacion(2); 
            tareaDAO.actualizar(tarea);
            usuarioDAO.actualizar(vol);
        }
    }
    public Tarea buscarTareaPorId(int idTarea) {
        return tareaDAO.buscarPorId(idTarea);
    }
    public List<Tarea> listarTareasPorVoluntario(Voluntario v) {
        return tareaDAO.buscarPendientesPorVoluntario(v); 
    }

    public List<Tarea> listarTareas() {
        List<Tarea> tareas = tareaDAO.listarTodas();
        return tareas;
    }

    
    public String asignarTareasPendientes() {
        List<Tarea> tareasPendientes = tareaDAO.buscarSinVoluntarioAsignado();
        if (tareasPendientes.isEmpty()) {
            return "No hay tareas nuevas pendientes de asignacion.";
        }
        List<Voluntario> disponibles = obtenerVoluntariosDisponiblesParaTareas();
            if (disponibles.isEmpty()) {
                return "No hay voluntarios disponibles para asignar las tareas pendientes.(Ocupados o sin reputacion suficiente)";
            }
        int voluntarionum = 0; 
        for (Tarea t : tareasPendientes) {
            Voluntario voluntarioAsignado = disponibles.get(voluntarionum);
            t.setVoluntario(voluntarioAsignado); 
            tareaDAO.actualizar(t); 
            System.out.println("Tarea #" + t.getIdtarea() + " asignada a " + voluntarioAsignado.getNombre());
            voluntarionum = (voluntarionum + 1) % disponibles.size(); 
        }
        return "Se asignaron " + tareasPendientes.size() + " tareas automaticamente";
    }
    
    public List<Voluntario> obtenerVoluntariosDisponiblesParaTareas() {
       List<Usuario> todosUsuarios = usuarioDAO.listarTodos();
        List<Voluntario> disponibles = new ArrayList<>();
        for (Usuario u : todosUsuarios) {
            if (u instanceof Voluntario) {
                Voluntario v = (Voluntario) u; 
                if (v.getReputacion() >= 0) { 
                    List<Tarea> tareasPendientesVol = tareaDAO.buscarPendientesPorVoluntario(v);
                    if (tareasPendientesVol.isEmpty()) { 
                        disponibles.add(v);
                    }
                }
            }
        }
        return disponibles;
    }
    public void actualizarTarea(Tarea tarea, Voluntario vol, Gato gato, String fechaStr, int tipo, String coords) throws Exception {
        if (tarea == null) {
            throw new Exception("No se encontro la tarea a modificar");
        }
        if (gato == null) {
            throw new Exception("Debe seleccionar un gato.");
        }
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            throw new Exception("La fecha limite no puede estar vacía.");
        }
        LocalDateTime fechaLimite;
        try {
            fechaLimite = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new Exception("Error: Formato de fecha invalido. Use dd/MM/yyyy HH:mm");
        }
        tarea.setVoluntario(vol); 
        tarea.setGato(gato);
        tarea.setFechahora(fechaLimite);
        tarea.setTipo(tipo);
        tarea.setCoordenadas(coords);
        
        tareaDAO.actualizar(tarea);

    }
    public void eliminarTarea(Tarea tarea) throws Exception {
        if (tarea == null) {
            throw new Exception("No se ha seleccionado ninguna tarea para eliminar.");
        }
        tareaDAO.eliminar(tarea);
    }
       public List<Tarea> listarTareasSinAsignar() {
        return tareaDAO.buscarSinVoluntarioAsignado();
    }
       
}
