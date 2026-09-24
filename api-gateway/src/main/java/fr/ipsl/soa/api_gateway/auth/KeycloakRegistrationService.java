package fr.ipsl.soa.api_gateway.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakRegistrationService {

    private final WebClient webClient;

    @Value("${keycloak.registration.client-id}")
    private String clientId;

    @Value("${keycloak.registration.client-secret}")
    private String clientSecret;

    @Value("${keycloak.registration.token-uri}")
    private String tokenUri;

    @Value("${keycloak.registration.admin-uri}")
    private String adminUri;

    public KeycloakRegistrationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Void> register(RegisterRequest request) {
        return getServiceAccountToken()
                .flatMap(token -> createUser(token, request))
                .flatMap(result -> assignUserRole(result.accessToken(), result.userId()));
    }

    private Mono<String> getServiceAccountToken() {
        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Object token = response.get("access_token");

                    if (token == null) {
                        throw new RuntimeException(
                                "Unable to obtain Keycloak service account token"
                        );
                    }

                    return token.toString();
                });
    }

    private Mono<UserCreationResult> createUser(
            String accessToken,
            RegisterRequest request) {

        Map<String, Object> credential = Map.of(
                "type", "password",
                "value", request.password(),
                "temporary", false
        );

        Map<String, Object> user = Map.of(
                "username", request.username(),
                "email", request.email(),
                "enabled", true,
                "emailVerified", false,
                "credentials", List.of(credential)
        );

        return webClient.post()
                .uri(adminUri + "/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    URI location = response.getHeaders().getLocation();

                    if (location == null) {
                        throw new RuntimeException(
                                "Keycloak did not return the created user location"
                        );
                    }

                    String path = location.getPath();
                    String userId = path.substring(path.lastIndexOf('/') + 1);

                    return new UserCreationResult(accessToken, userId);
                });
    }

    private Mono<Void> assignUserRole(
            String accessToken,
            String userId) {

        return webClient.get()
                .uri(adminUri + "/roles/ROLE_USER")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(role ->
                        webClient.post()
                                .uri(adminUri + "/users/" + userId
                                        + "/role-mappings/realm")
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        "Bearer " + accessToken
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(List.of(role))
                                .retrieve()
                                .toBodilessEntity()
                )
                .then();
    }

    private record UserCreationResult(
            String accessToken,
            String userId
    ) {}
}