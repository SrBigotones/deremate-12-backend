package ar.edu.uade.deremateapp.back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtTokenUtil {

    private static final int JWT_TOKEN_VALIDITY =  5 * 60 * 60;
    private static final String secret = "dad2n9smyrxtr33e2mrou45vhcw8ssdad2n9smyrxtr33e2mrou45vhcw8ssdad2n9smyrxtr33e2mrou45vhcw8ss";

    public Boolean isValidToken(String token) {
        try {
            return !isTokenExpired(token);
        }
        catch (ExpiredJwtException e) {
            log.error("Token expirado: {}", token);
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    public Date getExpirationFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims).setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    private Claims getAllClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtTokenUtil.secret));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }
}
