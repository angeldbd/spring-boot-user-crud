package com.cursojava.curso.dao;

import com.cursojava.curso.model.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuario();

    void eliminar(Long id);

    Usuario registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
