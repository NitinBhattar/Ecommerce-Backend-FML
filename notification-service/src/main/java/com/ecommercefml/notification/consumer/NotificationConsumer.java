package com.ecommercefml.notification.consumer;

import com.ecommercefml.notification.enums.MessageType;
import com.ecommercefml.notification.event.NotificationEvent;
import com.ecommercefml.notification.service.impl.EmailService;
import com.ecommercefml.notification.service.impl.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final EmailService emailService;
    private final SmsService smsService;

    @KafkaListener(topics = "notification-event", groupId = "notification-group")
    public void consume(NotificationEvent notificationEvent) {
        // For email
        if (notificationEvent.getType().equals(MessageType.EMAIL)) {
            emailService.sendMail(notificationEvent.getEmail(), notificationEvent.getSubject(), notificationEvent.getMessage());
        }

        // For message
        if (notificationEvent.getType().equals(MessageType.SMS)) {
            smsService.sendSms(notificationEvent.getPhone(), notificationEvent.getMessage());
        }
    }
}
