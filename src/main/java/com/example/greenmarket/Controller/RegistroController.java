package com.example.greenmarket.Controller;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Service.AnuncioService;
import com.example.greenmarket.Service.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class RegistroController {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    private AnuncioService anuncioService;
//------------------------------------------------formularios de registro usuario--------------------------------------------------------------------------
    @GetMapping("/panel/usuario")
    public String volverAlPanel(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        List<Anuncio> anuncios = usuario.getAnuncios();
        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", usuario);

        return "usuario-panel";
    }


    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-nuevo";
    }
    @PostMapping("/registro")
    public String registroInserta(@Valid Usuario usuario, BindingResult result, Model model, HttpSession session) {

        usuario.setRol("ROLE_USER");
        session.setAttribute("usuario", usuario.getNombre());

        if (result.hasErrors()) {
            return "usuario-nuevo";
        }
        usuarioServicio.altaUsuario(usuario);
        model.addAttribute("usuario", usuario);
        return "redirect:/login";
    }

    //---------------------------------------------formularios de registro rol admin--------------------------------------------------------------------------
    @GetMapping("/admin/registro")
    public String registroAdministrador(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-nuevo-admin";
    }


    @PostMapping("/admin/registro")
    public String registroAdministradorInserta(@Valid Usuario usuario, BindingResult result, Model model,  HttpSession session) {
        if (result.hasErrors()) {
            return "usuario-nuevo-admin";
        }
        usuario.setRol("ROLE_ADMIN");
        usuarioServicio.altaUsuario(usuario);
        session.setAttribute("usuario", usuario.getNombre());

        model.addAttribute("usuario", usuario);
        return "redirect:/login";
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("usuario") != null){
            model.addAttribute("usuarioRegistrado",session.getAttribute("usuario"));

        }else{
            model.addAttribute("usuarioRegistrado", "");
        }
        model.addAttribute("usuario", new Usuario());
        return "usuario-login";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

}
