package com.example.auth.service;

import com.example.auth.dto.AuthResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.model.MagicLinkToken;
import com.example.auth.model.User;
import com.example.auth.repo.InMemoryMagicLinkRepository;
import com.example.auth.repo.InMemoryUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final InMemoryUserRepository users = new InMemoryUserRepository();
    private final InMemoryMagicLinkRepository tokens = new InMemoryMagicLinkRepository();
    private final PasswordEncoder encoder;
    private final SecureRandom secureRandom = new SecureRandom();

    // 簡單的 Session 模擬（登入後產生 UUID 當 sessionToken）
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public AuthService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    // 註冊
    public void register(RegisterRequest req) {
        if (users.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email 已被註冊");
        }
        String hash = encoder.encode(req.getPassword());
        users.save(new User(req.getEmail(), hash));
    }

    // Email + 密碼登入
    public AuthResponse login(LoginRequest req) {
        User user = users.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("帳號或密碼錯誤"));

        if (!encoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("帳號或密碼錯誤");
        }
        String session = UUID.randomUUID().toString();
        sessions.put(session, user.getEmail());
        return new AuthResponse("Login success");
    }

    public AuthResponse issueMagicLink(String email) {
        // 若要避免帳號探測，可在這裡不檢查或統一回成功訊息
        // Optional<User> u = users.findByEmail(email);

        String token = generateUrlSafeToken(32);
        Instant expires = Instant.now().plus(Duration.ofMinutes(10));
        MagicLinkToken record = new MagicLinkToken(token, email, expires);
        tokens.save(record);

        String magicLinkUrl = "http://localhost:8080/auth/magic/consume?token=" + token;
        System.out.println("[MagicLink] send to " + email + " => " + magicLinkUrl);

        return new AuthResponse("Magic Link generated successfully.", token, magicLinkUrl);
    }

    // 使用 Magic Link 登入（一次性）
    public AuthResponse consumeMagicToken(String token) {
        MagicLinkToken record = tokens.find(token)
                .orElseThrow(() -> new IllegalArgumentException("Token 不存在或已使用"));

        if (record.isUsed()) {
            throw new IllegalArgumentException("Token 已使用");
        }
        if (record.isExpired()) {
            tokens.delete(token); // 也可直接刪除
            throw new IllegalArgumentException("Token 已過期");
        }

        // 標記一次性
        record.markUsed();
        tokens.delete(token); // 徹底移除

        String session = UUID.randomUUID().toString();
        sessions.put(session, record.getEmail());
        return new AuthResponse("Magic login success");
    }

    private String generateUrlSafeToken(int bytes) {
        byte[] buf = new byte[bytes];
        secureRandom.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}
