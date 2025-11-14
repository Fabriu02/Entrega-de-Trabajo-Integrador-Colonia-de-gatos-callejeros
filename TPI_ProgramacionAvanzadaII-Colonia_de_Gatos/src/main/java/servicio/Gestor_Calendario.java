/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */

import dao.DiagnosticoDAO;
import dao.TareaDAO;
import dao.VisitaDAO;
import modelo.Diagnosticos;
import modelo.EventoCalendario; 
import modelo.Tarea;
import modelo.Visita;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Gestor_Calendario {

    private DiagnosticoDAO diagnosticoDAO;
    private TareaDAO tareaDAO;
    private VisitaDAO visitaDAO;
    public Gestor_Calendario(DiagnosticoDAO diagnosticoDAO, TareaDAO tareaDAO, VisitaDAO visitaDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
        this.tareaDAO = tareaDAO;
        this.visitaDAO = visitaDAO;
    }
    public List<EventoCalendario> obtenerTodosLosEventosOrdenados() {
        List<EventoCalendario> todosLosEventos = new ArrayList<>();
        todosLosEventos.addAll(diagnosticoDAO.listarTodos());
        todosLosEventos.addAll(tareaDAO.listarTodas());
        todosLosEventos.addAll(visitaDAO.listarTodas());
        todosLosEventos.sort(Comparator.comparing(EventoCalendario::getFecha,Comparator.nullsLast(Comparator.naturalOrder()) )); //ordena la lista combinada por fecha (los mas próximos primero)
        return todosLosEventos;
    }
    public List<EventoCalendario> listarEventosProximos(int dias) { // para que sea como una vision semanal
        List<EventoCalendario> todos = obtenerTodosLosEventosOrdenados();
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime futuro = ahora.plusDays(dias);
        // que sea antes de 7 días
        return todos.stream().filter(e -> !e.estaVencido()) .filter(e -> e.getFecha() != null) .filter(e -> e.getFecha().isAfter(ahora.minusMinutes(1))) .filter(e -> e.getFecha().isBefore(futuro))  .collect(Collectors.toList());
    }

   public List<EventoCalendario> listarAlertas() {
        List<EventoCalendario> todos = obtenerTodosLosEventosOrdenados();
        return todos.stream().filter(EventoCalendario::requiereAlerta).collect(Collectors.toList()); //filtra con funcion requiere alerta()
    }
}
