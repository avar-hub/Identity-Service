package com.socials.IdentityService.service;

import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.repository.UserCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential= repo.findByEmail(username);
       return userCredential.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
}
