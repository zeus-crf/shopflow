package br.com.shopflow.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockDecreaseRequest(
        @NotNull @Min(1) Integer quantity
) {
}
