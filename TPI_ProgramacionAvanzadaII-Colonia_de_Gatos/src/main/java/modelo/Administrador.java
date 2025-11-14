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
@DiscriminatorValue("1")
public class Administrador extends Usuario {

    public Administrador(String DNI, String clave, String telefono, int rol, String correo,String nombre) {
        super(DNI, clave, telefono, rol, correo,nombre);
    }
    public Administrador() {
        super();
     }
    
}
