package com.example.greenmarket.Controller;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Categoria;
import com.example.greenmarket.Entity.Foto;
import com.example.greenmarket.Service.AnuncioService;
import com.example.greenmarket.Service.CategoriaService;
import com.example.greenmarket.Service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@Controller
public class AnuncioController {
    @Autowired
    private AnuncioService anuncioService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private FotoService fotoService;

    @GetMapping("/")
    public String getAnuncios(Model model){

        List<ListadoAnunciosImagenes> anunciosConFotos = anuncioService.getAnunciosImagenes();
        model.addAttribute("anuncios", anunciosConFotos);
        return "anuncios-lista";
    }



    @GetMapping("/anuncios/nuevo")
    public String getAnunciosNuevo(Model model){
        Anuncio anuncio = new Anuncio();
        List<Categoria> categorias = categoriaService.getAllCategorias();
        model.addAttribute("anuncio", anuncio);
        model.addAttribute("categorias", categorias);

        return "anuncio-nuevo";
    }

    @PostMapping("/anuncios/nuevo")
    public String getAnunciosNuevoInserta(@Valid Anuncio anuncio, Model model , @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos){

        fotoService.guardarFotos(fotos, anuncio);
        anuncioService.altaAnuncio(anuncio);
        return "anuncios-lista";
    }

    @GetMapping("/anuncios/borrar/{id}")
    public String eliminarAnuncio(@PathVariable Long id) {
        fotoService.eliminarFotosAnuncio(id);
        anuncioService.bajaAnuncioId(id);
        return "anuncios-lista";
    }

}
