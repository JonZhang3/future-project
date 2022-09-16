package com.future.framework.security.domain;

import lombok.Data;

@Data
public class TokenResult {
    
    private String accessToken;

    private String refreshToken;

    private Long userId;
    
    public TokenResult() {
    }

    public TokenResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResult(String accessToken, String refreshToken, Long userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
    
}
