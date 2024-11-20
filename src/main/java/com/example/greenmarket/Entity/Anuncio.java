package com.example.greenmarket.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "anuncios")
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo titulo es obligatorio. Debe introduccir un titulo para el anuncio.")
    @Column(nullable = false)
    private String titulo;

    @NotEmpty
    private String descripcion;


    private LocalDate fechaCreacion;

    @NotNull(message = "El campo precio es obligatorio. Debe introduccir un valor para el precio del anuncio.")
    @Column(nullable = false)
    private Double precio;

    @OneToMany(targetEntity = Foto.class, cascade = CascadeType.ALL,
            mappedBy = "anuncio")
    private List<Foto> fotos = new ArrayList<>();

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
