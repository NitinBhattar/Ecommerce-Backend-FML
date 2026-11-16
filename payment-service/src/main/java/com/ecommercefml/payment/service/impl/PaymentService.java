package com.ecommercefml.payment.service.impl;

import com.ecommercefml.payment.dto.PaymentRequestDto;
import com.ecommercefml.payment.dto.PaymentResponseDto;
import com.ecommercefml.payment.dto.PaymentStatusUpdateRequestDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    PaymentResponseDto getPaymentByOrderId(String orderId);
    PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequestDto paymentStatusUpdateRequestDto);
}
