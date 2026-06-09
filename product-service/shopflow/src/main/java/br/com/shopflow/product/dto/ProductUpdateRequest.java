package br.com.shopflow.product.dto;

import java.math.BigDecimal;

public record ProductUpdateRequest (
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
){
}
