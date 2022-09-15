package com.future.framework.security.token;

public interface TokenStorage {
    
    void saveToken(TokenType type, String key, String token);
    
    String getToken(TokenType type, String key);
    
    void removeToken(TokenType type, String key);

    default String getPrefix(TokenType type) {
        if(TokenType.ACCESS_TOKEN.equals(type)) {
            return "token:access:";
        }
        return "token:refresh:";
    }

}
