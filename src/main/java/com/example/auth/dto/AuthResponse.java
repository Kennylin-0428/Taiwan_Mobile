package com.example.auth.dto;

public class AuthResponse {
    private String message;
    private String token; // 可以存 sessionToken 或 magic link 的 token
    private String magicLinkUrl; // 可選：直接把完整 Magic Link 連結也回傳

    public AuthResponse() {
    }

    public AuthResponse(String message) {
        this.message = message;
    }

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public AuthResponse(String message, String token, String magicLinkUrl) {
        this.message = message;
        this.token = token;
        this.magicLinkUrl = magicLinkUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMagicLinkUrl() {
        return magicLinkUrl;
    }

    public void setMagicLinkUrl(String magicLinkUrl) {
        this.magicLinkUrl = magicLinkUrl;
    }
}
