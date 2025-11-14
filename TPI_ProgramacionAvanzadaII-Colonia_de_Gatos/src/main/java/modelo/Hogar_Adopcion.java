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
import java.util.ArrayList; 
import java.util.List;

@Entity
@Table(name = "hogares") 
public class Hogar_Adopcion { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idhogarfam; 

    private String direccion;
    private String coordenadas;
    private boolean aprobado;


    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "familia_dni") 
    private Familia familia;

    @OneToMany(mappedBy = "hogar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Gato> gatos = new ArrayList<>(); 

    public Hogar_Adopcion() {
    }

    public Hogar_Adopcion(String direccion, String coordenadas, Familia familia) { 
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        this.familia = familia;
        this.aprobado = false;
    }

    public int getIdhogarfam() { 
        return idhogarfam;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) { 
        this.direccion = direccion;
    }
    public String getCoordenadas() {
        return coordenadas;
    }
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
    public boolean isAprobado() { 
        return aprobado; 
    }
    public void setAprobado(boolean aprobado) { 
        this.aprobado = aprobado; 
    }
    public Familia getFamilia() { 
        return familia; 
    }
    public void setFamilia(Familia familia) { 
        this.familia = familia; 
    }
    public List<Gato> getGatos() { 
        return gatos; 
    }
    public void setGatos(List<Gato> gatos) { 
        this.gatos = gatos; 
    }

    public void agregarGato(Gato gato) {
        this.gatos.add(gato);
        gato.setHogar(this); 
    } //lado dueño -> gato para el modelo

    public void removerGato(Gato gato) {
        this.gatos.remove(gato);
        gato.setHogar(null); 
    }

    public void aprobado_adopcion() { 
        this.aprobado = true;
    }

    @Override
    public String toString() {
        return "Hogar_Adopcion{" +"ID=" + idhogarfam +", direccion='" + direccion + '\'' +", coordenadas='" + coordenadas + '\'' +", aprobado=" + aprobado +", familia=" + (familia != null ? familia.getNombre() : "Sin asignar") +", gatosAsignados=" + (gatos != null ? gatos.size() : 0) + '}';
    }
}
