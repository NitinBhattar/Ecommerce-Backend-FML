package com.ecommercefml.payment.mapper;

import com.ecommercefml.payment.dto.PaymentRequestDto;
import com.ecommercefml.payment.dto.PaymentResponseDto;
import com.ecommercefml.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    public Payment toEntity(PaymentRequestDto paymentRequestDto);

    @Mapping(source = "id", target = "paymentId")
    public PaymentResponseDto toDto(Payment payment);
}
