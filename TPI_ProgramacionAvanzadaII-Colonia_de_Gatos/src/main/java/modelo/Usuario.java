/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.*;
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "roles", discriminatorType = DiscriminatorType.INTEGER)

public abstract class Usuario {
    @Id
    private String DNI;
    
    private String nombre;
    private String clave;
    private String telefono;
    private String correo;
    @Column(name = "roles", insertable = false, updatable = false)
    private int rol;
    
    public Usuario() {
    }

        public Usuario(String DNI, String clave, String telefono, int rol, String correo,String nombre) {
            this.DNI = DNI;
            this.clave = clave;
            this.telefono = telefono;
            this.rol = rol;
            this.correo = correo;
            this.nombre = nombre;
        }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getRol() {
        return rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    } 
        public String nombrerol(){
            switch(rol){
                case 1: return "Administrador"; 

                case 2: return "Veterinario";

                case 3 : return "Voluntario";

                case 4: return "Familia";

                default : return "Rol invalido";

            }
        }
    @Override
    public String toString() {
        return "Usuario{" +"DNI='" + DNI + '\'' +", nombre='" + nombre + '\'' +", telefono='" + telefono + '\'' +", correo='" + correo + '\'' +", rol=" + rol ; 
    }

}
 

