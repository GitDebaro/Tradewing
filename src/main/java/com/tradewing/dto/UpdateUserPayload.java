package com.tradewing.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserPayload{
    private String token;
    private String image;
}