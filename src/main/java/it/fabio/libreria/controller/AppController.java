package it.fabio.libreria.controller;

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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> getLibro(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long libroId){
        return libroService.getLibroById(userDetails,libroId);
    }


    @GetMapping("/get-libri")
    public ResponseEntity<?> getListaLibriPerUtente(@AuthenticationPrincipal UserDetails userDetails){
        return utenteService.getListaLibriPerUtente(userDetails);
    }

    @PutMapping("/mod-book")
    public ResponseEntity<?> modificaLibro(@RequestBody @Valid LibroRequest libroRequest, @AuthenticationPrincipal UserDetails userDetails, @RequestParam long libroId){
        return libroService.modificaLibro(libroRequest, userDetails, libroId);
    }
    @GetMapping
    public ResponseEntity<?> login(@RequestParam String nome, @RequestParam String cognome){
        return utenteService.login(nome, cognome);
    }

    @PutMapping("/mod-book-count")
    public ResponseEntity<?> modificaNumeroLetture(@AuthenticationPrincipal UserDetails userDetails, @RequestParam long libroId, @RequestParam int numeroLetture){
        return libroService.modificaNumeroLetture(userDetails, libroId, numeroLetture);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> eliminaLibro(@AuthenticationPrincipal UserDetails userDetails, @RequestParam long libroId){
        return libroService.eliminaLibro(userDetails, libroId);
    }

    @GetMapping("/get-csv")
    public ResponseEntity<?> getCsv(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return csvService.getListaLibriPerUtenteToStringArray(userDetails);
    }

    @PostMapping("/save-book")
    public ResponseEntity<?> aggiungiLibro(@RequestBody @Valid LibroRequest libroRequest, @AuthenticationPrincipal UserDetails userDetails) {
        return libroService.aggiungiLibro(libroRequest, userDetails);
    }


    @PutMapping(value = "/add-libro-image/{libroId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImageToContent(
            @RequestParam MultipartFile file,
            @PathVariable long libroId) throws IOException {
        return imageService.addImageToContent(size, width, height, extensions, file, libroId);
    }



}
