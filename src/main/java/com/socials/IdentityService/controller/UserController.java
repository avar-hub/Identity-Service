package com.socials.IdentityService.controller;

import com.socials.IdentityService.dto.AuthRequest;
import com.socials.IdentityService.entity.UserCredential;
import com.socials.IdentityService.service.JWTService;
import com.socials.IdentityService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    private final UserService userService;

    private final JWTService jwtService;


    @GetMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserCredential userCredential){

        return ResponseEntity.ok().body(userService.saveUser(userCredential));
    }

    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest){

        return ResponseEntity.ok().body(jwtService.generateToken(authRequest.getEmail()));
    }
}
