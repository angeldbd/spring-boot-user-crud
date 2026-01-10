package com.cursojava.curso.dao;

import com.cursojava.curso.model.Usuario;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuario() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public boolean verificarEmailPassword(Usuario usuario) {
        String query = "FROM Usuario where email = :email AND password = :password";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter(email, usuario.getEmail())
                .setParameter(password, usuario.getPassword())
                .getResultList();

        return !lista.isEmpty();
    }
}
