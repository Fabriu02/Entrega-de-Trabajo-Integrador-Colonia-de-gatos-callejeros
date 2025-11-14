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
import modelo.Familia;
import modelo.Gato;
import modelo.SolicitudAdopcion;
import java.util.Collections;
import java.util.List;

public class SolicitudDAO {
    private EntityManager em;
    public SolicitudDAO(EntityManager em) {
        this.em = em;
    }
    public void guardar(SolicitudAdopcion solicitud) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(solicitud);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al guardar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public SolicitudAdopcion buscarPorId(int id) {
        try {
            return em.find(SolicitudAdopcion.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar solicitud por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public void actualizar(SolicitudAdopcion solicitud) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(solicitud);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al actualizar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void eliminar(SolicitudAdopcion solicitud) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            if (!em.contains(solicitud)) {
                solicitud = em.merge(solicitud);
            }
            em.remove(solicitud);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Error al eliminar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Especiales o por las dudas / para el admin
    
    public List<SolicitudAdopcion> listarTodas() {
        try {
            TypedQuery<SolicitudAdopcion> query = em.createQuery("SELECT s FROM SolicitudAdopcion s ORDER BY s.fechaSolicitud DESC", SolicitudAdopcion.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar todas las solicitudes: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<SolicitudAdopcion> listarPendientes() {
        try {
            TypedQuery<SolicitudAdopcion> query = em.createQuery("SELECT s FROM SolicitudAdopcion s WHERE s.estado = 0 ORDER BY s.fechaSolicitud ASC", SolicitudAdopcion.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar solicitudes pendientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<SolicitudAdopcion> buscarPorFamilia(Familia familia) {
        if (familia == null) return Collections.emptyList();
        try {
             TypedQuery<SolicitudAdopcion> query = em.createQuery("SELECT s FROM SolicitudAdopcion s WHERE s.familia = :familia ORDER BY s.fechaSolicitud DESC", SolicitudAdopcion.class);
             query.setParameter("familia", familia);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar solicitudes por familia: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<SolicitudAdopcion> buscarPendientesPorGato(Gato gato) {
        if (gato == null) return Collections.emptyList();
        try {
             TypedQuery<SolicitudAdopcion> query = em.createQuery("SELECT s FROM SolicitudAdopcion s WHERE s.gato = :gato AND s.estado = 0", SolicitudAdopcion.class);
             query.setParameter("gato", gato);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar solicitudes pendientes por gato: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
}
