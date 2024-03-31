package com.socials.IdentityService.service;

import com.socials.IdentityService.dto.UserCredentialDto;
import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.exception.UserAlreadyExistsException;
import com.socials.IdentityService.exception.UserNotCreatedException;
import com.socials.IdentityService.repository.UserCredentialRepo;
import com.socials.IdentityService.util.EmailUtil;
import com.socials.IdentityService.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserCredentialRepo repo;

    private final OtpUtil otpUtil;

    private final EmailUtil emailUtil;

    private final PasswordEncoder encoder;
    public String saveUser(UserCredentialDto userCredential) {

        log.info("In saveUser service of username : {}",userCredential.getEmail());
        String otp= otpUtil.generateOtp();
        emailUtil.sentOtpEmail(userCredential.getEmail(),otp);
        UserCredential userCredential1 = UserCredential.builder()
                .email(userCredential.getEmail()).password(encoder.encode(userCredential.getPassword()))
                .otp(otp).otpGeneratedTime(LocalDateTime.now())
                .build();
        UserCredential user= repo.save(userCredential1);
        if(user==null)
            throw new UserNotCreatedException("User not saved in database");
        return "User Saved Successfully";
    }

    public String verifyUser(String email, String otp) {
      UserCredential user = repo.findByEmail(email).orElseThrow(()-> new UserNotCreatedException("User not found"));
      if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<(10*60)){
          user.setActive(true);
          repo.save(user);
          return "User Verified "+ email;
      }
      else {
          return "Please signup again";
      }
    }
}
