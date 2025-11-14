/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fabri
 */
//INTERFAZ HOGAR : agregue esta funcionalidad porque pensaba en dividir los tipos de hogares en 2 clases distintas, la deje porque es util para usarla en un futuro

import java.util.List;

public interface Hogar {
    void agregarGato(Gato gato);
    List<Gato> getGatos();
}

