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
import modelo.Usuario;
import java.util.List;
public class UsuarioDAO {
    private EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }
    public void guardar(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario buscarPorDNI(String dni) {
        return em.find(Usuario.class, dni);
    }

    public List<Usuario> listarTodos() {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }
    
 
    public void actualizar(Usuario usuario) { //usuario existente 
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void eliminar(Usuario usuario) {
        em.getTransaction().begin();
        if (!em.contains(usuario)) {
            usuario = em.merge(usuario);
        }
        em.remove(usuario);
        em.getTransaction().commit();
    }
}
