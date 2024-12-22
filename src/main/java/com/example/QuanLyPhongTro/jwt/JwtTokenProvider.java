package com.example.QuanLyPhongTro.jwt;

import com.example.QuanLyPhongTro.models.Admins;
import com.example.QuanLyPhongTro.models.CustomUserDetails;
import com.example.QuanLyPhongTro.models.Users;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long JWT_EXPIRATION = 604800000L; // 7 ngày

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        int userId;
        String role;
        String fullName;
        int service;

        // Lấy ID và role từ user
        if (userDetails.getUser() instanceof Users) {
            userId = ((Users) userDetails.getUser()).getId();
            role = "ROLE_USER"; // Hoặc lấy từ cơ sở dữ liệu
            fullName=((Users) userDetails.getUser()).getFullName();
            service=((Users) userDetails.getUser()).getServicePackage().getId();
        } else if (userDetails.getUser() instanceof Admins) {
            userId = ((Admins) userDetails.getUser()).getId();
            role = "ROLE_ADMIN"; // Hoặc lấy từ cơ sở dữ liệu
            fullName="Admin";
            service=0;
        } else {
            throw new IllegalArgumentException("User type is not supported");
        }

        // Tạo JWT với claims bao gồm ID và role
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .claim("role", role)
                .claim("fullName",fullName)
                .claim("service",service)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public int getUserIdFromJWT(String token) {
        return Integer.parseInt(getClaimsFromToken(token).getSubject());
    }

    // Lấy role từ token
    public String getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }

    // Phương thức để lấy tên người dùng từ token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
             log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
             log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
             log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
             log.error("JWT claims string is empty.");
        }
        return false;
    }
}