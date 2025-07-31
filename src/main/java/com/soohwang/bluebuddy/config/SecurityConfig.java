package com.soohwang.bluebuddy.config;

import com.soohwang.bluebuddy.jwt.JwtAuthFilter;
import com.soohwang.bluebuddy.jwt.JwtUtil;
import com.soohwang.bluebuddy.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    // 인증인가 없이 접근 가능한 엔드포인트 리스트
    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/v3/api-docs.yaml", "/v3/api-docs", "/webjars/**",
            "/auth/signup", "/auth/login", "/mission/complete"
    };

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        //FormLogin, BasicHttp 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // JwtAuthFilter 추가
        http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 권한 규칙 작성
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
        );

        return http.build();

    }
}
