package app.pijulcel.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔹 Puedes cargarlo desde application.properties si quieres:
    // jwt.secret=MiClaveSecretaMuySeguraParaJWT123456789
    // jwt.expiration=3600000
    @Value("${jwt.secret:MiClaveSecretaMuySeguraParaJWT123456789}")
    private String SECRET_KEY;

    @Value("${jwt.expiration:3600000}") // 1 hora por defecto
    private long EXPIRATION_TIME;

    // Clave de firma segura (mínimo 32 bytes)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 🔹 Generar token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("APP-PIJULCEL")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Validar token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token expirado: " + e.getMessage());
            throw e; // se capturará en el filtro
        } catch (MalformedJwtException e) {
            System.out.println("❌ Token malformado: " + e.getMessage());
            throw e;
        } catch (SignatureException e) {
            System.out.println("❌ Firma JWT inválida: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Token vacío o nulo");
            throw e;
        }
    }

    // 🔹 Obtener Claims (datos completos dentro del token)
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🔹 Obtener usuario (subject)
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }
}