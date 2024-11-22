package com.example.greenmarket.Controller;


import com.example.greenmarket.Service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FotoController {
    @Autowired
    private FotoService fotoService;


    @GetMapping("anuncios/{id1}/fotos/{id2}/elimina")
    public String eliminaFoto(@PathVariable("id1") Long idProducto,
                             @PathVariable("id2") Long idFoto) {
        fotoService.eliminarFotosId(idFoto);
        return "redirect:/anuncios/editar/" + idProducto ;
    }

}
