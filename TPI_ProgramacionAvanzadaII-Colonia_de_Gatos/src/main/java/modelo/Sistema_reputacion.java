/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fabri
 */
import java.util.List;
public interface Sistema_reputacion {           //Reglas: la reputacion se setea en 0, en cada visita se suma 1 punto, 3 en total, si es negativo
     int getReputacion();                        // El gato no sera adoptado 
    void aumentarReputacion(int puntos);
    void disminuirReputacion(int puntos);
    void setReputacion(int valor);
}
