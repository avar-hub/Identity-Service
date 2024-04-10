package com.socials.IdentityService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponse {
    private String accessToken;
    private String token;
}
