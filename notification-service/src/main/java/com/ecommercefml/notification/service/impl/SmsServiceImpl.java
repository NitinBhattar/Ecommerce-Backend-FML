package com.ecommercefml.notification.service.impl;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    @Value("${twilio.from-number}")
    private String from;


    @Override
    public void sendSms(String to, String message) {
        Message.creator(new PhoneNumber(to), new PhoneNumber(from), message).create();
    }
}
