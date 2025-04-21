package com.example.demo.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.demo.Model.Repo.UserRepository;
import com.example.demo.Model.User;
import com.example.demo.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserRepository userRepository;


    public JWTRequestFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                String role = jwtService.getRole(token);

                Optional<User> opUser = userRepository.findByUsernameIgnoreCase(username);
                if (opUser.isPresent()) {
                    User user = opUser.get();
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            } catch (JWTDecodeException e) {
                System.out.println("JWT Decode error: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
