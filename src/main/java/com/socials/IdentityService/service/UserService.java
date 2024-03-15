package com.socials.IdentityService.service;

import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.exception.UserNotCreatedException;
import com.socials.IdentityService.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCredentialRepo repo;

    private final PasswordEncoder encoder;
    public String saveUser(UserCredential userCredential) {
        userCredential.setPassword(encoder.encode(userCredential.getPassword()));
        UserCredential user= repo.save(userCredential);
        if(user==null)
            throw new UserNotCreatedException("User not saved in database");
        return "User Saved Successfully";
    }
}
