package com.tradewing.dto;

import lombok.Getter;

@Getter
public class PaymentRequest {
    private Long id;
    private String productName;
    private Long amount;
}
