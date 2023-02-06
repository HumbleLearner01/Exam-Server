package com.rest.config.jwt;

import com.rest.config.jwt.userdetailsservice.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get the header and store it in a variable
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("JwtAuthenticationFilter -> doFilterInternal: " + requestTokenHeader);
        String jwtToken = null, username = null;

        // get the token from the header (we got the header from the above line)
        // if we have a token.
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("<<<<<<<<<< JwtAuthenticationFilter -> doFilterInternal: \"Jwt token has been expired\" >>>>>>>>>>");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("JwtAuthenticationFilter -> doFilterInternal");
            }
        } else
            System.out.println("<<<<<<<<<< JwtAuthenticationFilter -> doFilterInternal: \"Invalid Token! there is no token to authorize you\"" +
                               " \"or token does not start with 'Bearer' string\" >>>>>>>>>>");

        //now validate the token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                //token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //now set the authentication
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else System.out.println("<<<<<<<<<< JwtAuthenticationFilter -> doFilterInternal: Invalid Token >>>>>>>>>>");
        filterChain.doFilter(request, response);
    }
}