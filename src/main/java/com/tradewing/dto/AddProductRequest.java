package com.tradewing.dto;

import lombok.Getter;

@Getter
public class AddProductRequest {
    private String name;
    private String price;
    private String description;
    private String image;
}
