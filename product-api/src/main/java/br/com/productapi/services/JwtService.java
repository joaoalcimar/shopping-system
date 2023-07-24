package br.com.productapi.services;


import br.com.productapi.configs.SecretsConfig;
import br.com.productapi.exceptions.AuthenticationException;
import br.com.productapi.models.dtos.responses.JwtResponse;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

@AllArgsConstructor
@Service
public class JwtService {

    @Autowired
    private SecretsConfig secretsConfig;

    private final static String BLANK_SPACE = " ";
    private final static Integer TOKEN_INDEX = 1;

    public void validateAuthorization(String token) {
        String accessToken = extractToken(token);
        String apiSecret = secretsConfig.getApiSecret();

        try {
            var claims = Jwts
                    .parserBuilder()
                    .setSigningKey(apiSecret.getBytes())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            JwtResponse user = JwtResponse.getUser(claims);
            if (isEmpty(user) || isEmpty(user.getId())) {
                throw new AuthenticationException("The user is not valid.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Error while trying to process the Access Token.");
        }
    }

    protected String extractToken(String token) {
        if (isEmpty(token)) {
            throw new AuthenticationException("The access token was not informed.");
        }

        if (token.contains(BLANK_SPACE)) {
            return token.split(BLANK_SPACE)[TOKEN_INDEX];
        }

        return token;
    }
}
