package br.com.shopflow.product.dto;

import br.com.shopflow.product.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDateTime createdAt
) {
    public static ProductResponse  from (Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPreco(),
                product.getStockQuantity(),
                product.getCreatedAt()
        );
    }

}
