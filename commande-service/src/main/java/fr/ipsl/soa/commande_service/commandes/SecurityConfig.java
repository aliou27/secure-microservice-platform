package fr.ipsl.soa.commande_service.commandes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/commandes/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(source -> {
                            var auth = new fr.ipsl.soa.produits.JwtRoleConverter().convert(source);
                            return new org.springframework.security.oauth2.server.resource
                                    .authentication.JwtAuthenticationToken(source, auth);
                        }))
                );
        return http.build();
    }
}