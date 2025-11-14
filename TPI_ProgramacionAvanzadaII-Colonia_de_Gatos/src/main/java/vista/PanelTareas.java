/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

/**
 *
 * @author Fabri
 */
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Gato;
import modelo.Tarea;
import modelo.Usuario;
import modelo.Voluntario;
import modelo.Zona_geografica; 
import servicio.Swingcontexto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;

public class PanelTareas extends javax.swing.JPanel {
    private Swingcontexto servicios;
    private Usuario usuarioLogueado;
    private DefaultTableModel modeloTabla;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    /**
     * Creates new form PanelTareas
     */
    public PanelTareas(Swingcontexto servicios, Usuario usuarioLogueado) {
        this.servicios = servicios;
        this.usuarioLogueado = usuarioLogueado;
        initComponents();
        configurarTabla();
        cargarDatosTabla(servicios.tareaService.listarTareas());
         cargarVoluntariosComboBox();
    }
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        
        modeloTabla.addColumn("ID Tarea");
        modeloTabla.addColumn("Fecha Limite");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Gato");
        modeloTabla.addColumn("Voluntario");
        modeloTabla.addColumn("Estado");
        
        tablaTareas.setModel(modeloTabla); 
    }
    private void cargarDatosTabla(List<Tarea> tareas) {
        modeloTabla.setRowCount(0); 
        
        for (Tarea t : tareas) {
            String tipoStr = switch (t.getTipo()) {
                case 1 -> "Alimentacion";
                case 2 -> "Captura";
                case 3 -> "Transporte";
                case 4 -> "Control Vet.";
                default -> "N/A";
            };
            String estadoStr = switch (t.getEstado()) {
                case 0 -> "Pendiente";
                case 1 -> "Completada";
                case 2 -> "Incompleta";
                default -> "N/A";
            };
            
            Object[] fila = {
                t.getIdtarea(),
                (t.getFechahora() != null) ? t.getFechahora().format(FORMATO_FECHA) : "N/A",
                tipoStr,
                (t.getGato() != null) ? t.getGato().getNombre() : "N/A",
                (t.getVoluntario() != null) ? t.getVoluntario().getNombre() : "Sin Asignar",
                estadoStr
            };
            modeloTabla.addRow(fila);
        }
    }
    private void cargarVoluntariosComboBox() {
        List<Usuario> usuarios = servicios.usuarioService.listarUsuarios();
        
        DefaultComboBoxModel<Usuario> comboModel = new DefaultComboBoxModel<>();
        comboModel.addElement(null); 
        for (Usuario u : usuarios) {
            if (u instanceof Voluntario) {
                comboModel.addElement((Voluntario) u); 
            }
        }
        cmbVoluntarios.setRenderer(new VoluntarioComboBoxRenderer());
        cmbVoluntarios.setModel(comboModel);
    }
    private JComboBox<Gato> crearComboGatos() {
        JComboBox<Gato> cmbGatos = new JComboBox<>();
        List<Gato> gatos = servicios.gatoService.listarGatos();
        DefaultComboBoxModel<Gato> comboModel = new DefaultComboBoxModel<>();
        comboModel.addElement(null); 
        for (Gato g : gatos) {
            comboModel.addElement(g);
        }
        cmbGatos.setModel(comboModel);
        cmbGatos.setRenderer(new GatoComboBoxRenderer()); 
        return cmbGatos;
    }
    private JComboBox<Object> crearComboVoluntarios() {
        JComboBox<Object> cmbVol = new JComboBox<>();
        List<Usuario> usuarios = servicios.usuarioService.listarUsuarios(); 
        DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
        
        comboModel.addElement("Dejar vacio"); 
        for (Usuario u : usuarios) {
            if (u instanceof Voluntario) {
                comboModel.addElement((Voluntario) u);
            }
        }
        cmbVol.setModel(comboModel);
        cmbVol.setRenderer(new VoluntarioComboBoxRenderer());
        return cmbVol;
    }
    private Tarea obtenerTareaDeTabla() throws Exception {
         int filaSel = tablaTareas.getSelectedRow();
         if (filaSel == -1) {
             throw new Exception("Debe seleccionar una tarea de la tabla.");
         }
         int idTarea = (int) modeloTabla.getValueAt(filaSel, 0); 
         Tarea tarea = servicios.tareaService.buscarTareaPorId(idTarea);
         if (tarea == null) {
             cargarDatosTabla(servicios.tareaService.listarTareas());
             throw new Exception("La tarea seleccionada ya no existe. La tabla ha sido actualizada.");
         }
         return tarea;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTareas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdTarea = new javax.swing.JTextField();
        MODIFICAR = new javax.swing.JButton();
        BUSCAR = new javax.swing.JButton();
        AÑADIR = new javax.swing.JButton();
        ELIMINAR = new javax.swing.JButton();
        EVALUAR = new javax.swing.JButton();
        cmbVoluntarios = new javax.swing.JComboBox<>();
        VER_SIN_ASIGNADOS = new javax.swing.JButton();
        ACTUALIZARLISTA = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GESTOR TAREAS", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        setLayout(new java.awt.BorderLayout());

        tablaTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaTareas);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("INGRESE ID");

        txtIdTarea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtIdTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdTareaActionPerformed(evt);
            }
        });

        MODIFICAR.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        MODIFICAR.setText("MODIFICAR");
        MODIFICAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MODIFICARActionPerformed(evt);
            }
        });

        BUSCAR.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        BUSCAR.setText("BUSCAR");
        BUSCAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUSCARActionPerformed(evt);
            }
        });

        AÑADIR.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        AÑADIR.setText("AÑADIR");
        AÑADIR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AÑADIRActionPerformed(evt);
            }
        });

        ELIMINAR.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ELIMINAR.setText("ELIMINAR");
        ELIMINAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINARActionPerformed(evt);
            }
        });

        EVALUAR.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        EVALUAR.setText("EVALUAR");
        EVALUAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EVALUARActionPerformed(evt);
            }
        });

        cmbVoluntarios.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cmbVoluntarios.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbVoluntariosItemStateChanged(evt);
            }
        });
        cmbVoluntarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVoluntariosActionPerformed(evt);
            }
        });

        VER_SIN_ASIGNADOS.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        VER_SIN_ASIGNADOS.setText("VER SIN ASIGNADOS");
        VER_SIN_ASIGNADOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VER_SIN_ASIGNADOSActionPerformed(evt);
            }
        });

        ACTUALIZARLISTA.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ACTUALIZARLISTA.setText("ACT LISTA");
        ACTUALIZARLISTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTUALIZARLISTAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BUSCAR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbVoluntarios, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VER_SIN_ASIGNADOS)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AÑADIR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MODIFICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ELIMINAR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EVALUAR, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addComponent(ACTUALIZARLISTA)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BUSCAR)
                    .addComponent(cmbVoluntarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VER_SIN_ASIGNADOS))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AÑADIR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MODIFICAR)
                        .addComponent(ELIMINAR)
                        .addComponent(EVALUAR)
                        .addComponent(ACTUALIZARLISTA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdTareaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdTareaActionPerformed

    private void MODIFICARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MODIFICARActionPerformed
        try {
            Tarea tarea = obtenerTareaDeTabla();
            JComboBox<Gato> cmbGatos = crearComboGatos();
            cmbGatos.setSelectedItem(tarea.getGato());
            
            JComboBox<Object> cmbVol = crearComboVoluntarios();
            cmbVol.setSelectedItem(tarea.getVoluntario());
            
            JTextField txtFecha = new JTextField(tarea.getFechahora().format(FORMATO_FECHA));
            JTextField txtCoords = new JTextField(tarea.getCoordenadas());
            JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Alimentacion", "Captura", "Transporte", "Control Vet."});
            cmbTipo.setSelectedIndex(tarea.getTipo() - 1);

            JPanel dialogPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            dialogPanel.add(new JLabel("Gato:"));
            dialogPanel.add(cmbGatos);
            dialogPanel.add(new JLabel("Voluntario:"));
            dialogPanel.add(cmbVol);
            dialogPanel.add(new JLabel("Fecha Limite:"));
            dialogPanel.add(txtFecha);
            dialogPanel.add(new JLabel("Tipo de Tarea:"));
            dialogPanel.add(cmbTipo);
            dialogPanel.add(new JLabel("Coordenadas:"));
            dialogPanel.add(txtCoords);

            int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Modificar Tarea #" + tarea.getIdtarea(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    Gato gatoSel = (Gato) cmbGatos.getSelectedItem();
                    Voluntario volSel = (cmbVol.getSelectedItem() instanceof Voluntario) ? (Voluntario) cmbVol.getSelectedItem() : null;
                    String fechaStr = txtFecha.getText();
                    String coords = txtCoords.getText();
                    int tipo = cmbTipo.getSelectedIndex() + 1;
                    servicios.tareaService.actualizarTarea(tarea, volSel, gatoSel, fechaStr, tipo, coords);

                    JOptionPane.showMessageDialog(this, "Tarea actualizada.", "exito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla(servicios.tareaService.listarTareas());
                }
        }   
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_MODIFICARActionPerformed

    private void BUSCARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUSCARActionPerformed
       try {
            int selectedIndex = cmbVoluntarios.getSelectedIndex();
            if (selectedIndex > 0) { 
                 throw new Exception("Limpia el filtro de Voluntarios (pon 'ver todos') antes de buscar por ID.");
            }
            
            String idStr = txtIdTarea.getText().trim();
            if (idStr.isEmpty()) {
                throw new Exception(" ingrese un ID de Tarea para buscar.");
            }
            
            int idTarea = Integer.parseInt(idStr);
            Tarea tarea = servicios.tareaService.buscarTareaPorId(idTarea);
            
            if (tarea != null) {
                List<Tarea> listaBusqueda = new ArrayList<>();
                listaBusqueda.add(tarea);
                cargarDatosTabla(listaBusqueda); 
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro ninguna tarea con el ID: " + idTarea, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID  valido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al Buscar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BUSCARActionPerformed

    private void AÑADIRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AÑADIRActionPerformed
        JComboBox<Gato> cmbGatos = crearComboGatos();
        JComboBox<Object> cmbVol = crearComboVoluntarios(); 
        JTextField txtFecha = new JTextField(LocalDateTime.now().plusDays(1).format(FORMATO_FECHA)); 
        JTextField txtCoords = new JTextField();
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Alimentacion", "Captura", "Transporte", "Control Vet."});
        JPanel dialogPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        dialogPanel.add(new JLabel("Gato:"));
        dialogPanel.add(cmbGatos);
        dialogPanel.add(new JLabel("Voluntario:"));
        dialogPanel.add(cmbVol);
        dialogPanel.add(new JLabel("Fecha Límite (dd/MM/yyyy HH:mm):"));
        dialogPanel.add(txtFecha);
        dialogPanel.add(new JLabel("Tipo de Tarea:"));
        dialogPanel.add(cmbTipo);
        dialogPanel.add(new JLabel("Coordenadas:"));
        dialogPanel.add(txtCoords);
        int result = JOptionPane.showConfirmDialog(
                this, dialogPanel, "Crear Nueva Tarea",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                Gato gatoSel = (Gato) cmbGatos.getSelectedItem();
                Voluntario volSel = null;
                if (cmbVol.getSelectedItem() instanceof Voluntario) {
                    volSel = (Voluntario) cmbVol.getSelectedItem();
                } 
                String fechaStr = txtFecha.getText();
                String coords = txtCoords.getText();
                int tipo = cmbTipo.getSelectedIndex() + 1; 
                servicios.tareaService.registrarTarea(volSel, gatoSel, fechaStr, tipo, coords);
                JOptionPane.showMessageDialog(this, "Tarea creada con exito.", "exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla(servicios.tareaService.listarTareas()); 

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_AÑADIRActionPerformed

    private void ELIMINARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELIMINARActionPerformed
        try {
            Tarea tarea = obtenerTareaDeTabla();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que deseas eliminar la Tarea #" + tarea.getIdtarea() + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                servicios.tareaService.eliminarTarea(tarea);
                JOptionPane.showMessageDialog(this, "Tarea eliminada.", "exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla(servicios.tareaService.listarTareas()); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_ELIMINARActionPerformed

    private void EVALUARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EVALUARActionPerformed
       try {
            Tarea tarea = obtenerTareaDeTabla();
            if (usuarioLogueado.getRol() == 3 && tarea.getVoluntario() != usuarioLogueado) {
                JOptionPane.showMessageDialog(this, "Como voluntario, solo puedes evaluar tus propias tareas.", "Accion Denegada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String[] opciones = {"Marcar como Finalizada", "Marcar como Incompleta"};
            int seleccion = JOptionPane.showOptionDialog(this, "Como deseas evaluar la Tarea #" + tarea.getIdtarea() + "?", "Evaluar Tarea", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            int nuevoEstado = -1;
            if (seleccion == 0) nuevoEstado = 1; 
            if (seleccion == 1) nuevoEstado = 2; 
            if (nuevoEstado != -1) {
                servicios.tareaService.evaluarTarea(tarea, nuevoEstado);
                JOptionPane.showMessageDialog(this, "Tarea evaluada. Reputacion actualizada.", "exito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla(servicios.tareaService.listarTareas()); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al evaluar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EVALUARActionPerformed

    private void VER_SIN_ASIGNADOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VER_SIN_ASIGNADOSActionPerformed
     try {
            List<Tarea> sinAsignar = servicios.tareaService.listarTareasSinAsignar();

            if (sinAsignar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay tareas sin asignar.", "Tareas Pendientes", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringBuilder reporte = new StringBuilder();
            reporte.append("--- Tareas Pendientes Sin Asignar ---\n\n");
            
            for (Tarea t : sinAsignar) {
                String tipoStr = switch (t.getTipo()) {
                    case 1 -> "Alimentación";
                    case 2 -> "Captura";
                    case 3 -> "Transporte";
                    case 4 -> "Control Vet.";
                    default -> "N/A";
                };
                
                reporte.append("ID Tarea: ").append(t.getIdtarea());
                reporte.append(" | Gato: ").append(t.getGato() != null ? t.getGato().getNombre() : "N/A");
                reporte.append(" | Tipo: ").append(tipoStr);
                reporte.append("\n (Fecha Limite: ").append(t.getFechahora().format(FORMATO_FECHA)).append(")\n\n");
            }
            JTextArea textArea = new JTextArea(reporte.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300)); 

            JOptionPane.showMessageDialog(
                this, 
                scrollPane, 
                "Tareas Sin Asignar (" + sinAsignar.size() + ")", 
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tareas sin asignar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_VER_SIN_ASIGNADOSActionPerformed

    private void ACTUALIZARLISTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACTUALIZARLISTAActionPerformed
       cargarDatosTabla(servicios.tareaService.listarTareas());
        txtIdTarea.setText("");
        cmbVoluntarios.setSelectedIndex(0);
    }//GEN-LAST:event_ACTUALIZARLISTAActionPerformed

    private void cmbVoluntariosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbVoluntariosItemStateChanged
      if (evt.getStateChange() == ItemEvent.SELECTED) {
 
            if (!txtIdTarea.getText().trim().isEmpty()) {JOptionPane.showMessageDialog(this, "Limpia el campo 'INGRESE ID' antes de filtrar por voluntario.", "Conflicto de Búsqueda", JOptionPane.WARNING_MESSAGE);
                cmbVoluntarios.setSelectedIndex(0); 
                cargarDatosTabla(servicios.tareaService.listarTareas());
                return;
            }
            
            Object item = cmbVoluntarios.getSelectedItem();
            
            if (item instanceof Voluntario) {
                Voluntario vol = (Voluntario) item;
                cargarDatosTabla(servicios.tareaService.listarTareasPorVoluntario(vol));
            } else if (item instanceof String && ((String) item).equals("VER SIN ASIGNADOS")) {
                cargarDatosTabla(servicios.tareaService.listarTareasSinAsignar());
            } else {
                // Filtra por "VER TODOS" (o default)
                cargarDatosTabla(servicios.tareaService.listarTareas());
            }
        }
    }//GEN-LAST:event_cmbVoluntariosItemStateChanged

    private void cmbVoluntariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVoluntariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbVoluntariosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ACTUALIZARLISTA;
    private javax.swing.JButton AÑADIR;
    private javax.swing.JButton BUSCAR;
    private javax.swing.JButton ELIMINAR;
    private javax.swing.JButton EVALUAR;
    private javax.swing.JButton MODIFICAR;
    private javax.swing.JButton VER_SIN_ASIGNADOS;
    private javax.swing.JComboBox<modelo.Usuario> cmbVoluntarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JTextField txtIdTarea;
    // End of variables declaration//GEN-END:variables
}
class VoluntarioComboBoxRenderer extends javax.swing.plaf.basic.BasicComboBoxRenderer {
    @Override
    public java.awt.Component getListCellRendererComponent(
            javax.swing.JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
             super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Voluntario) {
                    Voluntario vol = (Voluntario) value;
                    setText(vol.getNombre() + " (DNI: " + vol.getDNI() + ")");
                } 
                    else if (value == null || value.equals("Todos los Voluntarios")) {
                    setText("Todos los Voluntarios");
                    }
                    return this;
            }
}
class GatoComboBoxRenderer extends javax.swing.plaf.basic.BasicComboBoxRenderer {
    @Override
    public java.awt.Component getListCellRendererComponent(
            javax.swing.JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Gato) {
            Gato gato = (Gato) value;
            setText(gato.getNombre() + " (ID: " + gato.getId() + ")");
        } else if (value == null) {
            setText("Seleccione un gato");
        }
        return this;
    }
}

