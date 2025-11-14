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

import modelo.Gato;
import modelo.Zona_geografica;
import java.util.List;
import dao.DiagnosticoDAO;
import modelo.Diagnosticos;
import dao.TareaDAO;
import java.util.UUID;
import modelo.Tarea;
import utilidades.GeneradorQR;

public class Gestor_gatos {

    private GatoDAO gatoDAO;
    private DiagnosticoDAO diagnosticoDAO;
    private TareaDAO tareaDAO;

    public Gestor_gatos(GatoDAO gatoDAO,DiagnosticoDAO diagnosticoDAO,TareaDAO tareaDAO) {
        this.gatoDAO = gatoDAO;
        this.diagnosticoDAO = diagnosticoDAO;
        this.tareaDAO = tareaDAO;
       
    }

    //CRUD GATOS
    
    public void registrarGatos(String nombre, String color, String descripcion, Zona_geografica zona,int edad, String fotoUrl) throws Exception {
      if (color == null || color.trim().isEmpty()) {
            throw new Exception("El color no puede estar vacio");
        }
        if (zona == null) {
            throw new Exception("Debe seleccionar una zona valida");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Sin Nombre"; 
        }
        //Para hacer QR
        String uniqueID = UUID.randomUUID().toString();
        String qrText = "https://unsplash.com/es/s/fotos/gatos-graciosos" + uniqueID; //futuro JSP pero por ahora cualquier cosa
        String qrFilePath = "QRS/gato_" + uniqueID + ".png";
        try{
        GeneradorQR.generateQRCodeImage(qrText, 250, 250, qrFilePath);
        }
        catch(Exception e){
            System.out.println("Error creando QR");
        }
        Gato nuevogato = new Gato(nombre, color, descripcion);
         nuevogato.setZona(zona); 
         nuevogato.setEdad(edad);
        nuevogato.setFotoUrl(fotoUrl);
        nuevogato.setQrUrl(qrFilePath);
        
            gatoDAO.guardar(nuevogato);

    }
    
    public List<Gato> listarGatos() {
        return gatoDAO.listarTodos();
    }

    
    public void eliminarGato(Gato gato)throws Exception {
        if (gato == null) {
            throw new Exception("No se ha seleccionado ningún gato.");
        }
        List<Diagnosticos> historial = diagnosticoDAO.buscarPorGato(gato);
        if (!historial.isEmpty()) {
            throw new Exception("No se puede eliminar el gato '" + gato.getNombre() + " porque tiene " + historial.size() + " diagnostico(s) asociado(s)");
        }
            List<Tarea> tareas = tareaDAO.buscarPorGato(gato);
        if (!tareas.isEmpty()) {
            throw new Exception("No se puede eliminar el gato " + gato.getNombre() + " porque tiene " + tareas.size() + " tarea(s) asociada(s).");
        }
        if (gato.getHogar() != null) {
             throw new Exception("No se puede eliminar el gato " + gato.getNombre() + " porque esta asignado a un hogar. Cancele la adopción primero.");
        }
        
        gatoDAO.eliminar(gato);
        
    }

    
    public Gato buscarGatoID(int idGato) {
        return gatoDAO.buscarPorId(idGato);
        
    }
    
    public List<Gato> listarGatosDisponibles() {
        return gatoDAO.listarDisponibles();
    }
    public void actualizarGato(Gato gatoAModificar, String nuevoNombre, String nuevoColor, String nuevaDescripcion, Zona_geografica nuevaZona,int nuevaEdad, String nuevaFotoUrl) throws Exception {
        if (gatoAModificar == null) {
            throw new Exception("Error: No se ha seleccionado un gato valido para modificar.");
        }
        if (nuevoColor == null || nuevoColor.trim().isEmpty()) {
            throw new Exception("El color no puede estar vacio.");
        }
        if (nuevaZona == null) {
            throw new Exception("Debe seleccionar una zona valida.");
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            nuevoNombre = "Sin Nombre"; 
        }
        gatoAModificar.setNombre(nuevoNombre);
        gatoAModificar.setColor(nuevoColor);
        gatoAModificar.setDescripcion(nuevaDescripcion);
        gatoAModificar.setZona(nuevaZona);
        gatoAModificar.setEdad(nuevaEdad);
        gatoAModificar.setFotoUrl(nuevaFotoUrl);
        
        gatoDAO.actualizar(gatoAModificar);
        
    }
    public void marcarEsterilizado(Gato gato) throws Exception {
        if (gato == null) {
            throw new Exception("No se ha seleccionado un gato valido.");
        }
        
        gato.esterilizado(); 
        gatoDAO.actualizar(gato); 
    }
 
}
        

