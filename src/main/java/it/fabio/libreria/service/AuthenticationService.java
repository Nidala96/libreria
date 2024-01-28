package it.fabio.libreria.service;


import it.fabio.libreria.entity.Utente;
import it.fabio.libreria.payload.request.SigninRequest;
import it.fabio.libreria.payload.request.SignupRequest;
import it.fabio.libreria.payload.response.AuthenticationResponse;
import it.fabio.libreria.repository.UtenteRepository;
import it.fabio.libreria.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UtenteRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> signup(SignupRequest request) {
        if(userRepository.existsByNome(request.getUsername()))
            return new ResponseEntity<String>("Username or email already in use", HttpStatus.BAD_REQUEST);
        Utente user = Utente.builder()
                .nome(request.getUsername())
                .email(request.getEmail())
                .cognome(request.getCognome())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        log.info("### "+user.toString());
        return new ResponseEntity<String>("User successfully registered",HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> signin(SigninRequest request) {
        Utente user = userRepository.findByNome(request.getUsername())
                .orElseThrow(()->  new BadCredentialsException("Bad credentials"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Bad credentials");

        String jwtToken = jwtService.generateToken( user, user.getId());
        return new ResponseEntity(AuthenticationResponse.builder()
                .id(Math.toIntExact(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .token(jwtToken)
                .build(),
                HttpStatus.OK);
    }


}
