package com.example.greenmarket.Repository;

import com.example.greenmarket.DTO.ListadoAnunciosImagenes;
import com.example.greenmarket.Entity.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    @Query("SELECT new com.example.greenmarket.DTO.ListadoAnunciosImagenes(c.id, c.titulo, c.precio, " +
            "(SELECT f.ruta FROM Foto f WHERE f.anuncio.id = c.id ORDER BY f.id ASC LIMIT 1)) " +
            "FROM Anuncio c LEFT JOIN c.fotos f  GROUP BY c.id ORDER By c.fechaCreacion DESC")
    List<ListadoAnunciosImagenes> obtenerAnunciosConImagenes();

    Optional<Anuncio> findAnuncioByFotosId(long idFoto);

}
