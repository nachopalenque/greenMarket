package com.example.greenmarket.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String nombre;

    @NotNull
    @Column(length = 500, nullable = false)
    @Size(min=4, message = "El campo password ha de tener como mínimo 4 carácteres")
    private String password;


    @NotBlank(message = "El campo email no puede estar vacío.")
    @Email(message = "El campo email no tiene un formato correcto.")
    @Column(unique = true, nullable = false)
    private String email;

    private String poblacion;

    private int telefono;


    private String rol;

    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL,
            mappedBy = "usuario")
    private List<Anuncio> anuncios = new ArrayList<>();

}
