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

@Entity
@Table(name = "solicitudes_adopcion")
public class SolicitudAdopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familia_dni") 
    private Familia familia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gato_id") 
    private Gato gato;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaSolicitud;

     // Estados de la solicitud
     // 0 = Pendiente 
     // 1 = Aprobada
     // 2 = Rechazada
     
    private int estado;
    public SolicitudAdopcion() {
    }
    public SolicitudAdopcion(Familia familia, Gato gato) {
        this.familia = familia;
        this.gato = gato;
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = 0; // default pendiente
    }
    
    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Gato getGato() {
        return gato;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public int getEstado() {
        return estado;
    }
    
    public String getEstadoComoTexto() {
        return switch (estado) {
            case 0 -> "Pendiente";
            case 1 -> "Aprobada";
            case 2 -> "Rechazada";
            default -> "Desconocido";
        };
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
