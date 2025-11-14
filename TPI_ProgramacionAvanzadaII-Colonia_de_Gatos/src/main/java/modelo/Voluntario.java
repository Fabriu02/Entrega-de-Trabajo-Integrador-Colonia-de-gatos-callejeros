/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fabri
 */
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("3")
public class Voluntario extends Usuario implements Sistema_reputacion {
    private int reputacion;
    public Voluntario(String DNI, String clave, String telefono, int rol, String correo, String nombre) {
        super(DNI, clave, telefono, rol, correo, nombre);
        this.reputacion=0;
    }
     public Voluntario() {
        super();
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
}
