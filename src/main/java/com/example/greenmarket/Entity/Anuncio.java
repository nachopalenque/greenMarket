package com.example.greenmarket.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @NotEmpty (message = "El título no puede estar en blanco")
    @Column(nullable = false)

    private String titulo;

    private String descripcion;


    private LocalDate fechaCreacion;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
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
