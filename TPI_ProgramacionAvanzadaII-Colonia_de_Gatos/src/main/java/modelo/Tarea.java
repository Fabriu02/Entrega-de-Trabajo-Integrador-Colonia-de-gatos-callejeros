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
import java.util.List; 

@Entity
@Table(name = "tareas")
public class Tarea implements EventoCalendario { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtarea;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechahora; 

    // 0: Pendiente, 1: Completa, 2: Incompleta 
    private int estado; 
    
    // 1: Alimentacion, 2: Captura, 3: Transporte, 4: Control Vet
    private int tipo; 
    
    private String coordenadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gato_id")
    private Gato gato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_dni") 
    private Voluntario voluntario;

    public Tarea() {
    }

    public Tarea(LocalDateTime fechahora, int tipo, String coordenadas, Gato gato, Voluntario voluntario) {
        this.fechahora = fechahora;
        this.tipo = tipo;
        this.coordenadas = coordenadas;
        this.gato = gato;
        this.voluntario = voluntario;
        this.estado = 0; 
    }

    public int getIdtarea() { 
        return idtarea;
    }
    public LocalDateTime getFechahora() { 
        return fechahora;
    }
    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora; 
    }
    public int getEstado() { 
        return estado; 
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) { 
        this.tipo = tipo; 
    }
    public String getCoordenadas() { 
        return coordenadas; 
    }
    public void setCoordenadas(String coordenadas) { 
        this.coordenadas = coordenadas;
    }
    public Gato getGato() { 
        return gato;
    }
    public void setGato(Gato gato) { 
        this.gato = gato; 
    }
    public Voluntario getVoluntario() { 
        return voluntario;
    }
    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario; 
    }

   
    public void tareaCompletada() { 
        this.estado = 1;
    }
  
    private static final DateTimeFormatter FORMATO_FECHA_CAL = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    @Override 
    public LocalDateTime getFecha() { 
        return this.fechahora; 
    }
    
    @Override 
    public String getDescripcion() { 
        String tipoDesc = switch (tipo) {
            case 1 -> "Alimentacion";
            case 2 -> "Captura castracion";
            case 3 -> "Transporte hogar transitorio";
            case 4 -> "Control veterinario";
            default -> "Tipo desconocido";
        };
        String gatoNombre = (gato != null) ? gato.getNombre() : "N/A";
        return "Tarea #" + idtarea + ": " + tipoDesc + " (" + gatoNombre + ")";
    }
    
    @Override 
    public boolean estaVencido() { 
         return estado != 1 && fechahora != null && fechahora.isBefore(LocalDateTime.now());
    }
    
    @Override 
    public boolean requiereAlerta() { 
        return estado != 1 && fechahora != null && fechahora.minusHours(24).isBefore(LocalDateTime.now());
    }
     @Override
    public String toString() {
        return "Tarea{" +
               "idtarea=" + idtarea +
               ", fechahora=" + (fechahora != null ? fechahora.format(FORMATO_FECHA_CAL) : "N/A")+
               ", estado=" + estado +
               ", tipo=" + tipo +
               ", coordenadas='" + coordenadas + '\'' +
               ", gato=" + (gato != null ? gato.getNombre() : "N/A") +
               ", voluntario=" + (voluntario != null ? voluntario.getNombre() : "N/A") +
               '}';
    }
}
