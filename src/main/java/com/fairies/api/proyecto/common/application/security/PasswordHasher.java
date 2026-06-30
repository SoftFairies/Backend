package com.fairies.api.proyecto.common.application.security;

public interface PasswordHasher {
    String hash(String password);
    boolean check(String plain, String hashed);
}