package com.example.greenmarket.Controller;

import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Categoria;
import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Service.CategoriaService;
import com.example.greenmarket.Service.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/categoria/nuevo")
    public String nuevaCategoria(Model model, HttpSession session) {

        if (session.getAttribute("categoria") != null){
            session.removeAttribute("categoria");
        }
        model.addAttribute("categoria", new Categoria());
        return "categoria-nuevo";
    }

    @PostMapping("/categoria/nuevo")
    public String nuevoCategoriaInserta(@Valid @ModelAttribute Categoria categoria, BindingResult bindingResult, Principal principal, Model model, HttpSession session) {

        if(bindingResult.hasErrors()){

            return "categoria-nuevo";
        }

        categoriaService.altaCategoria(categoria);
        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        List<Anuncio> anuncios = usuario.getAnuncios();

        session.setAttribute("categoria", categoria.getTitulo());

        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", anuncios);
        return "redirect:/anuncios/panel/inicio";
    }
}
