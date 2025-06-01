package com.tradewing.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo{
    private String name;
    private String surname;
    private String email;
    private String image;
}