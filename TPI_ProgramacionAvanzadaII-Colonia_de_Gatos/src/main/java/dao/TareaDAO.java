/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Fabri
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction; 
import jakarta.persistence.TypedQuery;
import modelo.Tarea;
import modelo.Voluntario;
import java.util.Collections; 
import java.util.List;
import modelo.Gato;

public class TareaDAO {

    private EntityManager em;

    public TareaDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Tarea tarea) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(tarea);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al guardar la tarea: " + e.getMessage());
            e.printStackTrace();
            
        }
    }

    public Tarea buscarPorId(int id) {
        try {
            return em.find(Tarea.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar tarea por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Tarea> listarTodas() {
        try {
            TypedQuery<Tarea> query = em.createQuery("SELECT t FROM Tarea t ORDER BY t.fechahora ASC", Tarea.class);
            return query.getResultList();
        } catch (Exception e) {
             System.err.println("Error al listar todas las tareas: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList(); 
        }
    }
    public void actualizar(Tarea tarea) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(tarea);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al actualizar la tarea: " + e.getMessage());
            e.printStackTrace();
            
        }
    }
    public void eliminar(Tarea tarea) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            if (!em.contains(tarea)) {
                tarea = em.merge(tarea);
            }
            em.remove(tarea);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al eliminar la tarea: " + e.getMessage());
            e.printStackTrace();
            
        }
    }

    public List<Tarea> buscarSinVoluntarioAsignado() {
        try {
            TypedQuery<Tarea> query = em.createQuery("SELECT t FROM Tarea t WHERE t.voluntario IS NULL AND t.estado = 0 ORDER BY t.fechahora ASC", Tarea.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar tareas sin voluntario: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Tarea> buscarPendientesPorVoluntario(Voluntario v) {
        if (v == null) 
            return Collections.emptyList();
        try {
             TypedQuery<Tarea> query = em.createQuery(
                "SELECT t FROM Tarea t WHERE t.voluntario = :vol AND t.estado = 0 ORDER BY t.fechahora ASC", Tarea.class);
             query.setParameter("vol", v);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar tareas pendientes por voluntario: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<Tarea> buscarPorGato(Gato gato) {
        if (gato == null) return Collections.emptyList();
        try {
             TypedQuery<Tarea> query = em.createQuery("SELECT t FROM Tarea t WHERE t.gato = :gato", Tarea.class);
             query.setParameter("gato", gato);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar tareas por gato: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<Tarea> buscarPorVoluntario(Voluntario v) {
        if (v == null) return Collections.emptyList();
        try {
             TypedQuery<Tarea> query = em.createQuery(
                "SELECT t FROM Tarea t WHERE t.voluntario = :vol", Tarea.class);
             query.setParameter("vol", v);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar tareas por voluntario: " + e.getMessage());
            return Collections.emptyList();
        }
    }
 
}
