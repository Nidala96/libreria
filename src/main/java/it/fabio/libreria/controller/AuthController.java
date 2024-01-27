package it.fabio.libreria.controller;

import it.fabio.libreria.payload.request.SigninRequest;
import it.fabio.libreria.payload.request.SignupRequest;
import it.fabio.libreria.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {


    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest){
        return authenticationService.signup(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SigninRequest signinRequest){
        return authenticationService.signin(signinRequest);
    }
}
