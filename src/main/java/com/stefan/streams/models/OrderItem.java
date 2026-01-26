package com.stefan.streams.models;

public record OrderItem(
        Product product,
        int quantity
) {}
