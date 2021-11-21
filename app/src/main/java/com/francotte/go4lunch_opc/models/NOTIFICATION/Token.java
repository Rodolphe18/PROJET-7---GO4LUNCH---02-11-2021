package com.francotte.go4lunch_opc.models.NOTIFICATION;

public class Token {

    private String token;

    public Token() {
    }
    public Token(String token) {
        this.token = token;
    }

    // --- GETTER --- //
    public String getToken() {
        return token;
    }
    // --- SETTER --- //
    public void setToken(String token) {
        this.token = token;
    }
}
