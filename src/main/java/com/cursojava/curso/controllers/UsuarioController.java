package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Angel");
        usuario.setApellido("Latin");
        usuario.setEmail("angel_latinp@hotmial.com");
        usuario.setTelefono("234231");
        usuario.setPassword("sdfsdkj");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios")
    public List<Usuario> getUsuarios(){
        return usuarioDao.getUsuario();
    }

    @RequestMapping(value = "api/crearUsuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/eliminar/{id}", method = RequestMethod.DELETE)
    public void eliminar(@PathVariable Long id){
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "buscar")
    public Usuario buscar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Angel");
        usuario.setApellido("Latin");
        usuario.setEmail("angel_latinp@hotmial.com");
        usuario.setTelefono("234231");
        usuario.setPassword("sdfsdkj");
        return usuario;
    }
}
