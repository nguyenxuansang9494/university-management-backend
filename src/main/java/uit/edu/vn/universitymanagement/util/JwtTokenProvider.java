package uit.edu.vn.universitymanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uit.edu.vn.universitymanagement.dto.JwtTokenDto;
import uit.edu.vn.universitymanagement.model.entity.Account;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private final String secret;

    @Value("${jwt.duration}")
    private final long duration;


    private Key getKey() {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public JwtTokenDto provideJwtToken(Account account) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + duration);
        String token = Jwts.builder()
                .setExpiration(expireAt)
                .setIssuedAt(now)
                .setSubject(Long.toString(account.getId()))
                .claim("username", account.getUsername())
                .claim("authority", account.getAuthorities())
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return new JwtTokenDto("Bearer", token, now, expireAt);
    }

    public long getAccountId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJwt(token)
                    .getBody()
                    .getExpiration();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
