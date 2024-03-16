package com.socials.IdentityService.controller;

import com.socials.IdentityService.dto.AuthRequest;
import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.service.JWTService;
import com.socials.IdentityService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {


    private final UserService userService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserCredential userCredential){

        return ResponseEntity.ok().body(userService.saveUser(userCredential));
    }

    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest){

        return ResponseEntity.ok().body(jwtService.generateToken(authRequest));
    }
}
