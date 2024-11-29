package com.akshay.backend.SecurityConfig;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal( @Nonnull HttpServletRequest request,
                                     @Nonnull HttpServletResponse response,
                                     @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String jwt=request.getHeader(JwtConstants.JwtHeader);

        if(jwt !=null && jwt.startsWith("Bearer "))
        {
            try{
                String email=JwtProvider.extractEmail(jwt);
                String role= JwtProvider.extractRole(jwt);

                Authentication authentication =new UsernamePasswordAuthenticationToken(email,
                        null,
                        List.of(new SimpleGrantedAuthority(role)));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch(Exception e)
            {
                throw new BadCredentialsException("Invalid token");

            }
        }
        filterChain.doFilter(request,response);
    }


}
