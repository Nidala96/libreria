package it.fabio.libreria.repository;

import it.fabio.libreria.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByNomeAndCognome(String nome, String cognome);

    Optional<Utente> findByNome(String nome);

    boolean existsByNome(String nome);
}
