/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Fabri
 */
public class Swingcontexto {
    public final GestorUsuario usuarioService;
    public final Gestor_gatos gatoService;
    public final Listado_zonas zonaService;
    public final Gestion_hogares gestionHogares;
    public final Gestor_diagnosticos diagnosticoService;
    public final Gestion_tareas tareaService;
    public final Gestion_visitas gestionVisitas;
    public final Adopcion_gato adopcionService;
    public final Gestor_Calendario calendarioService;
    public final Gestor_reportes gestorReportes;

    public Swingcontexto(GestorUsuario us, Gestor_gatos gs, Listado_zonas zs, Gestion_hogares gh,Gestor_diagnosticos ds, Gestion_tareas ts, Gestion_visitas gv,Adopcion_gato as, Gestor_Calendario cs, Gestor_reportes gr) {
        this.usuarioService = us;
        this.gatoService = gs;
        this.zonaService = zs;
        this.gestionHogares = gh;
        this.diagnosticoService = ds;
        this.tareaService = ts;
        this.gestionVisitas = gv;
        this.adopcionService = as;
        this.calendarioService = cs;
        this.gestorReportes = gr;
    }
}
