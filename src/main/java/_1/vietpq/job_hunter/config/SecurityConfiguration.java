package _1.vietpq.job_hunter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity(securedEnabled=true)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
        http .csrf(c->c.disable())
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(
                authz -> authz.requestMatchers("/api/v1","api/v1/auth/login","api/v1/auth/refresh").permitAll()
                                .anyRequest().authenticated()
                               
                   
            )
            .oauth2ResourceServer(oauth2->oauth2. jwt(org.springframework.security.config.Customizer.withDefaults())
            .authenticationEntryPoint(customAuthenticationEntryPoint))
            .formLogin(f->f.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


}
