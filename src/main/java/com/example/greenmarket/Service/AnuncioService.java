package com.example.greenmarket.Service;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    AnuncioRepository anuncioRepository;

    public List<ListadoAnunciosImagenes> getAnunciosImagenes() {
        List<ListadoAnunciosImagenes> anunciosImagenes = anuncioRepository.obtenerAnunciosConImagenes();
        return anunciosImagenes;
    }
    public List<Anuncio> getAnuncios(){

        return anuncioRepository.findAll();
    }
    public boolean altaAnuncio(Anuncio anuncio){
        return anuncioRepository.save(anuncio) != null;
    }

    public void bajaAnuncioId(long id){

        anuncioRepository.deleteById(id);
        ;
    }




}
