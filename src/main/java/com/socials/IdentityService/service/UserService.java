package com.socials.IdentityService.service;

import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.exception.UserAlreadyExistsException;
import com.socials.IdentityService.exception.UserNotCreatedException;
import com.socials.IdentityService.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserCredentialRepo repo;

    private final PasswordEncoder encoder;
    public String saveUser(UserCredential userCredential) {

        log.info("In saveUser service of username : {}",userCredential.getEmail());
        String email= userCredential.getEmail();
        if(repo.findByEmail(email)!=null)
            throw new UserAlreadyExistsException("User present in database");
        userCredential.setPassword(encoder.encode(userCredential.getPassword()));
        UserCredential user= repo.save(userCredential);
        if(user==null)
            throw new UserNotCreatedException("User not saved in database");
        return "User Saved Successfully";
    }
}
