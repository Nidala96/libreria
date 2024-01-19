package it.fabio.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String titolo;

    @Column(length = 50)
    private String autore;

    @Column(length = 13)
    private String codiceISBN;

    @Column(updatable = false)
    private Date dataAggiunta;

    private Date dataEliminazione;

    @Column(length = 500)
    private String trama;

    private int numeroLettureComplete;

}
