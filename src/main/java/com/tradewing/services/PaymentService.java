package com.tradewing.services;

import com.tradewing.dto.PaymentRequest;
import java.util.Map;

public interface PaymentService {
    Map<String, String> createCheckoutSession(PaymentRequest request) throws Exception;
    void sendConfirmation(String id, String email);
}