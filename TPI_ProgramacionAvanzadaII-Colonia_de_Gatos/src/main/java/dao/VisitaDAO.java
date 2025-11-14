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
import modelo.Visita;
import modelo.Hogar_Adopcion; 
import java.util.Collections;
import java.util.List;
import modelo.Voluntario;

public class VisitaDAO {

    private EntityManager em;

    public VisitaDAO(EntityManager em) { this.em = em; }

    public void guardar(Visita visita) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(visita);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al guardar la visita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Visita buscarPorId(int id) {
        try {
            return em.find(Visita.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar visita por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Visita> listarTodas() {
        try {
            TypedQuery<Visita> query = em.createQuery("SELECT v FROM Visita v ORDER BY v.fechahora DESC", Visita.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar todas las visitas: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void actualizar(Visita visita) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(visita);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al actualizar la visita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminar(Visita visita) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            if (!em.contains(visita)) {
                visita = em.merge(visita);
            }
            em.remove(visita);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al eliminar la visita: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Visita> buscarPorHogar(Hogar_Adopcion hogar) {
         if (hogar == null) return Collections.emptyList();
         try {
             TypedQuery<Visita> query = em.createQuery("SELECT v FROM Visita v WHERE v.adopcion = :hogar ORDER BY v.fechahora DESC", Visita.class);
             query.setParameter("hogar", hogar);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar visitas por hogar: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<Visita> buscarPorVoluntario(Voluntario voluntario) {
         if (voluntario == null) return Collections.emptyList();
         try {
             TypedQuery<Visita> query = em.createQuery("SELECT v FROM Visita v WHERE v.voluntario = :vol ORDER BY v.fechahora DESC", Visita.class);
             query.setParameter("vol", voluntario);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar visitas por voluntario: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
