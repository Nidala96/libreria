package it.fabio.libreria.controller;

import it.fabio.libreria.entity.Image;
import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.service.CsvService;
import it.fabio.libreria.service.ImageService;
import it.fabio.libreria.service.LibroService;
import it.fabio.libreria.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("libro")
@Validated
public class AppController {

    @Value("${application.contentImage.size}")
    private long size;
    @Value("${application.contentImage.width}")
    private int width;
    @Value("${application.contentImage.height}")
    private int height;
    @Value("${application.contentImage.extensions}")
    private String[] extensions;
    private final LibroService libroService;

    private final CsvService csvService;

    private final UtenteService utenteService;

    private final ImageService imageService;

    @GetMapping("/get-libro/{libroId}")
    public ResponseEntity<?> getLibro(@PathVariable long libroId){
        return libroService.getLibroById(libroId);
    }

    @GetMapping("/get-libri")
    public ResponseEntity<?> getListaLibriPerUtente(@RequestParam long utenteId){
        return utenteService.getListaLibriPerUtente(utenteId);
    }



    @PutMapping("/mod-book")
    public ResponseEntity<?> modificaLibro(@RequestBody @Valid LibroRequest libroRequest, @RequestParam long utenteId, @RequestParam long libroId){
        return libroService.modificaLibro(libroRequest, utenteId, libroId);
    }
    @GetMapping
    public ResponseEntity<?> login(@RequestParam String nome, @RequestParam String cognome){
        return utenteService.login(nome, cognome);
    }

    @PutMapping("/mod-book-count")
    public ResponseEntity<?> modificaNumeroLetture(@RequestParam long utenteId, @RequestParam long libroId, @RequestParam int numeroLetture){
        return libroService.modificaNumeroLetture(utenteId, libroId, numeroLetture);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> eliminaLibro(@RequestParam long utenteId, @RequestParam long libroId){
        return libroService.eliminaLibro(utenteId, libroId);
    }

    @GetMapping("/get-csv")
    public ResponseEntity<?> getCsv(@RequestParam long utenteId) throws Exception {
        return csvService.getListaLibriPerUtenteToStringArray(utenteId);
    }

    @PostMapping("/save-book")
    public ResponseEntity<?> aggiungiLibro(@RequestBody @Valid LibroRequest libroRequest, @RequestParam long utenteId) {
        return libroService.aggiungiLibro(libroRequest, utenteId);
    }


    @PutMapping(value = "/add-libro-image/{libroId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImageToContent(
            @RequestParam MultipartFile file,
            @PathVariable long libroId) throws IOException {
        return imageService.addImageToContent(size, width, height, extensions, file, libroId);
    }



}
