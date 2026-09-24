package fr.ipsl.soa.api_gateway.auth;

public record RegisterRequest(
        String username,
        String email,
        String password
) {}
