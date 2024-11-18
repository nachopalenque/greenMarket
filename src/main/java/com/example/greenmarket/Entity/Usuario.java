package com.example.greenmarket.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Column(length = 500, nullable = false)
    private String password;

    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String poblacion;


    @NotNull
    @Column(nullable = false)
    private String rol;

    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL,
            mappedBy = "usuario")
    private List<Anuncio> anuncios = new ArrayList<>();

}
