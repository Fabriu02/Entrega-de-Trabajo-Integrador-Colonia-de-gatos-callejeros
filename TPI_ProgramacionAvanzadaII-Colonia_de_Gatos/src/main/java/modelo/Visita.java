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

@Entity
@Table(name = "visitas")
public class Visita implements EventoCalendario { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codvisita; 

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechahora;

    @Column(length = 1000) 
    private String observacion;
    private boolean cumplida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_dni") 
    private Voluntario voluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hogar_id") 
    private Hogar_Adopcion adopcion; 

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Visita() {
    }

   
    public Visita(LocalDateTime fechahora, String observacion, Voluntario voluntario, Hogar_Adopcion adopcion) {
        this.fechahora = fechahora;
        this.observacion = observacion;
        this.voluntario = voluntario;
        this.adopcion = adopcion; 
        this.cumplida = false; 
    }

    public int getCodvisita() { return codvisita; }
    public LocalDateTime getFechahora() { 
        return fechahora; 
    }
    public void setFechahora(LocalDateTime fechahora) { 
        this.fechahora = fechahora;
    }
    public String getObservacion() { 
        return observacion;
    }
    public void setObservacion(String observacion) {
        this.observacion = observacion; 
    }
    public boolean isCumplida() { 
        return cumplida; 
    }
    public void setCumplida(boolean cumplida) {
        this.cumplida = cumplida;
    }
    public Voluntario getVoluntario() {
        return voluntario;
    }
    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario; 
    }
    public Hogar_Adopcion getAdopcion() { 
        return adopcion;
    } 
    public void setAdopcion(Hogar_Adopcion adopcion) {
        this.adopcion = adopcion; 
    }

    public void visitaCumplida() { 
        this.cumplida = true;
    }

    // EVENTO CALENDARIO METODOS 
    @Override 
    public LocalDateTime getFecha() { 
        return fechahora; 
    }
    @Override public String getDescripcion() {
         String famNombre = (adopcion != null && adopcion.getFamilia() != null) ? adopcion.getFamilia().getNombre() : "N/A";
         return "Visita #" + codvisita + " a " + famNombre;
     }
    @Override public boolean estaVencido() {
        return !cumplida && fechahora != null && fechahora.isBefore(LocalDateTime.now());
     }
    @Override public boolean requiereAlerta() {
        return !cumplida && fechahora != null && fechahora.minusHours(24).isBefore(LocalDateTime.now());
    }

    public String resumen() {
        String fechaStr = (fechahora != null) ? fechahora.format(FORMATO_FECHA) : "N/A";
        String volNombre = (voluntario != null) ? voluntario.getNombre() : "N/A";
        String famNombre = (adopcion != null && adopcion.getFamilia() != null) ? adopcion.getFamilia().getNombre() : "N/A";
        String hogarId = (adopcion != null) ? String.valueOf(adopcion.getIdhogarfam()) : "N/A";

        return "Visita #" + codvisita + " el " + fechaStr + "\n" +
               " por voluntario " + volNombre + "\n" +
               " a la familia " + famNombre + " | ID del hogar: " + hogarId;
    }
}
    

