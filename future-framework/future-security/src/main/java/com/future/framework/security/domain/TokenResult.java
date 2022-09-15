package com.future.framework.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResult {
    
    private String accessToken;

    private String refreshToken;

}
