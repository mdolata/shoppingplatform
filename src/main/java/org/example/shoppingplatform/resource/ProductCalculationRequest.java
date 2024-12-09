package org.example.shoppingplatform.resource;

import java.math.BigDecimal;

public record ProductCalculationRequest (String price, int amount) {
    public BigDecimal getPrice() {
        return new BigDecimal(price);
    }
}
