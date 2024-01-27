package it.fabio.libreria.service;

import com.opencsv.CSVWriter;
import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final UtenteRepository utenteRepository;

    public ResponseEntity<?> getListaLibriPerUtenteToStringArray(UserDetails userDetails) throws Exception {
        Utente utenteData = (Utente) userDetails;
        Utente utente = utenteRepository.findById(utenteData.getId()).orElseThrow();
        if(utente == null) {
            return new ResponseEntity<>("Utente non esiste", HttpStatus.NOT_FOUND);
        }
        Set<Libro> libri = utente.getLibri();
        if(libri.isEmpty()){
            return new ResponseEntity<>("Nessun libro e' stato trovato", HttpStatus.NOT_FOUND);
        }
        List<String[]> listaArrayLibri = new ArrayList<>();
        for(Libro lib : libri) {
            String[] libro = new String[6];
            libro[0] = lib.getTitolo();
            libro[1] = lib.getAutore();
            libro[2] = lib.getCodiceISBN();
            libro[3] = String.valueOf(lib.getDataAggiunta());
            libro[4] = lib.getTrama();
            libro[5] = String.valueOf(lib.getNumeroLettureComplete());
            listaArrayLibri.add(libro);
        }
        String data = getCsvString(listaArrayLibri);
        return ResponseEntity.ok()
                .contentLength(data.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    private String getCsvString(List<String[]> lines) throws Exception {
        StringWriter sw = new StringWriter();
        try (CSVWriter writer = new CSVWriter(sw)) {
            for (String[] line : lines) {
                writer.writeNext(line);
            }
            return sw.toString();
        }
    }
}
