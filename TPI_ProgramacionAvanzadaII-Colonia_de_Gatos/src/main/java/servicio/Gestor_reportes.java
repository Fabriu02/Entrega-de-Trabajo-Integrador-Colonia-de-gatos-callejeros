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
import dao.ZonaDAO;
import modelo.Gato;
import modelo.Zona_geografica;
import java.util.List;



public class Gestor_reportes {
    private GatoDAO gatoDAO;
    private ZonaDAO zonaDAO;

    public Gestor_reportes(GatoDAO gatoDAO, ZonaDAO zonaDAO) {
        this.gatoDAO = gatoDAO;
        this.zonaDAO = zonaDAO;
    }
    public List<Gato> gatoporZona(Zona_geografica zonaSe){
        return gatoDAO.buscarPorZona(zonaSe);
    }
    public String reporteGatosPorZona() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- Reporte: Gatos por Zona ---\n");

        List<Zona_geografica> zonas = zonaDAO.listarTodas();
        List<Gato> gatos = gatoDAO.listarTodos();

        if (zonas.isEmpty()) {
            reporte.append("No hay zonas registradas.\n");
        }

        for (Zona_geografica z : zonas) {
            int contador = 0;
            for (Gato g : gatos) {
                if (g.getZona() != null && g.getZona().getIdzona() == z.getIdzona()) {
                    contador++;
                }
            }
            reporte.append("Zona: ").append(z.getNombrezona()).append(" (ID: ").append(z.getIdzona()).append(")\n");
            reporte.append("  Coordenadas: ").append(z.getCoordenadas()).append("\n");
            reporte.append("  Cantidad de gatos: ").append(contador).append("\n");
            reporte.append("-----------------------------------\n");
        }
        
        int sinZona = 0;
        for (Gato g : gatos) {
            if (g.getZona() == null) sinZona++;
        }
        if (sinZona > 0) {
            reporte.append("Gatos sin zona asignada: ").append(sinZona).append("\n");
        }
        return reporte.toString();
    }

    public String reporteEsterilizados() {
      StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- Reporte: Gatos Esterilizados ---\n");
        List<Gato> gatos = gatoDAO.listarTodos();
        int contador = 0;
        for (Gato g : gatos) {
            if (g.isEsterilizado()) { 
                contador++;
            }
        }
        reporte.append("Total de gatos registrados: ").append(gatos.size()).append("\n");
        reporte.append("Total de gatos esterilizados: ").append(contador).append("\n");
        return reporte.toString();
    }

    public String reporteAdoptados() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- Reporte: Gatos Adoptados ---\n");

        List<Gato> gatos = gatoDAO.listarTodos();
        int adoptadosDef = 0;
        int enTransicion = 0;
        for (Gato g : gatos) {
            String estado = g.getAdoptado(); 
            if (estado.equals("Adoptado")) { 
                adoptadosDef++;
            } else if (estado.equals("Transicion")) { 
                enTransicion++;
            }
        }
        reporte.append("Total de gatos en adopcion definitiva: ").append(adoptadosDef).append("\n");
        reporte.append("Total de gatos en Transicion: ").append(enTransicion).append("\n");
        reporte.append("Total de gatos adoptados (ambos tipos): ").append(adoptadosDef + enTransicion).append("\n");
        return reporte.toString();
    }
    public String verReporteGeneral() {
     StringBuilder reporteGeneral = new StringBuilder();
        reporteGeneral.append("======================================\n");
        reporteGeneral.append("     INFORME DE GESTION DE GATOS\n");
        reporteGeneral.append("======================================\n");
        
        reporteGeneral.append(reporteGatosPorZona());
        reporteGeneral.append(reporteEsterilizados());
        reporteGeneral.append(reporteAdoptados());
        
        reporteGeneral.append("\n======================================\n");
        reporteGeneral.append("          FIN DEL INFORME\n");
        reporteGeneral.append("======================================\n");
        return reporteGeneral.toString();
    }
}
