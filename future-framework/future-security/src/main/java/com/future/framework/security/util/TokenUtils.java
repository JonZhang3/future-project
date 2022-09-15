package com.future.framework.security.util;

import java.time.Duration;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.jetbrains.annotations.Nullable;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.future.framework.common.utils.StringUtils;
import com.future.framework.security.domain.LoginUser;

public final class TokenUtils {

    private TokenUtils() {
    }

    private static final String ISSUER = "future";
    private static final String DEFAULT_SECRET = "$$future$$";

    private static final String CLAIM_KEY_USERID = "userid";
    private static final String CLAIM_KEY_USERTYPE = "usertype";

    @NotNull
    public static String generate(@NotNull LoginUser user, @NotNull Duration validTime) {
        return generate(user, validTime, DEFAULT_SECRET);
    }

    /**
     * 生成 token
     *
     * @param user      登录用户
     * @param validTime 有效时间
     * @param secret    密钥
     * @return JWT
     */
    @NotNull
    public static String generate(@NotNull LoginUser user,
            @NotNull Duration validTime, String secret) {
        if (StringUtils.isEmpty(secret)) {
            secret = DEFAULT_SECRET;
        }
        Date expiresAt = new Date(System.currentTimeMillis() + validTime.getSeconds() * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(expiresAt)
                .withClaim(CLAIM_KEY_USERID, user.getId())
                .withClaim(CLAIM_KEY_USERTYPE, user.getUserType())
                .sign(algorithm);
    }

    public static boolean validate(@NotNull String token) {
        return validate(token, DEFAULT_SECRET);
    }

    /**
     * 校验 Token
     *
     * @param token  JWT
     * @param secret 密钥
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

    @Nullable
    public static LoginUser validateAndGetPayload(@NotNull String token) {
        return validateAndGetPayload(token, DEFAULT_SECRET);
    }

    /**
     * 校验并获取 JWT 中的负载内容
     *
     * @param token  JWT
     * @param secret 密钥，通常使用用户密码
     * @return JWT 中的负载
     */
    @Nullable
    public static LoginUser validateAndGetPayload(String token, @NotNull String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            return new LoginUser()
                    .setId(jwt.getClaim(CLAIM_KEY_USERID).asLong())
                    .setUserType(jwt.getClaim(CLAIM_KEY_USERTYPE).asInt());
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * 仅仅获取 JWT 中的负载内容，不对 JWT 进行校验
     *
     * @param token JWT
     * @return JWT 中的负载内容
     */
    @Nullable
    public static LoginUser parse(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return new LoginUser()
                    .setId(jwt.getClaim(CLAIM_KEY_USERID).asLong())
                    .setUserType(jwt.getClaim(CLAIM_KEY_USERTYPE).asInt());
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
