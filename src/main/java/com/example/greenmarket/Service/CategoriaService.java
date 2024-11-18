package com.example.greenmarket.Service;

import com.example.greenmarket.Entity.Categoria;
import com.example.greenmarket.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    public boolean altaCategoria(Categoria categoria){
        return categoriaRepository.save(categoria) != null;
    }

}
