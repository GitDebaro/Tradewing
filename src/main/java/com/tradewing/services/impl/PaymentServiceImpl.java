package com.tradewing.services.impl;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tradewing.dto.PaymentRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.repos.ProductRepo;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.tradewing.services.PaymentService;


import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

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

    @Override
    public Map<String, String> createCheckoutSession(PaymentRequest request) throws Exception {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        lineItems.add(
            SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
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
            .setSuccessUrl(frontendUrl + "/success/" + request.getId())
            .setCancelUrl(frontendUrl + "/cancel")
            .addAllLineItem(lineItems)
            .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());
        return response;
    }

    @Override
    public void sendConfirmation(String id, String email) {
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
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
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
}