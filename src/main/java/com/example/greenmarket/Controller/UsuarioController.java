package com.example.greenmarket.Controller;

import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Service.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
     @Autowired
     CustomUserDetailsService userDetailsService;


     @GetMapping("/usuario/nuevo")
     public String nuevoUsuario(Model model) {

          model.addAttribute("usuario", new Usuario());
          return "usuario-nuevo";
     }

     @PostMapping("/usuario/nuevo")
     public String nuevoUsuarioInserta(Model model, @Valid Usuario usuario, BindingResult bindingResult) {

          if(bindingResult.hasErrors()){

               return "usuario-nuevo";
          }

          //Si no ha habido errores de validación insertamos los datos en la BD
          userDetailsService.altaUsuario(usuario);

          //Redirigimos a /getProductos
          return "redirect:/anuncios-lista";
     }

}
