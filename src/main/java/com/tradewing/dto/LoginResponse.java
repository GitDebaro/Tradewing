package com.tradewing.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    private String token;

    public String getToken() {return this.token;}
}
