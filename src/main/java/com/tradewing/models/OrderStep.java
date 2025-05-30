package com.tradewing.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
public class OrderStep {
    private String name;
    private LocalDateTime deadline;

}
