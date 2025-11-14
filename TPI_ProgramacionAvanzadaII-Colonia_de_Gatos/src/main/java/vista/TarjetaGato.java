/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Fabri
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.Familia;
import modelo.Gato;
import servicio.Swingcontexto;
import java.net.URL; 
import javax.imageio.ImageIO;
        
public class TarjetaGato extends JPanel {
    private Gato gato;
    private Familia familiaLogueada;
    private Swingcontexto servicios;
    private JLabel lblFoto;
    private JLabel lblNombre;
    private JLabel lblInfo;
    private JButton btnSolicitar;
        public TarjetaGato(Gato gato, Familia familiaLogueada, Swingcontexto servicios) {
        this.gato = gato;
        this.familiaLogueada = familiaLogueada;
        this.servicios = servicios;
        initComponents();
        cargarDatos();
    }
        private void initComponents() {
        setLayout(new BorderLayout(5, 5)); 
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); 
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 400)); 

        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(300, 212)); 
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setText("foto");
        add(lblFoto, BorderLayout.NORTH);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); 
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        infoPanel.setBackground(Color.WHITE);
        lblNombre = new JLabel(gato.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(lblNombre);
        lblInfo = new JLabel(); 
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoPanel.add(lblInfo);
        add(infoPanel, BorderLayout.CENTER);
        JPanel botonPanel = new JPanel(new BorderLayout());
        botonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        botonPanel.setBackground(Color.WHITE);
        
        btnSolicitar = new JButton("Solicitar Adopcion");
        btnSolicitar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        btnSolicitar.addActionListener((ActionEvent e) -> {
            btnSolicitarActionPerformed();
        });
        
        botonPanel.add(btnSolicitar, BorderLayout.CENTER);
        add(botonPanel, BorderLayout.SOUTH);
    }
    private void cargarDatos() {
        lblInfo.setText("<html>Edad: " + gato.getEdad() + " meses<br>Color: " + gato.getColor() + "<br>Descripcion: " + gato.getDescripcion() +"</html>");
        String fotoPath = gato.getFotoUrl();
        if (fotoPath != null && !fotoPath.isEmpty()) {
            try {
                URL imageUrl = new URL(fotoPath);
                Image image = ImageIO.read(imageUrl);
                if (image != null) {
                    Image scaledImage = image.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
                    
                    lblFoto.setIcon(new ImageIcon(scaledImage));
                    lblFoto.setText(null); 
                } else {
                    lblFoto.setText("Foto no disponible");
                }
            } catch (Exception e) {
                lblFoto.setText("Foto no disponible");
                System.err.println("Error cargando imagen: " + fotoPath + " - " + e.getMessage());
            }
        } else {
            lblFoto.setText("Foto no disponible");
        }
    }
    private void btnSolicitarActionPerformed() {
        try {
            int confirm = JOptionPane.showConfirmDialog(this," Seguro de que deseas enviar una solicitud de adopcion para " + gato.getNombre() + "?\n" +"El gato quedara 'Reservado' hasta que un voluntario revise tu solicitud.","Confirmar Solicitud",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                servicios.adopcionService.crearSolicitud(gato, familiaLogueada);
                JOptionPane.showMessageDialog(this,"Solicitud enviada \nEl gato esta ahora reservado para ti.\n" +"Un voluntario/administrador revisara tu caso.","exito",JOptionPane.INFORMATION_MESSAGE);
                btnSolicitar.setText("Solicitud Pendiente");
                btnSolicitar.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error al enviar la solicitud: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
