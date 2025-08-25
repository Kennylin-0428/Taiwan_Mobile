package com.example.auth.repo;

import com.example.auth.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository {
    private final ConcurrentHashMap<String, User> byEmail = new ConcurrentHashMap<>();

    public boolean existsByEmail(String email) {
        return byEmail.containsKey(email.toLowerCase());
    }

    public void save(User user) {
        byEmail.put(user.getEmail().toLowerCase(), user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(byEmail.get(email.toLowerCase()));
    }
}
