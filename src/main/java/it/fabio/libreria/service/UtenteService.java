package it.fabio.libreria.service;

import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.exception.OwnerException;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public ResponseEntity<?> getListaLibriPerUtente(UserDetails userDetails) {
        Utente utente = (Utente) userDetails;
        Set<Libro> libri = utente.getLibri();
        return new ResponseEntity<>(libri, HttpStatus.OK);
    }

    public ResponseEntity<?> login(String nome, String cognome) {
        Utente utente = utenteRepository.findByNomeAndCognome(nome, cognome);
        if(utente == null)
            return new ResponseEntity<>("Utente non trovato", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(utente.getId(), HttpStatus.OK);
    }

}
