package org.example.shoppingplatform.resource;

import org.example.shoppingplatform.domain.Product;

public record ProductCalculationResponse(String productId, String price, int amount) {
    public static ProductCalculationResponse fromProduct(Product product) {
        return new ProductCalculationResponse(product.productId().value().toString(),
                product.price().toString(),
                product.amount());
    }
}
