package com.example.auth.repo;

import com.example.auth.model.MagicLinkToken;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMagicLinkRepository {
    private final ConcurrentHashMap<String, MagicLinkToken> byToken = new ConcurrentHashMap<>();

    public void save(MagicLinkToken token) {
        byToken.put(token.getToken(), token);
    }

    public Optional<MagicLinkToken> find(String token) {
        return Optional.ofNullable(byToken.get(token));
    }

    public void delete(String token) {
        byToken.remove(token);
    }
}
