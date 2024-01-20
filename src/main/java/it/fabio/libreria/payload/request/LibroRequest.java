package it.fabio.libreria.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class LibroRequest {

    @Size(min = 1, max = 50)
    private String titolo;

    @Size(min = 1, max = 50)
    private String autore;

    @Size(min = 13, max = 13)
    private String codiceISBN;

    private Date dataAggiunta;

    @Size(min = 10, max = 500)
    private String trama;

    @Size(min = 1)
    private int numeroLettureComplete;
}
