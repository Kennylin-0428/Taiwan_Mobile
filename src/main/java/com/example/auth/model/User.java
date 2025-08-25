package com.example.auth.model;

import java.time.Instant;

public class User {
    private final String email;
    private final String passwordHash; // BCrypt
    private final Instant createdAt;

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
