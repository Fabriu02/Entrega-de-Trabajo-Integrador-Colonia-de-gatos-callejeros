/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */

import dao.GatoDAO;
import dao.HogarDAO;
import modelo.Gato;
import dao.SolicitudDAO;
import dao.UsuarioDAO;
import modelo.*;
import modelo.Hogar_Adopcion;
import java.util.List;
import java.util.ArrayList;
import modelo.Familia;

public class Adopcion_gato { 

    private GatoDAO gatoDAO;
    private HogarDAO hogarDAO;
    private SolicitudDAO solicitudDAO; 
    private UsuarioDAO usuarioDAO;
    private Listado_zonas zonaService;
    public Adopcion_gato(GatoDAO gatoDAO, HogarDAO hogarDAO,SolicitudDAO solicitudDAO, UsuarioDAO usuarioDAO,Listado_zonas zonaService) {
        this.gatoDAO = gatoDAO;
        this.hogarDAO = hogarDAO;
        this.solicitudDAO = solicitudDAO;
        this.usuarioDAO = usuarioDAO;
        this.zonaService = zonaService;
    }
        private Zona_geografica buscarZonaDelHogar(Hogar_Adopcion hogar) {
        if (hogar == null || hogar.getCoordenadas() == null) {
            return null;
        }
        
        List<Zona_geografica> todasLasZonas = zonaService.listarZonas();
        for (Zona_geografica z : todasLasZonas) {
            if (z.getTipo() == 1 && z.getCoordenadas().equals(hogar.getCoordenadas())) {
                return z; 
            }
        }
        return null; 
    }
        public List<Gato> listarGatosDisponibles() {
            return gatoDAO.listarDisponibles();
        }
        public List<Gato> mostrarGatosTransicion() {
            List<Gato> todosGatos = gatoDAO.listarTodos();
            List<Gato> enTransicion = new ArrayList<>();
            for (Gato g : todosGatos) {
                if (g.getAdoptado().equals("Transicion")) {
                    enTransicion.add(g);
                }
            }
             return enTransicion;
        }
        public List<Gato> mostrarGatosAdoptados() {
            List<Gato> todosGatos = gatoDAO.listarTodos();
            List<Gato> adoptados = new ArrayList<>();
            for (Gato g : todosGatos) {
                if (g.getAdoptado().equals("Adoptado")) {
                    adoptados.add(g);
                }
            }
            return adoptados;
        }
        public List<SolicitudAdopcion> verPendientes(){
            return solicitudDAO.listarPendientes();
        }
        public SolicitudAdopcion solicitudPorId(int idSol){
            return solicitudDAO.buscarPorId(idSol);
        }
        public List<Gato> gatosporFamilia(Familia familia) throws Exception {
            if (familia == null) {
                throw new Exception("Familia no encontrada.");
            }
                Hogar_Adopcion hogar = hogarDAO.buscarPorDniFamilia(familia.getDNI());
                if (hogar == null) {
                    return new ArrayList<Gato>();
                }
                return hogar.getGatos();
        }
    public void realizarAdopcion(Gato gato, Hogar_Adopcion hogar, int tipoAdopcion) throws Exception {
        if (gato == null) throw new Exception("No se ha seleccionado un gato.");
         if (hogar == null) throw new Exception("No se ha encontrado el hogar de la familia.");
         Zona_geografica zonaDelHogar = buscarZonaDelHogar(hogar);
            if (zonaDelHogar == null) {
               System.err.println("Error: No se encontro una zona para el Hogar ID: " + hogar.getIdhogarfam());
           }
            if (!hogar.getFamilia().isPostulado()) {
                hogar.getFamilia().postularse(); 
                usuarioDAO.actualizar(hogar.getFamilia());
            }
         switch (tipoAdopcion) {
             case 1: // transicion
                 if (!hogar.getGatos().isEmpty() && !hogar.isAprobado()) {
                    throw new Exception("El hogar no esta aprobado y ya tiene un gato en transicion. No puede adoptar mas hasta ser aprobado.");
                }
                 if (gato.getAdoptado().equals("Libre") && gato.getApto() == 1) {
                     if (!hogar.isAprobado()) {
                         hogar.agregarGato(gato);
                         gato.setZona(zonaDelHogar);
                         gato.transicion(); 
                         hogarDAO.actualizar(hogar);
                         gatoDAO.actualizar(gato);
                         hogar.getFamilia().baja_postulado(); 
                         usuarioDAO.actualizar(hogar.getFamilia());
                     } else {
                         throw new Exception("El hogar ya esta aprobado. Debe realizar una adopcion definitiva.");
                     }
                 } else {
                     throw new Exception("Gato no disponible para adopcion temporal (Estado: " + gato.getAdoptado() + ", Apto: " + (gato.getApto() == 1 ? "Sí" : "No") + ")");
                 }
                 break;

             case 2: //definitiva
                 if ((gato.getAdoptado().equals("Transicion") || gato.getAdoptado().equals("Libre")) && gato.getApto() == 1) {
                     if (hogar.isAprobado()) {
                         if (!hogar.getGatos().contains(gato)) {
                             hogar.agregarGato(gato);
                         }
                         gato.adoptado();
                         gato.setZona(zonaDelHogar);
                         hogarDAO.actualizar(hogar);
                         gatoDAO.actualizar(gato);
                         hogar.getFamilia().baja_postulado();
                         usuarioDAO.actualizar(hogar.getFamilia());
                     } else {
                         throw new Exception("El hogar no esta aprobado para adopcion definitiva. Realice primero una adopcion temporal.");
                     }
                 } else {
                     throw new Exception("Gato no disponible para adopcion definitiva (Estado: " + gato.getAdoptado() + ", Apto: " + (gato.getApto() == 1 ? "Sí" : "No") + ")");
                 }
                 break;

             default:
                 throw new Exception("Opcion de adopcion invalida.");
         }
    }
    public void cancelarAdopcion(Gato gato, Hogar_Adopcion hogar)throws Exception { 
       if (gato == null) throw new Exception("Gato no encontrado.");
        if (hogar == null) throw new Exception("Hogar no encontrado.");
        if (gato.getHogar() != null && gato.getHogar().getIdhogarfam() == hogar.getIdhogarfam()) {
            hogar.removerGato(gato); 
            gato.libre(); 
            hogarDAO.actualizar(hogar);
            gatoDAO.actualizar(gato);
        } else {
            throw new Exception("Error: El gato " + gato.getNombre() + " no esta asignado a este hogar.");
        }
    }
    
   
    // Metodo para panel Familia 
 
    public void crearSolicitud(Gato gato, Familia familia) throws Exception {
        if (gato == null) throw new Exception("No se ha seleccionado un gato.");
        if (familia == null) throw new Exception("No se ha encontrado la familia.");
        if (!gato.getAdoptado().equals("Libre") || gato.getApto() != 1) {
            throw new Exception("Este gato no esta disponible para adopción.");
        }
        if (!solicitudDAO.buscarPendientesPorGato(gato).isEmpty()) {
            throw new Exception("Este gato ya tiene una solicitud pendiente de revision.");
        }
        if (!familia.isPostulado()) {
            familia.postularse(); 
            usuarioDAO.actualizar(familia);
        }
        SolicitudAdopcion solicitud = new SolicitudAdopcion(familia, gato);
        solicitudDAO.guardar(solicitud);
        gato.reservado(); 
        gatoDAO.actualizar(gato);
    }
    
    // Metodo para panel de solicitudes admin/voluntario
    public void aprobarSolicitud(SolicitudAdopcion solicitud, int tipoAdopcion) throws Exception {
        if (solicitud == null || solicitud.getEstado() != 0) { 
            throw new Exception("La solicitud no es valida o ya ha sido procesada.");
        }
        Gato gato = solicitud.getGato();
        Familia familia = solicitud.getFamilia();
        Hogar_Adopcion hogar = hogarDAO.buscarPorDniFamilia(familia.getDNI());
        if (gato == null) throw new Exception("El gato asociado a esta solicitud ya no existe.");
        if (hogar == null) throw new Exception("La familia de esta solicitud no tiene un hogar registrado.");
        Zona_geografica zonaDelHogar = buscarZonaDelHogar(hogar);
        if (zonaDelHogar == null) {
            System.err.println("Error: No se encontro una zona para el Hogar ID: " + hogar.getIdhogarfam());
        }
        switch (tipoAdopcion) {
            case 1: 
                if (!hogar.getGatos().isEmpty() && !hogar.isAprobado()) {
                    throw new Exception("El hogar no esta aprobado y ya tiene un gato en transicion. No puede adoptar mas.");
                }
                if (hogar.isAprobado()) throw new Exception("El hogar ya esta aprobado. Debe ser adopcion definitiva.");
                gato.transicion(); 
                break;
            case 2: 
                if (!hogar.isAprobado()) throw new Exception("El hogar no esta aprobado. Debe ser adopcion temporal.");
                gato.adoptado();
                break;
            default:
                throw new Exception("Tipo de adopcion invalido.");
        }
        hogar.agregarGato(gato);
        solicitud.setEstado(1); 
        gato.setZona(zonaDelHogar);
        familia.baja_postulado();
        gatoDAO.actualizar(gato);
        hogarDAO.actualizar(hogar);
        solicitudDAO.actualizar(solicitud);
        usuarioDAO.actualizar(familia);
    }
    public void rechazarSolicitud(SolicitudAdopcion solicitud) throws Exception {
        if (solicitud == null || solicitud.getEstado() != 0) {
            throw new Exception("La solicitud no es valida o ya ha sido procesada.");
        }
        Gato gato = solicitud.getGato();
        solicitud.setEstado(2); 
        solicitudDAO.actualizar(solicitud);
        if (gato != null && gato.getAdoptado().equals("Reservado")) {
            gato.libre(); 
            gatoDAO.actualizar(gato);
        }
    }
    
}
