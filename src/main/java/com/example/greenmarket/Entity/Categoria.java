package com.example.greenmarket.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="categorias")
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String titulo;
    private String descripcion;
    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL,
            mappedBy = "categoria")
    private List<Anuncio> anuncios = new ArrayList<>();
}
