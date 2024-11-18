package com.example.greenmarket.Service;

import com.example.greenmarket.Entity.Anuncio;
import com.example.greenmarket.Entity.Foto;
import com.example.greenmarket.Repository.AnuncioRepository;
import com.example.greenmarket.Repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FotoService {
    private static final List<String> PERMITTED_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/avif", "image/webp");
    private static final long MAX_FILE_SIZE = 10000000;
    private static final String UPLOADS_DIRECTORY = "uploads/imagesAnuncio";

    @Autowired
    AnuncioRepository anuncioRepository;
    @Autowired
    FotoRepository fotoProductoRepository;

    public List<Foto> getFotos(){
        return fotoProductoRepository.findAll();
    }

    public List<Foto> guardarFotos(List<MultipartFile> fotos, Anuncio anuncio) {

        List<Foto> listaFotos = new ArrayList<>();

        for (MultipartFile foto : fotos) {
            if (!foto.isEmpty()) {

                if(esValidoElArchivo(foto)) {
                    String rutaFoto = generarNombreUnico(foto);
                    guardarArchivo(foto, rutaFoto);

                    Foto fotoAnuncio = Foto.builder()
                            .ruta(rutaFoto)
                            .anuncio(anuncio).build();

                    listaFotos.add(fotoAnuncio);
                    anuncio.setFotos(listaFotos);

                }

            }
        }

        return listaFotos;
    }

    public Boolean esValidoElArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Archivo no seleccionado");
        } else if (!PERMITTED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("El archivo seleccionado no es una imagen.");
        } else if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Archivo demasiado grande. Sólo se admiten archivos < 10MB");
        } else {

            return true;

        }


    }

    public String generarNombreUnico(MultipartFile file) {
        UUID nombreUnico = UUID.randomUUID();
        String extension;
        if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } else {
            throw new IllegalArgumentException("El archivo seleccionado no es una imagen.");
        }
        return nombreUnico + extension;
    }


    public void guardarArchivo(MultipartFile file, String nuevoNombreFoto) {


        Path path = Paths.get(UPLOADS_DIRECTORY);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("El directorio fue creado con éxito.");
            } else {
                System.out.println("El directorio ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Hubo un error al crear el directorio.");
            e.printStackTrace();
        }




        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);


        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
        } catch (
                IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }

    public void eliminarFotosAnuncio(long id_Anuncio){

        Anuncio anuncio = anuncioRepository.findById(id_Anuncio).get();
        List<Foto> fotos = fotoProductoRepository.findByAnuncio(anuncio);
        for (Foto fotoDelete : fotos ) {
            fotoProductoRepository.delete(fotoDelete);
        }

    }


}
