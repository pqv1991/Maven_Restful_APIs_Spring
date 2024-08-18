package _1.vietpq.job_hunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
@Service
public class SecurityUtil {
    @Value("${jwt.base64-secret}")
    private String jwtKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long jwtExpiration;

    public static final  MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    

    private SecretKey getSecretKey(){
        byte[] keyBytes = com.nimbusds.jose.util.Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }
    @Bean
    public JwtEncoder jwtEncoder(){
        return  new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return token->{
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }

    public String createToken(Authentication authentication){
        Instant now = Instant.now();
        Instant validity = now.plus(jwtExpiration,ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                .issuedAt(now)
                                .expiresAt(validity)
                                .subject(authentication.getName())
                                .claim("Hoi dan it", authentication)
                                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        String token  = jwtEncoder().encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
        return token;

    }

    
}
