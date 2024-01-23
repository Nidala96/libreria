package it.fabio.libreria.service;

import com.sun.source.tree.TryTree;
import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.repository.LibroRepository;
import it.fabio.libreria.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibroService {


    private final UtenteRepository utenteRepository;
    private final LibroRepository libroRepository;

    public ResponseEntity<?> getLibroById(long idLibro) {
        try {
            Libro libro = libroRepository.getReferenceById(idLibro);
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Libro non trovato", HttpStatus.NOT_FOUND);
        }
    }



    @Transactional
    public ResponseEntity<?> aggiungiLibro(LibroRequest libroRequest, long utenteId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
        Set<Libro> libri = utente.getLibri();
        Libro libro = new Libro(libroRequest.getTitolo(),libroRequest.getAutore(), libroRequest.getCodiceISBN(), libroRequest.getDataAggiunta(),libroRequest.getTrama(),libroRequest.getNumeroLettureComplete());
        libri.add(libro);
        return new ResponseEntity<>("Aggiunto libro", HttpStatus.OK);

    }
    @Transactional
    public ResponseEntity<?> modificaLibro(LibroRequest libroRequest, long utenteId, long libroId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
        Set<Libro> libri = utente.getLibri();
        Optional<Libro> libroDaModificare = libri.stream()
                .filter(libro -> libro.getId() == libroId)
                .findFirst();
        if(libroDaModificare.isEmpty())
            return new ResponseEntity<>("Libro non trovato", HttpStatus.NOT_FOUND);
        Libro libro = libroDaModificare.get();
        libro.setTitolo(libroRequest.getTitolo());
        libro.setAutore(libroRequest.getAutore());
        libro.setTrama(libroRequest.getTrama());
        libro.setCodiceISBN(libroRequest.getCodiceISBN());
        libro.setNumeroLettureComplete(libroRequest.getNumeroLettureComplete());
        return new ResponseEntity<>("Libro Modificato", HttpStatus.OK);


    }
    @Transactional
    public ResponseEntity<?> modificaNumeroLetture(long utenteId, long libroId, int numeroLetture) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
        Set<Libro> libri = utente.getLibri();
        Optional<Libro> libroDaModificare = libri.stream()
                .filter(libro -> libro.getId() == libroId)
                .findFirst();
        if(libroDaModificare.isEmpty())
            return new ResponseEntity<>("Libro non trovato", HttpStatus.NOT_FOUND);
        Libro libro = libroDaModificare.get();
        libro.setNumeroLettureComplete(numeroLetture);
        return new ResponseEntity<>("Numero letture modificato", HttpStatus.OK);

    }
    @Transactional
    public ResponseEntity<?> eliminaLibro(long utenteId, long libroId) {
            Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
            Set<Libro> libri = utente.getLibri();
            Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new ResourceNotFoundException("Libro", "id", libroId));
            libro.setDataEliminazione(new Date());
            if (!libri.remove(libro)) {
                throw new ResourceNotFoundException("libro", "id", libroId);
            }
            return new ResponseEntity<>("Libro eliminato", HttpStatus.OK);
        }
}
