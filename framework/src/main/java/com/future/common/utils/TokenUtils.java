package com.future.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具
 *
 * @author JonZhang
 */
public final class TokenUtils {

    private TokenUtils() {
    }

    private static final String ISSUER = "future";
    private static final String REQUEST_HEADER_TOKEN = "X-Access-Token";
    private static final String DEFAULT_SECRET = "$$future$$";

    /**
     * 从请求头中获取 token
     *
     * @param request 请求
     * @return JWT
     */
    public static String getTokenFromRequest(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

    /**
     * 生成 Token
     */
    @NotNull
    public static String generate(Map<String, Object> claims, 
                                  Duration validTime, String secret) {
        if (StringUtils.isEmpty(secret)) {
            secret = DEFAULT_SECRET;
        }
        Date expiresAt = new Date(System.currentTimeMillis() + validTime.getSeconds() * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withIssuer(ISSUER)
            .withExpiresAt(expiresAt)
            .withPayload(claims)
            .sign(algorithm);
    }

    public static boolean validate(@NotNull String token) {
        return validate(token, DEFAULT_SECRET);
    }

    /**
     * 校验 Token
     *
     * @param token  JWT
     * @param secret 密钥，通常使用用户密码
     * @return 校验通过返回 true
     */
    public static boolean validate(@NotNull String token, String secret) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

}
