package com.example.greenmarket.Controller;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Categoria;
import com.example.greenmarket.Entity.Foto;
import com.example.greenmarket.Entity.Usuario;
import com.example.greenmarket.Repository.AnuncioRepository;
import com.example.greenmarket.Service.AnuncioService;
import com.example.greenmarket.Service.CategoriaService;
import com.example.greenmarket.Service.FotoService;
import com.example.greenmarket.Service.UsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
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
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String getAnuncios(Model model, HttpSession session) {

        usuarioServicio.eliminarHttpSessions(session);

        if (session.getAttribute("usuarioLogeado") != null) {

            model.addAttribute("usuario", session.getAttribute("usuarioLogeado"));
        }
        else{
            model.addAttribute("usuario", "");

        }


        List<ListadoAnunciosImagenes> anunciosConFotos = anuncioService.getAnunciosImagenes();
        model.addAttribute("totalAnuncios", anunciosConFotos.size());
        model.addAttribute("anuncios", anunciosConFotos);

        return "anuncios-lista";
    }


    @GetMapping("/anuncios/panel/registro")
    public String getAnunciosUsuario(Model model, Usuario usuario){
        List<Anuncio> anuncios = usuario.getAnuncios();
        model.addAttribute("anuncios", anuncios);
        model.addAttribute("usuario", usuario);

        return "usuario-panel";
    }

    @GetMapping("/anuncios/panel/inicio")
    public String getUsuarioPanel(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        session.setAttribute("usuarioLogeado", usuario.getNombre());

        // Obtén los anuncios del usuario
        List<Anuncio> anuncios = usuario.getAnuncios();
        // Agrega los anuncios y el usuario al modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", anuncios);

        if (session.getAttribute("categoria") != null){
            model.addAttribute("categoriaNueva",session.getAttribute("categoria"));

        }else{
            model.addAttribute("categoriaNueva", "");
        }

        if (session.getAttribute("anuncio") != null){
            model.addAttribute("anuncioNuevo",session.getAttribute("anuncio"));

        }else{
            model.addAttribute("anuncioNuevo", "");
        }
        return "usuario-panel";
    }






    @GetMapping("/anuncios/nuevo")
    public String getAnunciosNuevo(Model model, Principal principal, HttpSession session){

        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        Anuncio anuncio = new Anuncio();
        anuncio.setPrecio(0.0);
        anuncio.setUsuario(usuario);
        List<Categoria> categorias = categoriaService.getAllCategorias();
        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncio", anuncio);
        model.addAttribute("categorias", categorias);
        usuarioServicio.eliminarHttpSessions(session);

        return "anuncio-nuevo";
    }

    @PostMapping("/anuncios/nuevo")
    public String getAnunciosNuevoInserta(@Valid Anuncio anuncio , BindingResult bindingResult  , @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos, Principal principal, Model model, HttpSession session){

        if (bindingResult.hasErrors()) {
            return "anuncio-nuevo";
        }

        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        anuncio.setUsuario(usuario);
        LocalDate fechaAnuncio = LocalDate.now();
        anuncio.setFechaCreacion(fechaAnuncio);

        fotoService.guardarFotos(fotos, anuncio);
        anuncioService.altaAnuncio(anuncio);
        List<Anuncio> anuncios = usuario.getAnuncios();

        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", anuncios);

        session.setAttribute("anuncio", anuncio.getTitulo());

        return "redirect:/anuncios/panel/inicio";

    }

    @GetMapping("/anuncios/borrar/{id}")
    public String eliminarAnuncio(@PathVariable Long id, Principal principal, Model model) {
        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        fotoService.eliminarFotosAnuncio(id);
        anuncioService.bajaAnuncioId(id);
        List<Anuncio> anuncios = usuario.getAnuncios();

        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", anuncios);
        return "redirect:/anuncios/panel/inicio";
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
    public String getAnunciosEditarActualiza(@Valid Anuncio anuncio , BindingResult bindingResult  , @PathVariable Long id, @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos, Model model, Principal principal ){


        if (bindingResult.hasErrors()) {
            return "anuncio-editar";
        }
        Anuncio anuncioDatosNoEditables = anuncioService.getAnuncioId(id);
        anuncio.setCategoria(anuncioDatosNoEditables.getCategoria());
        anuncio.setFechaCreacion(anuncioDatosNoEditables.getFechaCreacion());
        anuncio.setId(id);

        String username = principal.getName();
        Usuario usuario = usuarioServicio.dameUsuarioPorEmail(username);
        anuncio.setUsuario(usuario);


        fotoService.guardarFotos(fotos, anuncio);
        anuncioService.actualizaAnuncio(anuncio);

        // Obtén los anuncios del usuario
        List<Anuncio> anuncios = usuario.getAnuncios();
        // Agrega los anuncios y el usuario al modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncios", anuncios);
        return "redirect:/anuncios/panel/inicio";
    }


}
