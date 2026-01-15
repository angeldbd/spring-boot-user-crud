package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.model.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("api/usuario/{id}")
    public Usuario getUsuario(@PathVariable Long id){
        return  usuarioDao.obtenerUsuarioPorID(id);
    }

    @GetMapping("api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        return usuarioDao.getUsuario();
    }

    @DeleteMapping(value = "api/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        usuarioDao.eliminar(id);
    }

}
