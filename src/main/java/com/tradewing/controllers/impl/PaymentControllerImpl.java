package com.tradewing.controllers.impl;

import com.tradewing.dto.PaymentRequest;
import com.tradewing.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tradewing.controllers.PaymentController;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController{
    private final PaymentService paymentService;

    @Override
    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest request) {
        try {
            return ResponseEntity.ok(paymentService.createCheckoutSession(request));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    @GetMapping("/sendConfirmation")
    public ResponseEntity<Void> sendConfirmation(@RequestParam String id, @RequestParam String email) {
        paymentService.sendConfirmation(id, email);
        return ResponseEntity.ok().build();
    }
}
