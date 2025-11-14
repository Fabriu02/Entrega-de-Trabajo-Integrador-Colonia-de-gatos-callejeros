/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("4")
public class Familia extends Usuario implements Sistema_reputacion {
    private boolean postulado;
    private int reputacion;
    public Familia(String DNI, String clave, String telefono, int rol, String correo,String nombre) {
        super(DNI, clave, telefono, rol, correo,nombre);
        this.postulado=false;
    }
    public Familia(){
        super();
    }
    public void postularse(){
        this.postulado=true;
    }
    public void baja_postulado(){
        this.postulado=false;
    }
    
     @Override
    public int getReputacion() {
        return reputacion;
    }

@Override
    public void aumentarReputacion(int puntos) {
        reputacion = Math.min(reputacion + puntos, 3);
    }

    @Override
    public void disminuirReputacion(int puntos) {
        reputacion = Math.max(reputacion - puntos, -1);
    }
    @Override
    public void setReputacion(int valor) {
        this.reputacion = Math.max(-1, Math.min(3, valor));
    }

    public boolean isPostulado() {
        return postulado;
    }
     
}
