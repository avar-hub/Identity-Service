package com.socials.IdentityService.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    private final String FROM="avarmittal1sep@gmail.com";

    public void sentOtpEmail(String email, String otp){
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification");
            message.setText(otp);
            message.setFrom(FROM);
            javaMailSender.send(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
