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
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import modelo.Gato;
import java.util.List;
import modelo.Zona_geografica;

public class GatoDAO {

    private EntityManager em;

    public GatoDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Gato gato) {
        em.getTransaction().begin();
        em.persist(gato);
        em.getTransaction().commit();
    }

    public Gato buscarPorId(int id) {
        return em.find(Gato.class, id);
    }

    public List<Gato> listarTodos() {
        TypedQuery<Gato> query = em.createQuery("SELECT g FROM Gato g", Gato.class);
        return query.getResultList();
    }

    
    public List<Gato> listarDisponibles() {
        TypedQuery<Gato> query = em.createQuery("SELECT g FROM Gato g WHERE g.adoptado = 0 AND g.apto = 1", Gato.class);
        return query.getResultList();
    }
    
    public void actualizar(Gato gato) {
        em.getTransaction().begin();
        em.merge(gato);
        em.getTransaction().commit();
    }
    
    public void eliminar(Gato gato) {
        em.getTransaction().begin();
        if (!em.contains(gato)) {
            gato = em.merge(gato);
        }
        em.remove(gato);
        em.getTransaction().commit();
    }
    public List<Gato> buscarPorZona(Zona_geografica zona) {
        if (zona == null) return Collections.emptyList();
        try {
             TypedQuery<Gato> query = em.createQuery( "SELECT g FROM Gato g WHERE g.zona = :zona", Gato.class);
             query.setParameter("zona", zona);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar gatos por zona: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

