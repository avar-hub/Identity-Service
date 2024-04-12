package com.socials.IdentityService.service;

import com.socials.IdentityService.dto.AuthRequest;
import com.socials.IdentityService.dto.JWTResponse;
import com.socials.IdentityService.entity.RefreshToken;
import com.socials.IdentityService.repository.UserCredentialRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {

    private final AuthenticationManager authenticationManager;

    private final UserCredentialRepo repo;

    private final RefreshTokenService refreshTokenService;
    private static final String SECRET= "43d7640272c961817cbe57f9811a776dfde782048b35644ac1732778ea958806";
    public String generateToken(AuthRequest authRequest){

        log.info("In generateToken service of username : {}", authRequest.getEmail());
        if(repo.findByEmail(authRequest.getEmail()).get().getActive()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                log.info("User is authenticated now");
                Map<String, Object> claims = new HashMap<>();
                return createToken(claims,authRequest.getEmail());
            } else
                throw new UsernameNotFoundException("User not found");
        }
        else
            throw new UsernameNotFoundException("Please validate email");
    }

    public String createToken(Map<String,Object> claims , String userName){

        log.info("In createToken service of username : {}", userName);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*3))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
