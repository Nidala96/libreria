package it.fabio.libreria.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
public class LibroRequest {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String titolo;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String autore;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 13)
    private String codiceISBN;

    private Date dataAggiunta;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    private String trama;

    private int numeroLettureComplete;
}
