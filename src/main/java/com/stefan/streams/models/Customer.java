package com.stefan.streams.models;

import java.time.LocalDate;

public record Customer(
        String id,
        String name,
        String email,
        LocalDate registrationDate,
        String tier  // "standard", "premium", or "elite"
) {}
