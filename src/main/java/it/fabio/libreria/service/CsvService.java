package it.fabio.libreria.service;

import com.opencsv.CSVWriter;
import it.fabio.libreria.entity.Libro;
import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.exception.ResourceNotFoundException;
import it.fabio.libreria.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final UtenteRepository utenteRepository;

    public ResponseEntity<?> getListaLibriPerUtenteToStringArray(long utenteId) throws Exception {
        String home = System.getProperty("user.home");

        Path filePath = Paths.get(home + "/Downloads/file.csv");
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new ResourceNotFoundException("Utente", "id", utenteId));
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
        writeLineByLine(listaArrayLibri,filePath);
        return new ResponseEntity<>(libri, HttpStatus.OK);
    }

    private String writeLineByLine(List<String[]> lines, Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            for (String[] line : lines) {
                writer.writeNext(line);
            }
            return path.toString();
        }
    }
}
