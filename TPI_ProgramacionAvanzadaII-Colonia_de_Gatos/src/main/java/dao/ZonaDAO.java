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
import modelo.Zona_geografica;
import java.util.List;


public class ZonaDAO {

    private EntityManager em;

    public ZonaDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Zona_geografica zona) {
        em.getTransaction().begin();
        em.persist(zona);
        em.getTransaction().commit();
    }

    public Zona_geografica buscarPorId(int id) {
        return em.find(Zona_geografica.class, id);
    }

    public List<Zona_geografica> listarTodas() {
        TypedQuery<Zona_geografica> query = em.createQuery("SELECT z FROM Zona_geografica z", Zona_geografica.class);
        return query.getResultList();
    }
    
    public void actualizar(Zona_geografica zona) {
        em.getTransaction().begin();
        em.merge(zona);
        em.getTransaction().commit();
    }

    public void eliminar(Zona_geografica zona) {
        em.getTransaction().begin();
        if (!em.contains(zona)) {
            zona = em.merge(zona);
        }
        em.remove(zona);
        em.getTransaction().commit();
    }
    
}
