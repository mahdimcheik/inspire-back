package com.poec.projet_backend.config;

import com.poec.projet_backend.filter.JwtAuthenticationFilter;
import com.poec.projet_backend.user_app.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Data
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationErrors jwtAuthenticationErrors;
    private final AccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/**").disable())

            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/user/upload/image/mentor","/user/upload/image/student", "/uploads/images/**", "/images/**", "/static/images/**", "uploads/images/**","/**").permitAll()
                    .anyRequest().authenticated()
            )

            .exceptionHandling((exception) ->  exception
                    .authenticationEntryPoint(jwtAuthenticationErrors)
                    .accessDeniedHandler(accessDeniedHandler)
            )

            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();

    }
}
