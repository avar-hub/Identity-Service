package com.socials.IdentityService.service;

import com.socials.IdentityService.dto.AuthRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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
public class JWTService {

    private final AuthenticationManager authenticationManager;
    private static final String SECRET= "43d7640272c961817cbe57f9811a776dfde782048b35644ac1732778ea958806";
    public String generateToken(AuthRequest authRequest){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        if(authentication.isAuthenticated())
        {
            Map<String,Object> claims= new HashMap<>();
            return createToken(claims,authRequest.getEmail());
        }
        else
            throw new UsernameNotFoundException("User not found");
    }

    public String createToken(Map<String,Object> claims , String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
