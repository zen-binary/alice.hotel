package com.alice.hotel.api.config.security.app;

import com.alice.hotel.api.config.security.entrypoint.RestAuthenticationEntryPoint;
import com.alice.hotel.api.config.security.filter.RestAuthenticationFilter;
import com.alice.hotel.api.config.security.handler.RestAccessDeniedHandler;
import com.alice.hotel.api.config.security.handler.RestAuthenticationFailureHandler;
import com.alice.hotel.api.config.security.handler.RestAuthenticationSuccessHandler;
import com.alice.hotel.api.config.security.manager.CustomDynamicAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider restAuthenticationProvider;

    private final RestAuthenticationSuccessHandler restSuccessHandler;

    private final RestAuthenticationFailureHandler restFailureHandler;

    private final CustomDynamicAuthorizationManager authorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
                //.securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/auth/**",
                                        "/files/**",
                                        "/c/booking/hotel",
                                        "/c/booking/room-types",
                                        "/c/booking/services",
                                        "/c/booking/availability",
                                        "/c/booking/otp",
                                        "/c/booking/register",
                                        "/c/booking/vnpay/return",
                                        "/c/booking/vnpay/ipn"
                                ).permitAll()
                                .anyRequest().access(authorizationManager)
                ).headers(headers -> headers.xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                        .contentSecurityPolicy(csp -> csp.policyDirectives("script-src 'self'")))
//                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
//                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //.addFilterBefore(new XssFilter(), CsrfFilter.class)
                .addFilterBefore(restAuthenticationFilter(authenticationManager, http),
                        UsernamePasswordAuthenticationFilter.class).authenticationManager(authenticationManager)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(new RestAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:3001",
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost",
                "http://127.0.0.1:3000",
                "http://127.0.0.1:3001",
                "http://127.0.0.1:5173",
                "http://127.0.0.1:5174"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private RestAuthenticationFilter restAuthenticationFilter(AuthenticationManager authenticationManager,
            HttpSecurity http) {

        RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter(http);
        restAuthenticationFilter.setAuthenticationManager(authenticationManager);
        restAuthenticationFilter.setAuthenticationSuccessHandler(restSuccessHandler);
        restAuthenticationFilter.setAuthenticationFailureHandler(restFailureHandler);

        return restAuthenticationFilter;
    }

}

