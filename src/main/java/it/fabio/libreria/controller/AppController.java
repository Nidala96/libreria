package it.fabio.libreria.controller;

import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.service.LibroService;
import it.fabio.libreria.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("libro")
public class AppController {

    private final LibroService libroService;

    private final UtenteService utenteService;

    @GetMapping("/get-libro/{libroId}")
    public ResponseEntity<?> getLibro(@PathVariable long libroId){
        return libroService.getLibroById(libroId);
    }

    @GetMapping("/get-libri/{utenteId}")
    public ResponseEntity<?> getListaLibriPerUtente(@PathVariable long utenteId){
        return utenteService.getListaLibriPerUtente(utenteId);
    }

    @PostMapping("/save-book/")
    public ResponseEntity<?> aggiungiLibro(@RequestBody @Valid LibroRequest libroRequest, @RequestParam long utenteId){
        return libroService.aggiungiLibro(libroRequest, utenteId);
    }


}
