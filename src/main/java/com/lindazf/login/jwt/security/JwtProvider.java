package com.lindazf.login.jwt.security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lindazf.login.jwt.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.sun.istack.Nullable;

@Component
public class JwtProvider {

    private String secretKey;
    private long validityInMilliseconds;
    private long valieInSeconds;
    private int validMinutes;

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}") int validMinutes) {

        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validMinutes * JwtProperties.MINUTE_TO_MILLISECOND;
        this.valieInSeconds = validMinutes * JwtProperties.MINUTE_TO_SECOND;
        this.validMinutes = validMinutes;
        log.info("valid minutes = " + validMinutes);
        log.info("validation in milliseconds = " + this.validityInMilliseconds);
    }

    /**
     * Create JWT string given username and roles.
     *
     * @param username
     * @param roles
     * @return
     */
    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(JwtProperties.ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .filter(Objects::nonNull).collect(Collectors.toList()));
        // Build the Token
        return createJWTToken(claims);
    }

    private String createJWTToken(Claims claims) {
        Date now = new Date();
        return Jwts.builder().setClaims(claims) //
                .setIssuedAt(now)//
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public boolean isValidToken(String token) {
        Jws<Claims> jws = getJwsClaims(token);
        return (jws != null);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsBodyFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<GrantedAuthority> getRoles(String token) {
        List<Map<String, String>> roleClaims = new ArrayList<>();
        Claims claims = getClaimsBodyFromToken(token);

        roleClaims = claims.get(JwtProperties.ROLES_KEY, List.class);
        return roleClaims.stream().map(roleClaim -> new SimpleGrantedAuthority(roleClaim.get(JwtProperties.AUTHORITY)))
                .collect(Collectors.toList());
    }

    private @Nullable Claims getClaimsBodyFromToken(String token) {
        Claims claims = null;
        if (isValidToken(token)) {
            claims = getJwsClaims(token).getBody();
        }
        return claims;
    }

    private Jws<Claims> getJwsClaims(String token) {
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        } catch (JwtException | IllegalArgumentException e) {
            log.error(String.format("Token [%s] is not valid", token));
        }
        return jws;
    }

    public String refreshToken(String token) {
        String refreshToken = null;
        Claims claims = getClaimsBodyFromToken(token);
        if (claims != null) {
            refreshToken = createJWTToken(claims);
        }
        return refreshToken;
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }

    public long getValieInSeconds() {
        return valieInSeconds;
    }

    public int getValidMinutes() {
        return validMinutes;
    }

    /**
     * Returns the token expiration date encapsulated within the token
     */
    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = getClaimsBodyFromToken(token);
        return claims.getExpiration();
    }

    /**
     * Get token issue Date
     *
     * @param token
     * @return
     */
    public Date getTokeIssueDateFromJWT(String token) {
        Claims claims = getClaimsBodyFromToken(token);
        return claims.getIssuedAt();
    }

}
