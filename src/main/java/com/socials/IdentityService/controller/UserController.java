package com.socials.IdentityService.controller;

import com.socials.IdentityService.dto.AuthRequest;
import com.socials.IdentityService.dto.JWTResponse;
import com.socials.IdentityService.dto.RefreshTokenDto;
import com.socials.IdentityService.dto.UserCredentialDto;
import com.socials.IdentityService.entity.RefreshToken;
import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.service.JWTService;
import com.socials.IdentityService.service.RefreshTokenService;
import com.socials.IdentityService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    private final UserService userService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<UserCredentialDto> saveUser(@RequestBody @Valid UserCredentialDto userCredential){

        log.info("In saveUser controller method of username : {}",userCredential.getEmail());
        return ResponseEntity.ok().body(userService.saveUser(userCredential));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String otp){

        log.info("In verify-account controller method of username : {}", email);
        return ResponseEntity.ok().body(userService.verifyUser(email,otp));
    }


    @GetMapping("/getToken")
    public ResponseEntity<JWTResponse> getToken(@RequestBody AuthRequest authRequest){

        log.info("In getToken controller method of username : {}",authRequest.getEmail());
        RefreshToken refreshToken= refreshTokenService.createRefreshToken(authRequest.getEmail());
        JWTResponse jwtResponse= JWTResponse.builder()
                .accessToken(jwtService.generateToken(authRequest))
                .token(refreshToken.getToken()).build();
         ResponseEntity.ok().body(jwtResponse);
         return ResponseEntity.ok().body(jwtResponse);
    }

    @GetMapping("/forgotPass")
    public ResponseEntity<String> forgotPass(@RequestParam String email){

        log.info("In forgotPass controller method of username : {}", email);
        return ResponseEntity.ok().body(userService.forgotPass(email));
    }

    @PutMapping("/resetPass")
    public ResponseEntity<String> resetPass(@RequestBody  UserCredentialDto userCredentialDto){

        log.info("In resetPass controller method of username : {}", userCredentialDto.getEmail());
        return ResponseEntity.ok().body(userService.resetPass(userCredentialDto));
    }

    @PostMapping("/refreshToken")
    public JWTResponse refreshToken(@RequestBody RefreshTokenDto refreshTokenRequest) {

        log.info("In refreshToken controller method of token  : {}", refreshTokenRequest);

        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiry)
                .map(RefreshToken::getUserCredential)
                .map(userInfo -> {
                    AuthRequest authRequest= AuthRequest.builder()
                            .email(userInfo.getEmail()).password(userInfo.getPassword()).build();
                    JWTResponse jwtResponse = JWTResponse.builder()
                            .accessToken(jwtService.createToken(new HashMap<>() ,userInfo.getEmail()))
                            .token(refreshTokenRequest.getToken()).build();
                    return jwtResponse;
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }
}
