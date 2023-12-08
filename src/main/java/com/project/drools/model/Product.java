package com.project.drools.model;

import lombok.Data;

@Data
public class Product {
    private String name;
    private double price;
    private double discountedPrice;
}
