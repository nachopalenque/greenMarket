package com.example.greenmarket.Service;

import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //NO hay manera de hacerlo funcionar
        // Usuario usuario = usuarioRepository.findByEmail(username)//
        // .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {

            if (usuario.getEmail().equals(username)) {

                return new org.springframework.security.core.userdetails.User(
                        usuario.getEmail(),
                        usuario.getPassword(),
                        AuthorityUtils.createAuthorityList(usuario.getRol())
                );
            }
        }

        return null;

    }


}
