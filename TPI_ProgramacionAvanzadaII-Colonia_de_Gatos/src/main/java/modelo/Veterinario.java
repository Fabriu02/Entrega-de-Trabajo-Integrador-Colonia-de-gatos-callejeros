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
@DiscriminatorValue("2")
public class Veterinario extends Usuario {
    private String matricula;
    public Veterinario(String DNI, String clave, String telefono, int rol, String correo, String nombre,String matricula) {
        super(DNI, clave, telefono, rol, correo, nombre);
        this.matricula=matricula;
    }
    public Veterinario() {
        super();
     }
     public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
