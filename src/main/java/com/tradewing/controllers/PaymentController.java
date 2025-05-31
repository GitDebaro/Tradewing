package com.tradewing.controllers;

import com.tradewing.dto.PaymentRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentController {
    ResponseEntity<Map<String, String>> createCheckoutSession(PaymentRequest request);
    ResponseEntity<Void> sendConfirmation(String id, String email);
}