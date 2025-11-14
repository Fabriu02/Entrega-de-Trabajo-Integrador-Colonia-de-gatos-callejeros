/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author Fabri
 */
import modelo.Usuario;
import servicio.Swingcontexto; 
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import vista.PanelZonas;
import vista.PanelGestionGatos;
import vista.PanelDiagnosticos;
import vista.PanelTareas;
import vista.Panelvisitas;
import vista.PanelAdopcion;
import vista.PanelGestorUsuarios;
import vista.PanelHogares;
import vista.PanelCalendario;
import vista.PanelFamilia;
import vista.VerGatosFamilia;


public class VentanaPrincipal extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());
    private final Swingcontexto servicios;
    private final Usuario usuarioLogueado;
    private final EntityManagerFactory emf;
    private final EntityManager em;
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal(Swingcontexto servicios, Usuario usuarioLogueado, EntityManagerFactory emf, EntityManager em) {
        this.servicios = servicios;
        this.usuarioLogueado = usuarioLogueado;
        this.emf = emf;
        this.em = em;
        initComponents();
        configurarVentana();
        cargarPanelesPorRol();
    }
    
    private void configurarVentana() {
        this.setMinimumSize(new java.awt.Dimension(800, 600));
        this.setLocationRelativeTo(null); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setTitle("Colonia de Gatos - Sesion actual :" + usuarioLogueado.getNombre() + " (" + usuarioLogueado.nombrerol() + ")");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               confirmarSalida();
            }
        });
    }
    private void cargarPanelesPorRol() {
        int rol = usuarioLogueado.getRol();
        

        // ADMINISTRADOR
        if (rol == 1) {
            PanelPrincipal.addTab("Gestionar Zonas", new PanelZonas(servicios)); 
            PanelPrincipal.addTab("Gestionar Tareas", new PanelTareas(servicios, usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Visitas", new Panelvisitas(servicios, usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Usuarios", new PanelGestorUsuarios(servicios));
            PanelPrincipal.addTab("Gestionar Hogares", new PanelHogares(servicios,usuarioLogueado));
            PanelPrincipal.addTab("CALENDARIO", new PanelCalendario(servicios,usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Gatos", new PanelGestionGatos(servicios, usuarioLogueado));
        }
        
        // VOLUNTARIO
        if (rol == 3) {
            PanelPrincipal.addTab("Gestionar Gatos", new PanelGestionGatos(servicios, usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Tareas", new PanelTareas(servicios, usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Visitas", new Panelvisitas(servicios, usuarioLogueado));
            PanelPrincipal.addTab("Gestionar Adopciones", new PanelAdopcion(servicios, usuarioLogueado));
             PanelPrincipal.addTab("CALENDARIO", new PanelCalendario(servicios,usuarioLogueado));
        }

        //  VETERINARIO
        if (rol == 2) {
            PanelPrincipal.addTab("Gestionar Diagnosticos", new PanelDiagnosticos(servicios, usuarioLogueado));
        }

        //  FAMILIA
        if (rol == 4) {
             PanelPrincipal.addTab("MI HOGAR", new PanelFamilia(servicios, usuarioLogueado));
             PanelPrincipal.addTab("Ver Gatos Disponibles", new VerGatosFamilia(servicios, usuarioLogueado));
        }
        
        if (PanelPrincipal.getTabCount() == 0) {
            JPanel panelVacio = new JPanel();
            panelVacio.add(new javax.swing.JLabel("No hay modulos disponibles para tu rol."));
            PanelPrincipal.addTab("Inicio", panelVacio);
        }
    }
   
    private void confirmarSalida() {
        Object[] opciones = {"Cerrar Sesion", "Salir de la Aplicacion", "Cancelar"};
        
        int n = JOptionPane.showOptionDialog(this, "Que deseas hacer?", "Confirmar Salida", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null,opciones, opciones[2]);
        switch (n) {
            case 0: 
                cerrarSesion();
                break;
            case 1:
                cerrarAplicacionCompleta();
                break;
            case 2:
              
                break;
        }
    }
    private void cerrarSesion() {
        this.dispose(); 
        new LoginVista(servicios, emf, em).setVisible(true); 
    }
    private void cerrarAplicacionCompleta() {
        System.out.println("Cerrando conexión JPA...");
        if (em != null) em.close();
        if (emf != null) emf.close();
        System.out.println("Conexión cerrada.");
        System.exit(0); // Cierra el programa
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(PanelPrincipal, java.awt.BorderLayout.CENTER);
        PanelPrincipal.getAccessibleContext().setAccessibleName("");
        PanelPrincipal.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane PanelPrincipal;
    // End of variables declaration//GEN-END:variables
}
