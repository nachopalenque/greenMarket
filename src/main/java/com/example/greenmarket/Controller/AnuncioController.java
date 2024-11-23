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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
        model.addAttribute("totalAnuncios", anunciosConFotos.size());
        model.addAttribute("anuncios", anunciosConFotos);
        return "anuncios-lista";
    }



    @GetMapping("/anuncios/nuevo")
    public String getAnunciosNuevo(Model model){
        Anuncio anuncio = new Anuncio();
        anuncio.setPrecio(0.0);
        List<Categoria> categorias = categoriaService.getAllCategorias();
        model.addAttribute("anuncio", anuncio);
        model.addAttribute("categorias", categorias);

        return "anuncio-nuevo";
    }

    @PostMapping("/anuncios/nuevo")
    public String getAnunciosNuevoInserta(@Valid Anuncio anuncio , BindingResult bindingResult  , @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos){

        if (bindingResult.hasErrors()) {
            return "anuncio-nuevo";
        }

        fotoService.guardarFotos(fotos, anuncio);
        anuncioService.altaAnuncio(anuncio);
        return "redirect:/";
    }

    @GetMapping("/anuncios/borrar/{id}")
    public String eliminarAnuncio(@PathVariable Long id) {
        fotoService.eliminarFotosAnuncio(id);
        anuncioService.bajaAnuncioId(id);
        return "redirect:/";
    }


    @GetMapping("/anuncios/ver/{id}")
    public String getAnuncioVer(@PathVariable Long id, Model model){

        Anuncio anuncio = new Anuncio();
        anuncio = anuncioService.getAnuncioId(id);
        model.addAttribute("anuncio", anuncio );
        model.addAttribute("fotos", fotoService.getFotosAnuncio(anuncio));

        return "anuncio-ver";
    }

    @GetMapping("/anuncios/editar/{id}")
    public String getAnuncioEditar(@PathVariable Long id, Model model){
        try{
            Anuncio anuncio = new Anuncio();
            anuncio = anuncioService.getAnuncioId(id);
            model.addAttribute("anuncio", anuncio );
            return "anuncio-editar";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @PostMapping("/anuncios/editar/{id}")
    public String getAnunciosEditarActualiza(@Valid Anuncio anuncio , BindingResult bindingResult  , @PathVariable Long id, @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos){

        if (bindingResult.hasErrors()) {
            return "anuncio-editar";
        }
        anuncio.setId(id);
        fotoService.guardarFotos(fotos, anuncio);
        anuncioService.actualizaAnuncio(anuncio);
        return "redirect:/";
    }


}
