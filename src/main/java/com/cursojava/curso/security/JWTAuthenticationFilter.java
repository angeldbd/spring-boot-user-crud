package com.cursojava.curso.security;

import com.cursojava.curso.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        // 1. Obtener el token del header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Si no hay token o no empieza con "Bearer ", continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitar "Bearer ")
        String token = authHeader.substring(7);
        // 4. Validar el token
        if (jwtUtil.isTokenValid(token)) {
            // 5. Obtener el email (subject) del token
            String email = jwtUtil.getValue(token);

            if (email != null) {
                // 6. Crear autenticación de Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,              // Principal (usuario)
                                null,               // Credentials (no necesitamos password aquí)
                                new ArrayList<>()   // Authorities (roles/permisos - vacío por ahora)
                        );

                // 7. Agregar detalles de la request
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 9. Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }
}
