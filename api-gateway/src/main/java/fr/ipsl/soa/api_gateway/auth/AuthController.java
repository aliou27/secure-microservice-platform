package fr.ipsl.soa.api_gateway.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final KeycloakRegistrationService registrationService;

    public AuthController(KeycloakRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(
            @RequestBody RegisterRequest request) {

        return registrationService.register(request)
                .thenReturn(
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body("User registered successfully")
                );
    }
}