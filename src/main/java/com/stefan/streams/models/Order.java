package com.stefan.streams.models;

import java.time.LocalDate;
import java.util.List;

public record Order(
        String id,
        Customer customer,
        LocalDate orderDate,
        List<OrderItem> items,
        String status  // "placed", "shipped", "delivered", or "canceled"
) {}