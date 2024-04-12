package com.socials.IdentityService.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    private String email;
    private String password;

}
