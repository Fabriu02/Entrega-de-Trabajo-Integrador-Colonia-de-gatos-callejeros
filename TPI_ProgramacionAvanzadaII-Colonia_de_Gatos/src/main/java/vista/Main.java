package vista;


import dao.*; 
import servicio.*; 
import modelo.*; 
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.swing.SwingUtilities;
public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("Colonia-de-Gatos");
            em = emf.createEntityManager();
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);  // por si no se conecta
        }
        //Ini DAOS
        UsuarioDAO usuarioDAO = new UsuarioDAO(em);
        GatoDAO gatoDAO = new GatoDAO(em);
        ZonaDAO zonaDAO = new ZonaDAO(em);
        HogarDAO hogarDAO = new HogarDAO(em);
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO(em);
        TareaDAO tareaDAO = new TareaDAO(em);
        VisitaDAO visitaDAO = new VisitaDAO(em);
        SolicitudDAO solicitudDAO = new SolicitudDAO(em);
        
        //Ini servicios
        GestorUsuario gestorUsuarios = new GestorUsuario(usuarioDAO,diagnosticoDAO,tareaDAO,visitaDAO,hogarDAO);
        Listado_zonas zonaService = new Listado_zonas(zonaDAO,gatoDAO); 
        Gestor_gatos gestorGatos = new Gestor_gatos(gatoDAO,diagnosticoDAO,tareaDAO);
        Gestion_hogares gestionHogares = new Gestion_hogares(hogarDAO, usuarioDAO,gatoDAO,zonaService);
        Gestor_diagnosticos diagnosticoService = new Gestor_diagnosticos(diagnosticoDAO, gatoDAO, usuarioDAO); 
        Gestion_tareas tareaService = new Gestion_tareas(tareaDAO, gatoDAO, usuarioDAO); 
        Gestion_visitas gestionVisitas = new Gestion_visitas(visitaDAO, usuarioDAO, hogarDAO);
        Adopcion_gato adopcionService = new Adopcion_gato(gatoDAO,hogarDAO,solicitudDAO,usuarioDAO,zonaService); 
        Gestor_Calendario calendarioService = new Gestor_Calendario(diagnosticoDAO, tareaDAO, visitaDAO); 
        Gestor_reportes gestorReportes = new Gestor_reportes(gatoDAO, zonaDAO);
        

        //Swing
        Swingcontexto servicios = new Swingcontexto(gestorUsuarios,gestorGatos,zonaService,gestionHogares,diagnosticoService,tareaService,gestionVisitas,adopcionService,calendarioService,gestorReportes);
        final EntityManagerFactory finalEmf = emf;
        final EntityManager finalEm = em;
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginVista(servicios,finalEmf, finalEm).setVisible(true);
            }
        });
    }
        

        
} 

