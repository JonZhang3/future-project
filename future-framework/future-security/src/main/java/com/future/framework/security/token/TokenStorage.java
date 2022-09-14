package com.future.framework.security.token;

public interface TokenStorage {
    
    void saveToken(TokenType type, String token);
    
    String getToken(TokenType type);
    
}
