package com.abhishek.blogapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     // 1. get token

        // Bearer kjlkjfwlej234
        String requestHeaderToken = request.getHeader("Authorization");
        logger.info(" Header :  {}", requestHeaderToken);


        System.out.println("requestToken : "+requestHeaderToken);

        String username = null;
        String token = null;

        if(requestHeaderToken != null && requestHeaderToken.startsWith("Bearer")){
             token = requestHeaderToken.substring(7);
             try {
                 username = this.jwtTokenHelper.getUsernameFromToken(token);
             }catch (IllegalArgumentException e){
                 logger.info("Illegal Argument while fetching the username !!");
             }catch (ExpiredJwtException e){
                 logger.info("Given jwt token is expired !!");
             }catch (MalformedJwtException e){
                 logger.info("Some changed has done in token !! Invalid Token");
             }
        }else{
            logger.info("Invalid Header Value !! ");
        }


        //2. once we get the token ,now validate

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);

            if(validateToken){
                // means everything is fine
                // now have to do the authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                logger.info("Validation fails !!");
            }
        }else{
            System.out.println("Username is null or context is not null");
        }

        filterChain.doFilter(request,response);
    }
}



