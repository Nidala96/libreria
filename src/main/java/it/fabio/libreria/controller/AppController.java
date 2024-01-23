package it.fabio.libreria.controller;

import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.service.CsvService;
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

    private final CsvService csvService;
    private final UtenteService utenteService;

    @GetMapping("/get-libro/{libroId}")
    public ResponseEntity<?> getLibro(@PathVariable long libroId){
        return libroService.getLibroById(libroId);
    }

    @GetMapping("/get-libri")
    public ResponseEntity<?> getListaLibriPerUtente(@RequestParam long utenteId){
        return utenteService.getListaLibriPerUtente(utenteId);
    }

    @PostMapping("/save-book")
    public ResponseEntity<?> aggiungiLibro(@RequestBody @Valid LibroRequest libroRequest, @RequestParam long utenteId){
        return libroService.aggiungiLibro(libroRequest, utenteId);
    }

    @PutMapping("/mod-book")
    public ResponseEntity<?> modificaLibro(@RequestBody LibroRequest libroRequest, @RequestParam long utenteId, @RequestParam long libroId){
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



}
