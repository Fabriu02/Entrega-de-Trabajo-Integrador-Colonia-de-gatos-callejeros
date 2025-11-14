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

@Entity
@Table(name = "gatos")
public class Gato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idgato; 

    private String nombre;
    private String color;
    private String descripcion;
    private boolean esterilizado;
    private int apto; // 0: No Apto / 1: Sano (Apto para adopcion)
    private int adoptado; // 0: Libre / 1: Adoptado / 2: Transicion
    private int edad;
    private String fotoUrl;
    private String qrUrl;

    // para que se una con zona
    @ManyToOne(fetch = FetchType.EAGER) // EAGER -> para que cargue la zona al buscar el gato
    @JoinColumn(name = "zona_id") 
    private Zona_geografica zona;
    
  //para que se una con hogar
     @ManyToOne
     @JoinColumn(name = "hogar_id")
     private Hogar_Adopcion hogar;

    
    public Gato() {
    }

    public Gato(String nombre, String color, String descripcion) {
        this.nombre = nombre;
        this.color = color;
        this.descripcion = descripcion;
        this.esterilizado = false;
        this.apto = 0; 
        this.adoptado = 0;
        this.edad=edad;
        this.fotoUrl=fotoUrl;
    }
    
    public int getId() {
        return idgato;
    }
    public void setId(int idgato) { 
        this.idgato = idgato;
    }
    
    public String getNombre() {
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getColor() {
        return color; 
    }
    public void setColor(String color) { 
        this.color = color;
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public boolean isEsterilizado() {
        return esterilizado; 
    }
    public void setEsterilizado(boolean esterilizado) { 
        this.esterilizado = esterilizado; 
    }
    
    public int getApto() { 
        return apto; 
    }
    public void setApto(int apto) {
        this.apto = apto;
    }
    
    public Zona_geografica getZona() {
        return zona; 
    }
    public void setZona(Zona_geografica zona) { 
        this.zona = zona; 
    }
    
    public String getAdoptado(){
        switch(adoptado){
            case 0: return "Libre";
            case 1 : return "Adoptado";
            case 2: return "Transicion";
            case 3: return "Reservado";
            default: return "-";
        }
    }
    
    public void esterilizado(){ 
        this.esterilizado = true; 
    }
    public void apto_adopcion(){ 
        this.apto = 1; 
    }
    public void apto_salud(){ 
        this.apto = 1; 
    }
    public void adoptado(){ 
        this.adoptado = 1;
    }
    public void libre(){ 
        this.adoptado=0; 
    }
    public void transicion(){ 
        this.adoptado = 2;
    }
    public void reservado(){
        this.adoptado = 3;
    }

    @Override
    public String toString() {
        String nombreZona = (zona != null) ? zona.getNombrezona() : "Sin asignar";
        return "Gato ID=" + idgato +"\n Nombre='" + nombre + "\n Color='" + color +   "\n Zona='" + nombreZona +   "\n Apto=" + (apto == 1 ? "Si" : "No") + "\n Estado='" + getAdoptado();
    }

    public void setHogar(Hogar_Adopcion hogar) {
        this.hogar = hogar;
    }

    public Hogar_Adopcion getHogar() {
        return hogar;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }
    
}