package com.socials.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private UserCredential userCredential;

}
