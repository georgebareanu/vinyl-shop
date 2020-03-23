package ro.fortech.internship.vinylshop.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.fortech.internship.vinylshop.user.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final long JWT_VALIDITY_12_HOURS = 24 * 60 * 60 * 1000L;
    private final String secret;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generate(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getType());
        return generate(claims, user.getEmail());
    }

    private String generate(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY_12_HOURS))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
}
