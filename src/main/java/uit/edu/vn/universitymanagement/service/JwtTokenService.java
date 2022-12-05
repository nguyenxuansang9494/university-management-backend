package uit.edu.vn.universitymanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.dto.JwtTokenDto;
import uit.edu.vn.universitymanagement.model.entity.Account;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duration}")
    private long duration;


    private Key getKey() {
        return new SecretKeySpec(Base64.getEncoder().encode(secret.getBytes()), SignatureAlgorithm.HS256.getJcaName());
    }

    public JwtTokenDto provideJwtToken(Account account) {
        Date now = new Date();
        Date expireAt = Date.from(now.toInstant().plus(duration, ChronoUnit.SECONDS));
        String token = "Bearer " + Jwts.builder()
                .setExpiration(expireAt)
                .setIssuedAt(now)
                .setSubject(Long.toString(account.getId()))
                .claim("username", account.getUsername())
                .claim("authority", account.getAuthorities())
                .signWith(getKey())
                .compact();
        return new JwtTokenDto("Bearer", token, now, expireAt);
    }

    public long getAccountId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
