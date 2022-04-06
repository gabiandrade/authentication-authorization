package br.com.application.authenticationauthorization.core.security;

import br.com.application.authenticationauthorization.domain.data.UserDataDetails;
import br.com.application.authenticationauthorization.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int EXPIRATION_TOKEN = 3_600_000;
    public static final String PASSWORD_TOKEN = "ECommerceAppSecretKey";

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager =  authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            User user =  new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getLogin(),
                    user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Fail", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        UserDataDetails userDataDetails = (UserDataDetails) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(userDataDetails.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                                .sign(Algorithm.HMAC512(PASSWORD_TOKEN));

       response.getWriter().write(token);
       response.getWriter().flush();
    }
}
