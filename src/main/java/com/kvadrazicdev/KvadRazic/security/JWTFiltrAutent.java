package com.kvadrazicdev.KvadRazic.security;

import com.kvadrazicdev.KvadRazic.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFiltrAutent extends OncePerRequestFilter {
    //první linie zabezpečení
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CachingUserDetailsService cachingUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String zahlaviAutor = request.getHeader("Authorization");
        final String jwtToken;
        final String emailUzivatele;

        if (zahlaviAutor == null || zahlaviAutor.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = zahlaviAutor.substring(7);
        emailUzivatele = jwtUtils.extrahujJmenoUzivatele(jwtToken);
        if (emailUzivatele != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = cachingUserDetailsService.loadUserByUsername(emailUzivatele);
            if(jwtUtils.jeTokenValidni(jwtToken, userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
