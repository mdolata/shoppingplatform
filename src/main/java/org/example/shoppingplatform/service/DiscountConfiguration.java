package org.example.shoppingplatform.service;

import org.example.shoppingplatform.domain.DiscountDefinition;
import org.example.shoppingplatform.domain.DiscountPolicy;
import org.example.shoppingplatform.domain.ProductId;

import java.math.BigDecimal;
import java.util.Map;

public class DiscountConfiguration {

    private final Map<ProductId, DiscountDefinition> config;

    public DiscountConfiguration(Map<ProductId, DiscountDefinition> config) {
        this.config = config;
    }

    public DiscountPolicy getDiscountType(ProductId productId) {
        return config.getOrDefault(productId, DiscountDefinition.notConfigured()).discountPolicy();
    }

    public BigDecimal getDiscountForAmount(ProductId productId, int amount) {
        return config.get(productId).aboveThreshold(amount);
    }
}
