package com.stefan.streams.models;

import java.math.BigDecimal;

public record Product(
        String id,
        String name,
        String category,
        BigDecimal price
) {}
