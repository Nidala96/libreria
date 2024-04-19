package it.fabio.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Proxy;
import org.hibernate.type.SqlTypes;

import java.util.Date;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Proxy(lazy = false)
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

    @JdbcTypeCode(SqlTypes.JSON)
    private Image image;

    public Libro(String titolo, String autore, String codiceISBN, String trama, int numeroLettureComplete) {
        this.titolo = titolo;
        this.autore = autore;
        this.codiceISBN = codiceISBN;
        this.dataAggiunta = new Date();;
        this.trama = trama;
        this.numeroLettureComplete = numeroLettureComplete;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", codiceISBN='" + codiceISBN + '\'' +
                ", dataAggiunta=" + dataAggiunta +
                ", dataEliminazione=" + dataEliminazione +
                ", trama='" + trama + '\'' +
                ", numeroLettureComplete=" + numeroLettureComplete +
                '}';
    }


}
