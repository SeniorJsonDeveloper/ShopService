package ru.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    private String name;

    private String category;

    private BigDecimal price;

    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    private Integer userId;
}
