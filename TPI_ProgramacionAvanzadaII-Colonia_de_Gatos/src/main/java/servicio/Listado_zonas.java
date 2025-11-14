/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */

import dao.ZonaDAO;
import modelo.Zona_geografica;
import java.util.List;
import dao.GatoDAO;
import modelo.Gato;

public class Listado_zonas {

    private ZonaDAO zonaDAO;
    private GatoDAO gatoDAO;

    public Listado_zonas(ZonaDAO zonaDAO,GatoDAO gatoDAO) {
        this.zonaDAO = zonaDAO;
        this.gatoDAO = gatoDAO;
    }

    //CRUD Zona
    public void registrarZona(String nombre, String coordenadas, int tipo) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre de la zona no puede estar vacío.");
        }
        if (coordenadas == null || coordenadas.trim().isEmpty()) {
            throw new Exception("Las coordenadas no pueden estar vacías.");
        }
        
        Zona_geografica nuevazona = new Zona_geografica(nombre, coordenadas, tipo);
        
        zonaDAO.guardar(nuevazona);
    }

    public List<Zona_geografica> listarZonas() {
        return zonaDAO.listarTodas();
    }

    public Zona_geografica buscarPorId(int id) {
       return zonaDAO.buscarPorId(id);
    }
  
    public void eliminarZona(Zona_geografica zona) throws Exception {
        if (zona == null) {
            throw new Exception("No se ha seleccionado ninguna zona.");
        }
        List<Gato> gatosEnZona = gatoDAO.buscarPorZona(zona);
            if (!gatosEnZona.isEmpty()) {
                throw new Exception("No se puede eliminar la zona '" + zona.getNombrezona() + "' porque tiene " + gatosEnZona.size() + " gato(s) asociado(s).");
            }
            zonaDAO.eliminar(zona);
    }
    public void actualizarZona(int idZona, String nuevoNombre, String nuevasCoords, int nuevoTipo) throws Exception {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío.");
        }
        if (nuevasCoords == null || nuevasCoords.trim().isEmpty()) {
            throw new Exception("Las coordenadas no pueden estar vacías.");
        }
        Zona_geografica zona = zonaDAO.buscarPorId(idZona);
        if (zona == null) {
            throw new Exception("No se encontró la zona para actualizar.");
        }
        zona.setNombrezona(nuevoNombre);
        zona.setCoordenadas(nuevasCoords);
        zona.setTipo(nuevoTipo); 
        
        zonaDAO.actualizar(zona);
    }
}