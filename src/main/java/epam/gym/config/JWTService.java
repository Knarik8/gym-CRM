package epam.gym.config;

import epam.gym.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;


    public String extractUserName(String token) { //извлекает имя пользователя из токена
        return extractClaim(token, Claims::getSubject);  //extractClaim разбирает токен и извлекает конкретный claim.
    }

    public String generateToken(UserDetails userDetails) { // JWT-токен для заданного пользователя. В claims добавляется информация о пользователе, такая как ID, имя и роли затем используется метод generateToken для создания токена с заданными claims..
        System.out.println(Base64.getUrlEncoder().withoutPadding().encodeToString(jwtSigningKey.getBytes()));
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRoles());
        }
        return generateToken(claims, userDetails);
    }


    // generateToken для создания токена с заданными claims.
    // Создает JWT-токен с использованием заданных claims, устанавливает время создания и истечения токена.
    //Подписывает токен с использованием алгоритма HMAC-SHA256 и секретного ключа.
    //Возвращает компактный JWT-токен в виде строки.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //разбирает токен и извлекает конкретный claim.
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSigningKey.trim());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
