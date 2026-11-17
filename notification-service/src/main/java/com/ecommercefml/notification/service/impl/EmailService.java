package com.ecommercefml.notification.service.impl;

public interface EmailService {
    public void sendMail(String to, String subject, String body);
}
