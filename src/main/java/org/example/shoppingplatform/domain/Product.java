package org.example.shoppingplatform.domain;

import java.math.BigDecimal;

public record Product(ProductId productId, BigDecimal price, int amount) {
}
