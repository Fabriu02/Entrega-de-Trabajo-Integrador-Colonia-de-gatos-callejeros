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
import modelo.Hogar_Adopcion;
import java.util.List;

public class HogarDAO {

    private EntityManager em;

    public HogarDAO(EntityManager em) {
        this.em = em;
    }
    
    //CRUD
    public void guardar(Hogar_Adopcion hogar) {
        em.getTransaction().begin();
        em.persist(hogar);
        em.getTransaction().commit();
    }

    public Hogar_Adopcion buscarPorId(int id) {
        return em.find(Hogar_Adopcion.class, id);
    }

    public List<Hogar_Adopcion> listarTodos() {
        TypedQuery<Hogar_Adopcion> query = em.createQuery("SELECT h FROM Hogar_Adopcion h", Hogar_Adopcion.class);
        return query.getResultList();
    }
    
    public Hogar_Adopcion buscarPorDniFamilia(String dni) {
        TypedQuery<Hogar_Adopcion> query = em.createQuery("SELECT h FROM Hogar_Adopcion h WHERE h.familia.DNI = :dniFamilia", Hogar_Adopcion.class);
        query.setParameter("dniFamilia", dni);
        try {
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; 
        }
    }

    public void actualizar(Hogar_Adopcion hogar) {
        em.getTransaction().begin();
        em.merge(hogar);
        em.getTransaction().commit();
    }

    public void eliminar(Hogar_Adopcion hogar) {
        em.getTransaction().begin();
        if (!em.contains(hogar)) {
            hogar = em.merge(hogar);
        }
        em.remove(hogar);
        em.getTransaction().commit();
    }
}

