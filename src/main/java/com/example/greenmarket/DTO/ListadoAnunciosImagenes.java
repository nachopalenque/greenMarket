package com.example.greenmarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListadoAnunciosImagenes {
    private Long id;
    private String titulo;
    private Double precio;
    private String ruta;
}
