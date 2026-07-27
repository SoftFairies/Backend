package com.fairies.api.proyecto.common.domain.service;

public interface EmailService {
    void sendOtpEmail(String to, String otpCode);
}