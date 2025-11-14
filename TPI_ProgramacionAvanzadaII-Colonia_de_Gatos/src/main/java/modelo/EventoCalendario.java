/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Fabri
 */

public interface EventoCalendario {
   LocalDateTime getFecha();
    String getDescripcion();
    boolean estaVencido();
    boolean requiereAlerta();
}
