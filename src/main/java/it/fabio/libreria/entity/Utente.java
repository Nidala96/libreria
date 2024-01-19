package it.fabio.libreria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String nome;

    @Column(nullable = false, length = 15)
    private String cognome;

    @Column(nullable = false, length = 20)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="libri_utente",
            joinColumns = {@JoinColumn(name="utente_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="libro_id", referencedColumnName = "id")}
    )
    private Set<Libro> authorities = new HashSet<>();

}
