package com.example.greenmarket.Repository;

import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findByAnuncio(Anuncio anuncio);

}
