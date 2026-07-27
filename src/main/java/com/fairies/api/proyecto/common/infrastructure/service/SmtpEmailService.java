package com.fairies.api.proyecto.common.infrastructure.service;

import com.fairies.api.proyecto.common.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${MAIL_FROM}")
    private String senderEmail;

    @Override
    @Async
    public void sendOtpEmail(String to, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject("Código de verificación 2FA - Lectura Métrica");
        message.setText("Tu código de verificación para iniciar sesión es: " + otpCode +
                "\n\nEste código expirará en 10 minutos.\nSi no intentaste iniciar sesión, ignora y borra este correo.");

        mailSender.send(message);
    }
}