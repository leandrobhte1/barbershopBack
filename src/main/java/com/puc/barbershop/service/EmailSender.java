package com.puc.barbershop.service;

public interface EmailSender {
    void send(String to, String subject, String email);
}
