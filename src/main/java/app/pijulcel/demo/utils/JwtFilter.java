package app.pijulcel.demo.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        try {
            // ⚠️ 1. No se envió token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            // ⚠️ 2. Validar token
            if (!jwtUtil.validateToken(token)) {
                handleError(response, HttpStatus.UNAUTHORIZED, "Token inválido o manipulado");
                return;
            }

            // ✅ 3. Extraer datos del token
            String username = jwtUtil.getUsernameFromToken(token);
            Claims claims = jwtUtil.getClaims(token);

            String role = claims.get("role", String.class);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            handleError(response, HttpStatus.UNAUTHORIZED, "El token ha expirado, inicia sesión nuevamente");
        } catch (MalformedJwtException | SignatureException ex) {
            handleError(response, HttpStatus.UNAUTHORIZED, "Token inválido o corrupto");
        } catch (IllegalArgumentException ex) {
            handleError(response, HttpStatus.BAD_REQUEST, "No se proporcionó un token válido");
        } catch (Exception ex) {
            handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private void handleError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format(
                "{\"success\":false,\"status\":%d,\"message\":\"%s\"}",
                status.value(),
                message.replace("\"", "'")
        );
        response.getWriter().write(json);
    }
}
