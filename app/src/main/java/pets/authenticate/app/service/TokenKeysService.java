package pets.authenticate.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import pets.authenticate.app.model.TokenRequest;
import pets.authenticate.app.util.Util;

import java.util.Date;
import java.util.Map;

@Slf4j
public class TokenKeysService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final long EXPIRATION = 1800000;    // 30 MINUTES

    public String createToken(TokenRequest tokenRequest) {
        String token = null;

        try {
            TokenRequest tokenClaim = TokenRequest.builder()
                    .username(tokenRequest.getUsername())
                    .sourceIp(tokenRequest.getSourceIp())
                    .build();

            Map<String, Object> claims = objectMapper.convertValue(tokenClaim, new TypeReference<>() {
            });

            token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, Util.getSystemEnvProperty(Util.SECRET_KEY))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .compact();
        } catch (Exception ex) {
            log.error("Error in creating token: {}", tokenRequest, ex);
        }

        return token;
    }

    public String refreshToken(String oldToken, boolean isLogOut) {
        String newToken = null;

        try {
            Date expirationDate;
            if (isLogOut) {
                expirationDate = new Date(System.currentTimeMillis() + 5000);
            } else {
                expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(Util.getSystemEnvProperty(Util.SECRET_KEY))
                    .parseClaimsJws(oldToken)
                    .getBody();
            // TokenRequest tokenRequest = objectMapper.convertValue(claims, TokenRequest.class);   // NOSONAR
            newToken = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, Util.getSystemEnvProperty(Util.SECRET_KEY))
                    .setExpiration(expirationDate)
                    .compact();
        } catch (Exception ex) {
            log.error("Error in decoding old token: {}", oldToken, ex);
        }

        return newToken;
    }
}
