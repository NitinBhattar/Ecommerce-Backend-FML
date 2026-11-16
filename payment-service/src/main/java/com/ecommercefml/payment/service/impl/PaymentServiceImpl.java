package com.ecommercefml.payment.service.impl;

import com.ecommercefml.payment.dto.GatewayOrderResponseDto;
import com.ecommercefml.payment.dto.PaymentRequestDto;
import com.ecommercefml.payment.dto.PaymentResponseDto;
import com.ecommercefml.payment.dto.PaymentStatusUpdateRequestDto;
import com.ecommercefml.payment.entity.Payment;
import com.ecommercefml.payment.enums.PaymentStatus;
import com.ecommercefml.payment.exception.PaymentNotFoundException;
import com.ecommercefml.payment.mapper.PaymentMapper;
import com.ecommercefml.payment.repository.PaymentRepository;
import com.ecommercefml.payment.util.TransactionGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentGatewayService paymentGatewayService;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        // Mapper
        Payment payment = paymentMapper.toEntity(paymentRequestDto);
        payment.setTransactionId(TransactionGenerator.generate());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        // Create order
        GatewayOrderResponseDto gatewayOrderResponseDto = paymentGatewayService.createOrder(payment.getOrderId(), payment.getTransactionId(), payment.getAmount());

        // Update in gatewayOrderId in repository
        payment.setGatewayOrderId(gatewayOrderResponseDto.getGatewayOrderId());

        // Saving in repo
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(String orderId) {
        // Check for payment in repo
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException("Payment for order not found"));

        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequestDto paymentStatusUpdateRequestDto) {
        // Check for payment in repo
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment for order not found"));

        // Changing status
        payment.setPaymentStatus(paymentStatusUpdateRequestDto.getPaymentStatus());
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }
}
