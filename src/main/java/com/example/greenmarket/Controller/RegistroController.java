package com.example.greenmarket.Controller;

import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Service.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    UsuarioServicio usuarioServicio;

//------------------------------------------------formularios de registro usuario--------------------------------------------------------------------------
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-nuevo";
    }
    @PostMapping("/registro")
    public String registroInserta(@Valid Usuario usuario, BindingResult result, Model model) {
        usuario.setRol("ROLE_USER");
        if (result.hasErrors()) {
            return "usuario-nuevo";
        }
        usuarioServicio.altaUsuario(usuario);
        model.addAttribute("usuario", usuario);
        return "usuario-panel";
    }

    //---------------------------------------------formularios de registro rol admin--------------------------------------------------------------------------
    @GetMapping("/admin/registro")
    public String registroAdministrador(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-nuevo";
    }


    @PostMapping("/admin/registro")
    public String registroAdministradorInserta(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "usuario-nuevo";
        }
        usuario.setRol("ROLE_ADMIN");
        usuarioServicio.altaUsuario(usuario);
        model.addAttribute("usuario", usuario);
        return "usuario-panel";
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-login";
    }


}
