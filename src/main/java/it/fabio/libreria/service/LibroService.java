package it.fabio.libreria.service;

import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.repository.LibroRepository;
import it.fabio.libreria.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibroService {


    private final UtenteRepository utenteRepository;
    private final LibroRepository libroRepository;

    public ResponseEntity<?> getLibroById(long idUtente) {
        Libro libro = libroRepository.getReferenceById(idUtente);
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> aggiungiLibro(LibroRequest libroRequest, long utenteId) {
        Utente utente = utenteRepository.getReferenceById(utenteId);
        Set<Libro> libri = utente.getLibri();
        Libro libro = new Libro(libroRequest.getTitolo(),libroRequest.getAutore(), libroRequest.getCodiceISBN(), libroRequest.getDataAggiunta(),libroRequest.getTrama(),libroRequest.getNumeroLettureComplete());
        libri.add(libro);
        return new ResponseEntity<>("Aggiunto libro", HttpStatus.OK);

    }
}
