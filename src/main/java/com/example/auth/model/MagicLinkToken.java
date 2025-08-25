package com.example.auth.model;

import java.time.Instant;

public class MagicLinkToken {
    private final String token;
    private final String email;
    private final Instant expiresAt;
    private boolean used;

    public MagicLinkToken(String token, String email, Instant expiresAt) {
        this.token = token;
        this.email = email;
        this.expiresAt = expiresAt;
        this.used = false;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void markUsed() {
        this.used = true;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
