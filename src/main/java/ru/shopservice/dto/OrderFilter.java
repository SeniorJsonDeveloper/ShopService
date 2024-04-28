package ru.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private String name;

    private String category;
}

