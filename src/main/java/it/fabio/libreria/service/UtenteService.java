package it.fabio.libreria.service;

import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public ResponseEntity<?> getListaLibriPerUtente(long utenteId) {
        Utente utente = utenteRepository.getReferenceById(utenteId);
        Set<Libro> libri = utente.getLibri();
        return new ResponseEntity<>(libri, HttpStatus.OK);
    }
}
