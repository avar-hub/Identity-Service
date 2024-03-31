package com.socials.IdentityService.controller;

import com.socials.IdentityService.dto.AuthRequest;
import com.socials.IdentityService.dto.UserCredentialDto;
import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.service.JWTService;
import com.socials.IdentityService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class UserController {


    private final UserService userService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody @Valid UserCredentialDto userCredential){

        log.info("In saveUser controller method of username : {}",userCredential.getEmail());
        return ResponseEntity.ok().body(userService.saveUser(userCredential));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String otp){

        log.info("In verify-account controller method of username : {}", email);
        return ResponseEntity.ok().body(userService.verifyUser(email,otp));
    }


    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest){

        log.info("In getToken controller method of username : {}",authRequest.getEmail());
        return ResponseEntity.ok().body(jwtService.generateToken(authRequest));
    }
}
