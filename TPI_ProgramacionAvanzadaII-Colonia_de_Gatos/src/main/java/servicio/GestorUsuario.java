package servicio;

import dao.UsuarioDAO;
import modelo.*;
import dao.DiagnosticoDAO; 
import dao.TareaDAO; 
import dao.VisitaDAO; 
import dao.HogarDAO;
import java.util.List; 
public class GestorUsuario {

    private UsuarioDAO usuarioDAO;
    private DiagnosticoDAO diagnosticoDAO;
    private TareaDAO tareaDAO;
    private VisitaDAO visitaDAO;
    private HogarDAO hogarDAO;
    
    public GestorUsuario(UsuarioDAO usuarioDAO, DiagnosticoDAO diagnosticoDAO,TareaDAO tareaDAO, VisitaDAO visitaDAO, HogarDAO hogarDAO) {
        this.usuarioDAO = usuarioDAO;
        this.diagnosticoDAO = diagnosticoDAO;
        this.tareaDAO = tareaDAO;
        this.visitaDAO = visitaDAO;
        this.hogarDAO = hogarDAO;
    }

    public Usuario loguear(String dni, String clave) throws DatosInvalidosException {
        Usuario usuario = usuarioDAO.buscarPorDNI(dni);
        if (usuario == null) {
            throw new DatosInvalidosException(" DNI no encontrado.");
        }

        if (usuario.getClave().equals(clave)) {
            return usuario; 
        } else {
            throw new DatosInvalidosException(" Contraseña incorrecta.");
        }
    }

    //CRUD USUARIO
    public void registrar_usuario(String DNI, String nombre, String correo, String telefono, String clave, int rol, String matricula) throws Exception {
        if (DNI == null || DNI.trim().isEmpty()) {
            throw new Exception("El DNI no puede estar vacio.");
        }
        if (usuarioDAO.buscarPorDNI(DNI) != null) {
            throw new Exception("Error: Ya existe un usuario con ese DNI.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacio.");
        }
        if (clave == null || clave.trim().isEmpty()) {
            throw new Exception("La clave no puede estar vacia.");
        }

        Usuario nuevousuario = null;
        switch (rol) {
            case 1: // Admin
                nuevousuario = new Administrador(DNI, clave, telefono, rol, correo, nombre);
                break;
            case 2: // Veterinario
                if (matricula == null || matricula.trim().isEmpty()) {
                    throw new Exception("La matricula es obligatoria para veterinarios.");
                }
                nuevousuario = new Veterinario(DNI, clave, telefono, rol, correo, nombre, matricula);
                break;
            case 3: // Voluntario
                nuevousuario = new Voluntario(DNI, clave, telefono, rol, correo, nombre);
                break;
            case 4: // Familia
                nuevousuario = new Familia(DNI, clave, telefono, rol, correo, nombre);
                break;
            default:
                throw new Exception("Rol seleccionado invalido.");
        }

        if (nuevousuario != null) {
            usuarioDAO.guardar(nuevousuario);
        }
        
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
       return usuarios;
    }

    public void modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoCorreo, String nuevoTelefono, String nuevaClave, String nuevaMatricula)throws Exception {
       if (usuario == null) {
            throw new Exception("No se ha seleccionado un usuario valido.");
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacio.");
        }
        usuario.setNombre(nuevoNombre);
        usuario.setCorreo(nuevoCorreo);
        usuario.setTelefono(nuevoTelefono);
        usuario.setClave(nuevaClave);
        if (usuario instanceof Veterinario) {
            if (nuevaMatricula == null || nuevaMatricula.trim().isEmpty()) {
                 throw new Exception("La matricula es OBLIGATORIA para los veterinarios");
            }
            ((Veterinario) usuario).setMatricula(nuevaMatricula);
        }

        usuarioDAO.actualizar(usuario);
    }

   
    public void eliminarUsuario(Usuario usuario)throws Exception {
      if (usuario == null) {
            throw new Exception("No se ha seleccionado un usuario valido.");
        }
            if (usuario instanceof Administrador) {
                // (Necesito agregar despues un método en UsuarioDAO para contar admins) --> asi no borro todos los admin
                // List<Usuario> admins = usuarioDAO.listarPorRol(1); // (O filtrar la lista completa)
                // if (admins.size() <= 1) {
                //     throw new Exception("No se puede eliminar al último administrador del sistema.");
                // }
            }
                if (usuario instanceof Veterinario) {
                    List<Diagnosticos> diagnosticos = diagnosticoDAO.buscarPorVeterinario((Veterinario) usuario);
                        if (!diagnosticos.isEmpty()) {
                            throw new Exception("No se puede eliminar al veterinario '" + usuario.getNombre() +"' porque tiene " + diagnosticos.size() + " diagnostico/s asociado/s.");
                        }
                }
                    if (usuario instanceof Voluntario) {
                        List<Tarea> tareas = tareaDAO.buscarPorVoluntario((Voluntario) usuario);
                            if (!tareas.isEmpty()) {
                                throw new Exception("No se puede eliminar al voluntario '" + usuario.getNombre()+"' porque tiene " + tareas.size() + " tarea/s asociada/s.");
                            }
                                List<Visita> visitas = visitaDAO.buscarPorVoluntario((Voluntario) usuario);
                                if (!visitas.isEmpty()) {
                                    throw new Exception("No se puede eliminar al voluntario '" + usuario.getNombre() + "' porque tiene " + visitas.size() + " visita/s asociada/s.");
                                }
                    }
                        if (usuario instanceof Familia) {
                                Hogar_Adopcion hogar = hogarDAO.buscarPorDniFamilia(usuario.getDNI());
                                if (hogar != null) {
                                    throw new Exception("No se puede eliminar la familia '" + usuario.getNombre() +"' porque tiene un hogar (ID: " + hogar.getIdhogarfam() + ") asociado.");
                                }
                        }
                         usuarioDAO.eliminar(usuario);
    }

    //MODIFICAR REPUTACIONES 
    
    public void modificarReputacionVoluntario(Usuario usuario, int nuevoValor)throws Exception {
       if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }
        if (!(usuario instanceof Voluntario)) {
            throw new Exception("El DNI no pertenece a un voluntario.");
        }
        
        Voluntario vol = (Voluntario) usuario;
        vol.setReputacion(nuevoValor); 
        usuarioDAO.actualizar(vol);
    }

    
    public void modificarReputacionFamilia(Usuario usuario, int nuevoValor)throws Exception {
       if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }
        if (!(usuario instanceof Familia)) {
            throw new Exception("El DNI no pertenece a una familia.");
        }
        
        Familia fam = (Familia) usuario;
        fam.setReputacion(nuevoValor); 
        usuarioDAO.actualizar(fam); 
    }
    public Usuario UsuarioporDNI(String DNIbus){
        return usuarioDAO.buscarPorDNI(DNIbus);
    }
    
}