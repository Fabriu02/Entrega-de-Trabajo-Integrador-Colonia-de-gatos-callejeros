/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */

import dao.HogarDAO;
import dao.UsuarioDAO; 
import dao.GatoDAO;
import java.util.ArrayList;
import modelo.Usuario;
import modelo.Familia;
import modelo.Hogar_Adopcion;
import java.util.List;


public class Gestion_hogares {

    private HogarDAO hogarDAO;
    private UsuarioDAO usuarioDAO; 
    private GatoDAO gatoDAO;
    private Listado_zonas zonaService;

    public Gestion_hogares(HogarDAO hogarDAO, UsuarioDAO usuarioDAO,GatoDAO gatoDAO,Listado_zonas zonaService) {
        this.hogarDAO = hogarDAO;
        this.usuarioDAO = usuarioDAO;
        this.gatoDAO = gatoDAO;
        this.zonaService = zonaService;
    }

    //CRUD HOGARES
    public void registrarHogar(String direccion, String coordenadas, Familia familia)throws Exception { 
        if (familia == null) {
            throw new Exception("Familia no encontrada. No se puede registrar el hogar.");
        }
            if (direccion == null || direccion.trim().isEmpty()) {
                 throw new Exception("La direccion no puede estar vacia.");
            }
            if (hogarDAO.buscarPorDniFamilia(familia.getDNI()) != null) {
                 throw new Exception("Error: Esta familia ya tiene un hogar registrado.");
            }
                Hogar_Adopcion nuevoh = new Hogar_Adopcion(direccion, coordenadas, familia);
                hogarDAO.guardar(nuevoh);
                try {
            String nombreZonaHogar = "Hogar de " + familia.getNombre();
            zonaService.registrarZona(nombreZonaHogar, coordenadas, 1); 
            System.out.println("Zona creada para el hogar " + nuevoh.getIdhogarfam());
        } catch (Exception e) {
            throw new Exception("Hogar registrado, pero fallo al registrar zona: " + e.getMessage());
        }
    }
    public List<Hogar_Adopcion> listarHogares() {
        return hogarDAO.listarTodos();
    }
    public Hogar_Adopcion buscarHogarPorId(int idHogar) {
        return hogarDAO.buscarPorId(idHogar);
    }
    public Hogar_Adopcion buscarHogarPorFamilia(Familia familia) {
        if (familia == null) return null;
        return hogarDAO.buscarPorDniFamilia(familia.getDNI());
    }
    public List<Hogar_Adopcion> listarHogaresPostulados(){
        List<Hogar_Adopcion> todoshogares = hogarDAO.listarTodos();
        List<Hogar_Adopcion> postulados = new ArrayList<>();
        for(Hogar_Adopcion h : todoshogares){
            if(h.getFamilia() != null && h.getFamilia().isPostulado()){
                postulados.add(h);
            }
        }
        return postulados;
    }
    public void modificarHogar(Hogar_Adopcion hogar, String nuevaDireccion, String nuevasCoords)throws Exception {
        if (hogar == null) {
            throw new Exception("No se ha seleccionado un hogar valido.");
        }
            if (nuevaDireccion == null || nuevaDireccion.trim().isEmpty()) {
                 throw new Exception("La direccion no puede estar vacia.");
            }
            hogar.setDireccion(nuevaDireccion);
            hogar.setCoordenadas(nuevasCoords);
            hogarDAO.actualizar(hogar);
    }
    public void eliminarHogar(Hogar_Adopcion hogar)throws Exception {
        if (hogar == null) {
            throw new Exception("No se ha seleccionado un hogar valido.");
        }
            if (!hogar.getGatos().isEmpty()) {
                 throw new Exception("No se puede eliminar el hogar porque tiene " + hogar.getGatos().size() + " gato/s asignado/s");
            }
            hogarDAO.eliminar(hogar);
    }
}
    

