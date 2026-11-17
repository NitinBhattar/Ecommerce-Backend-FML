package com.ecommercefml.notification.event;

import com.ecommercefml.notification.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String email;
    private String phone;
    private MessageType type;
    private String subject;
    private String message;
}
