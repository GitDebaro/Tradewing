package com.tradewing.controllers;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tradewing.models.ProductEntity;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import com.tradewing.repos.ProductRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final JavaMailSender mailSender;
    private final ProductRepo productRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${payment.sk}")
    private String skKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = skKey;
    }

    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody PaymentRequest request) {
        try {
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

            // Suponiendo que te mandan nombre y precio del producto
            lineItems.add(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(request.getAmount())
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(request.getProductName())
                                    .build()
                            )
                            .build()
                    )
                    .build()
            );

            SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendUrl +"/success/"+ request.getId())
                .setCancelUrl(frontendUrl +"/cancel")
                .addAllLineItem(lineItems)
                .build();

            Session session = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("id", session.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/sendConfirmation")
    public ResponseEntity<Void> sendConfirmation(@RequestParam String id, @RequestParam String email) {
        Long productId = Long.parseLong(id);
        
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String subject = "Details of your recently purchase on Tradewing: " + product.getName();
        String content = buildHtmlEmail(product);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
        
        return ResponseEntity.ok().build();
    }

    private String buildHtmlEmail(ProductEntity product) {
        return """
            <div style="font-family: Arial, sans-serif;">
              <h2>Details of your recently purchase on Tradewing</h2>
              <p><strong>Name:</strong> %s</p>
              <p><strong>Price:</strong> €%d</p>
              <p><strong>Description:</strong> %s</p>
              <img src="%s" alt="Product image" style="max-width: 300px; border-radius: 10px; margin-top: 10px;">
            </div>
            """.formatted(
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getImage() != null ? product.getImage() : ""
            );
    }

    static class PaymentRequest {
        private Long id;
        private String productName;
        private Long amount;

        // Getters y setters
        public Long getId(){
            return id;
        }
        public String getProductName(){
            return productName;
        } 
        public Long getAmount(){
            return amount;
        }
        public void setId(Long newId){
            id = newId;
        }
        public void setProductName(String newProductName){
            productName = newProductName;
        }
        public void setAmount(Long newAmount){
            amount = newAmount;
        }
    }
}
