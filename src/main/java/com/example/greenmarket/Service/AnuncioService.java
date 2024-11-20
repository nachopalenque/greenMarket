package com.example.greenmarket.Service;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    AnuncioRepository anuncioRepository;

    public Anuncio getAnuncioId(Long id){
        return anuncioRepository.findById(id).get();
    }

    public List<ListadoAnunciosImagenes> getAnunciosImagenes() {
        List<ListadoAnunciosImagenes> anunciosImagenes = anuncioRepository.obtenerAnunciosConImagenes();
        return anunciosImagenes;
    }
    public List<Anuncio> getAnuncios(){

        return anuncioRepository.findAll();
    }
    public boolean altaAnuncio(Anuncio anuncio){
        LocalDate fechaAnuncio = LocalDate.now();

        anuncio.setFechaCreacion(fechaAnuncio);
        return anuncioRepository.save(anuncio) != null;
    }

    public void bajaAnuncioId(long id){

        anuncioRepository.deleteById(id);
        ;
    }




}
