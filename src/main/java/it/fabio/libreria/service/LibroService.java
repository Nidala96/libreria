package it.fabio.libreria.service;

import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.exception.OwnerException;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.payload.request.LibroRequest;
import it.fabio.libreria.payload.response.LibroResponse;
import it.fabio.libreria.repository.LibroRepository;
import it.fabio.libreria.repository.UtenteRepository;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final UtenteRepository utenteRepository;
    private final LibroRepository libroRepository;

    private final ImageService imageService;

    public ResponseEntity<?> getLibroById(UserDetails userDetails, long libroId) {
        try {
            Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new ResourceNotFoundException("Libro", "id", libroId));
            isOwner(userDetails, libro.getUtente());
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Libro non trovato", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<?> aggiungiLibro(LibroRequest libroRequest, UserDetails userDetails) {
        Utente utenteData = (Utente) userDetails;
        Utente utente = utenteRepository.findById(utenteData.getId()).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteData.getId()));
        Set<Libro> libri = utente.getLibri();
        Libro libro = new Libro(libroRequest.getTitolo(), libroRequest.getAutore(), libroRequest.getCodiceISBN(), libroRequest.getTrama(), libroRequest.getNumeroLettureComplete());
        libri.add(libro);
        libro.setUtente(utente);
        return new ResponseEntity<>(libro, HttpStatus.CREATED);

    }

    @Transactional
    public ResponseEntity<?> modificaLibro(LibroRequest libroRequest, UserDetails userDetails, long libroId) {
        Utente utenteData = (Utente) userDetails;
        Utente utente = utenteRepository.findById(utenteData.getId()).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteData.getId()));
        Set<Libro> libri = utente.getLibri();
        Optional<Libro> libroDaModificare = libri.stream()
                .filter(libro -> libro.getId() == libroId)
                .findFirst();
        if (libroDaModificare.isEmpty())
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
    public ResponseEntity<?> modificaNumeroLetture(UserDetails userDetails, long libroId, int numeroLetture) {
        Utente utenteData = (Utente) userDetails;
        Utente utente = utenteRepository.findById(utenteData.getId()).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteData.getId()));
        Set<Libro> libri = utente.getLibri();
        Optional<Libro> libroDaModificare = libri.stream()
                .filter(libro -> libro.getId() == libroId)
                .findFirst();
        if (libroDaModificare.isEmpty())
            return new ResponseEntity<>("Libro non trovato", HttpStatus.NOT_FOUND);
        Libro libro = libroDaModificare.get();
        libro.setNumeroLettureComplete(numeroLetture);
        return new ResponseEntity<>("Numero letture modificato", HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> eliminaLibro(UserDetails userDetails, long libroId) {
        Utente utenteData = (Utente) userDetails;
        Utente utente = utenteRepository.findById(utenteData.getId()).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteData.getId()));
        Set<Libro> libri = utente.getLibri();
        Libro libro = libroRepository.findById(libroId).orElseThrow(() -> new ResourceNotFoundException("Libro", "id", libroId));
        libro.setDataEliminazione(new Date());
        libro.setUtente(null);
        if (!libri.remove(libro)) {
            throw new ResourceNotFoundException("libro", "id", libroId);
        }
        return new ResponseEntity<>("Libro eliminato", HttpStatus.OK);
    }

    protected void isOwner(UserDetails userDetails, Utente utente) {
        if (!utente.equals(userDetails))
            throw new OwnerException();
    }
}
