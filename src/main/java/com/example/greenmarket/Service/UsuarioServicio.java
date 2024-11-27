package com.example.greenmarket.Service;

import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    UsuarioRepository usuarioRepository;

    public boolean altaUsuario(Usuario usuario){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return true;
    }

    public Usuario dameUsuarioPorEmail(String email){

        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {

            if (usuario.getEmail().equals(email)) {

                return usuario;

            }
        }

        return new Usuario();
    }

    public void eliminarHttpSessions(HttpSession session){

        if (session.getAttribute("categoria") != null){
            session.removeAttribute("categoria");
        }

        if (session.getAttribute("anuncio") != null){
            session.removeAttribute("anuncio");
        }


    }

}
