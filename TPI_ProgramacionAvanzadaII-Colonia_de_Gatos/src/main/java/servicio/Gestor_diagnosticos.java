package servicio;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Fabri
 */
import dao.DiagnosticoDAO;
import dao.GatoDAO;
import dao.UsuarioDAO;
import modelo.*; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;

public class Gestor_diagnosticos {

    private DiagnosticoDAO diagnosticoDAO;
    private GatoDAO gatoDAO;
    private UsuarioDAO usuarioDAO;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Gestor_diagnosticos(DiagnosticoDAO diagnosticoDAO, GatoDAO gatoDAO, UsuarioDAO usuarioDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
        this.gatoDAO = gatoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    //CRUD DIAGNOSTICOS 
    
    public void crearDiagnostico(Gato paciente, Veterinario autor, String fechaStr, String diag, String tratamiento, String descripcion)throws Exception { 
        if (paciente == null) throw new Exception("Debe seleccionar un gato");
        if (autor == null) throw new Exception("Error de autorización, no se encontro al veterinario.");
        if (fechaStr == null || fechaStr.trim().isEmpty()) throw new Exception("La fecha no puede estar vacia");
        if (diag == null || diag.trim().isEmpty()) throw new Exception("El diagnostico no puede estar vacio");
        LocalDateTime fecha;
        try {
            fecha = LocalDateTime.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new Exception("Formato de fecha invalido. Use dd/MM/yyyy HH:mm");
        }
        Diagnosticos nuevoDiag = new Diagnosticos(fecha, diag, tratamiento, descripcion, paciente, autor);
        diagnosticoDAO.guardar(nuevoDiag);
    }

   
    public List<Diagnosticos> mostrarDiagnosticos() {
        return diagnosticoDAO.listarTodos();
    }

    
    public List<Diagnosticos> mostrarHistorialPorGato(Gato gato) throws Exception {
        if (gato == null) {
            throw new Exception("No se ha seleccionado ningun gato");
        }
        return diagnosticoDAO.buscarPorGato(gato);
    }

    public void actualizarDiagnostico(Diagnosticos diag, String nuevoTratamiento, String nuevaDescripcion, boolean completado) throws Exception {
        if (diag == null) {
            throw new Exception("No se ha seleccionado un diagnostico");
        }
            diag.setTratamiento(nuevoTratamiento);
            diag.setDescripcion(nuevaDescripcion);
            diag.setEstado(completado);
            diagnosticoDAO.actualizar(diag);
    }
       
    public void eliminarDiagnostico(Diagnosticos diag)throws Exception {
        if (diag == null) {
            throw new Exception("No se ha seleccionado un diagnostico.");
        }
        diagnosticoDAO.eliminar(diag);  
    }


    public void marcarApto(Gato gato)throws Exception {
        if (gato == null) {
            throw new Exception("No se ha seleccionado un gato");
        }
        List<Diagnosticos> historial = diagnosticoDAO.buscarPorGato(gato);

        if (historial.isEmpty()) {
            throw new Exception("El gato no tiene historial clinico. No se puede marcar como apto");
        }
        boolean todosCompletos = true;
        for (Diagnosticos diag : historial) {
            if (!diag.isEstado()) {
                todosCompletos = false;
                break;
            }
        }
        if (todosCompletos) {
            gato.apto_salud(); 
            gatoDAO.actualizar(gato);
        } else {
            throw new Exception("Error: El gato " + gato.getNombre() + "tiene tratamientos pendientes");
        }
    }
    public Diagnosticos buscarporID(int id){
        return diagnosticoDAO.buscarPorId(id);
    }

    public List<Diagnosticos> mostrarAlertasDiagnosticos() {
        List<Diagnosticos> alertas = new ArrayList<>();
        List<Diagnosticos> todos = diagnosticoDAO.listarTodos();
        for (Diagnosticos d : todos) {
            if (d.requiereAlerta()) {
                alertas.add(d);
            }
        }
        return alertas;
    }
}

