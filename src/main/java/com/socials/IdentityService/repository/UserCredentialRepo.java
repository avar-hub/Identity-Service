package com.socials.IdentityService.repository;

import com.socials.IdentityService.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepo extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByEmail(String username);
}
