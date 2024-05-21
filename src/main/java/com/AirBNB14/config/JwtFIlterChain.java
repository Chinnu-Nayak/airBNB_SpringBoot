package com.AirBNB14.config;

import com.AirBNB14.Repo.PropertyUserRepository;
import com.AirBNB14.Service.JWTService;
import com.AirBNB14.entity.PropertyUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtFIlterChain extends OncePerRequestFilter {
    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public JwtFIlterChain(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer ")){
            String token = header.substring(8, header.length() - 1);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> byUsername = propertyUserRepository.findByUsername(username);
            if (byUsername.isPresent()){
                PropertyUser propertyUser = byUsername.get();

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(propertyUser,null,new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }

        filterChain.doFilter(request,response);
    }
}
