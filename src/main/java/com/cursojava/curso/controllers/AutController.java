package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.model.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Usuario usuario){

        Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);

        if(usuarioLogueado != null){
            String tokenJwt = jwtUtil.create(
                    String.valueOf(usuarioLogueado.getId()),
                    usuarioLogueado.getEmail()
            );
            return ResponseEntity.ok(tokenJwt);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales incorrectas");
    }

    @RequestMapping(value = "api/registro", method = RequestMethod.POST)
    public String  registrarUsuario(@RequestBody Usuario usuario){

        // codigo para encriptar
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        Usuario usuarioRegistrado = usuarioDao.registrar(usuario); // retorna usuario

        // Registrar en bd
        usuarioDao.registrar(usuario);

        // Generar token automáticamente depués del registro
        String tokenJwt = jwtUtil.create(String.valueOf(usuarioRegistrado.getId())
                            , usuario.getEmail()
        );
        return tokenJwt;
    }
}
