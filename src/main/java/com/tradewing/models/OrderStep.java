package com.tradewing.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStep {
    private String name;
    private LocalDateTime deadline;

}
