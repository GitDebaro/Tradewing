package com.tradewing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {
    private String name;
    private String capital;
    private int population;
}
