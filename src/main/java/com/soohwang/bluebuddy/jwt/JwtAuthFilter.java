package com.soohwang.bluebuddy.jwt;

import com.soohwang.bluebuddy.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            //jwt 유효성 검증
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                //유저와 토큰 일치 시 userDetails 생성
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                if (userDetails != null) {
                    // 접근권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}
