package com.socials.IdentityService.service;

import com.socials.IdentityService.entity.RefreshToken;
import com.socials.IdentityService.exception.UserNotCreatedException;
import com.socials.IdentityService.repository.RefreshTokenRepo;
import com.socials.IdentityService.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    private final UserCredentialRepo userCredentialRepo;

    public RefreshToken createRefreshToken(String email){
        RefreshToken refreshToken= RefreshToken.builder()
                .userCredential(userCredentialRepo.findByEmail(email).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000*60*10))
                .build();
        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiry(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }
}
