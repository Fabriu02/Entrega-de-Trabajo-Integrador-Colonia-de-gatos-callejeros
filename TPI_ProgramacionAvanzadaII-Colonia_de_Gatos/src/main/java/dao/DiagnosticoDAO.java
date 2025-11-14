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
import modelo.Diagnosticos;
import modelo.Gato;
import java.util.List;
import modelo.Veterinario;

public class DiagnosticoDAO {

    private EntityManager em;

    public DiagnosticoDAO(EntityManager em) {
        this.em = em;
    }
   
    public void guardar(Diagnosticos diagnostico) {
        em.getTransaction().begin();
        em.persist(diagnostico);
        em.getTransaction().commit();
    }

    public Diagnosticos buscarPorId(int id) {
        return em.find(Diagnosticos.class, id);
    }

    public List<Diagnosticos> listarTodos() {
        TypedQuery<Diagnosticos> query = em.createQuery("SELECT d FROM Diagnosticos d ORDER BY d.fecha DESC", Diagnosticos.class);
        return query.getResultList();
    }

    public List<Diagnosticos> buscarPorGato(Gato gato) {
        if (gato == null) {
            return List.of();
        }
        TypedQuery<Diagnosticos> query = em.createQuery("SELECT d FROM Diagnosticos d WHERE d.paciente = :gato ORDER BY d.fecha DESC",Diagnosticos.class);
        query.setParameter("gato", gato);
        return query.getResultList();
    }

    public void actualizar(Diagnosticos diagnostico) {
        em.getTransaction().begin();
        em.merge(diagnostico);
        em.getTransaction().commit();
    }

    public void eliminar(Diagnosticos diagnostico) {
        em.getTransaction().begin();
        if (!em.contains(diagnostico)) {
            diagnostico = em.merge(diagnostico);
        }
        em.remove(diagnostico);
        em.getTransaction().commit();
    }
    public List<Diagnosticos> buscarPorVeterinario(Veterinario vet) {
        if (vet == null) return Collections.emptyList();
        try {
             TypedQuery<Diagnosticos> query = em.createQuery("SELECT d FROM Diagnosticos d WHERE d.autor = :vet", Diagnosticos.class);
             query.setParameter("vet", vet);
             return query.getResultList();
         } catch (Exception e) {
            System.err.println("Error al buscar diagnosticos por veterinario: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
