package com.example.greenmarket.Controller;

import com.example.greenmarket.Entity.Categoria;
import com.example.greenmarket.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categoria/nuevo")
    public String nuevaCategoria(Model model) {

        model.addAttribute("categoria", new Categoria());
        return "categoria-nuevo";
    }

    @PostMapping("/categoria/nuevo")
    public String nuevoCategoriaInserta(@Valid Categoria categoria, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){

            return "categoria-nuevo";
        }

        categoriaService.altaCategoria(categoria);
        return "redirect:/";
    }
}
