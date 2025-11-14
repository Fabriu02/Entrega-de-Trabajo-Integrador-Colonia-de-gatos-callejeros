/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fabri
 */


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "diagnosticos")
public class Diagnosticos implements EventoCalendario { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id_diagnostico;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    private String diagnostico;
    private String tratamiento;
    private boolean estado; // tratamiento completado
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gato_id") 
    private Gato paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_dni") 
    private Veterinario autor;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Diagnosticos() {
    }

    public Diagnosticos(LocalDateTime fecha, String diagnostico, String tratamiento, String descripcion, Gato paciente, Veterinario autor) {
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estado = false;
        this.descripcion = descripcion;
        this.paciente = paciente;
        this.autor = autor;
    }

    public int getId_diagnostico() { return id_diagnostico; }
    @Override public LocalDateTime getFecha() { 
        return fecha; 
    } // de eventocalendario
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha; 
    }
    public String getDiagnostico() { 
        return diagnostico; 
    }
    public void setDiagnostico(String diagnostico) { 
        this.diagnostico = diagnostico; 
    }
    public String getTratamiento() {
        return tratamiento; 
    }
    public void setTratamiento(String tratamiento) { 
        this.tratamiento = tratamiento; 
    }
    public boolean isEstado() { 
        return estado; 
    }
    public void setEstado(boolean estado) { 
        this.estado = estado; 
    }
    @Override public String getDescripcion() { 
        return descripcion; 
    } // Viene de EventoCalendario
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Gato getPaciente() { 
        return paciente;
    }
    public void setPaciente(Gato paciente) {
        this.paciente = paciente; 
    }
    public Veterinario getAutor() {
        return autor;
    }
    public void setAutor(Veterinario autor) {
        this.autor = autor; 
    }

    // Verificacion vencimientos
    @Override public boolean estaVencido() {
        return !estado && fecha.isBefore(LocalDateTime.now());
    }
    @Override public boolean requiereAlerta() {
        return !estado && fecha.minusHours(24).isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Diagnóstico #" + id_diagnostico +
               " | Fecha: " + (fecha != null ? fecha.format(FORMATO_FECHA) : "Sin asignar") +
               "\nPaciente: " + (paciente != null ? paciente.getNombre() : "Desconocido") +
               "\nDiagnóstico: " + diagnostico +
               "\nTratamiento: " + tratamiento +
               "\nEstado: " + (estado ? "Completado" : "Pendiente") +
               "\nDescripción: " + descripcion +
               "\nVeterinario: " + (autor != null ? autor.getNombre() : "No registrado");
    }
}