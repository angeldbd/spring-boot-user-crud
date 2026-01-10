package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.model.Usuario;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutController {


    private UsuarioDao usuarioDao;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public void login(@RequestBody Usuario usuario){
        usuarioDao.verificarEmailPassword(usuario);
    }
}
