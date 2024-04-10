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
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .userCredential(userCredentialRepo.findByEmail(email)
                        .orElseThrow(()-> new UserNotCreatedException("User not found")))
                .build();
        return refreshToken;
    }

    public RefreshToken verifyExpiry(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            throw new RuntimeException("Sign in again");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }
}
