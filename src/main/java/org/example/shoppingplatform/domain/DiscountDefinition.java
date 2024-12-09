package org.example.shoppingplatform.domain;

import java.math.BigDecimal;
import java.util.Map;

public record DiscountDefinition(DiscountPolicy discountPolicy, Map<Integer, BigDecimal> thresholds) {
    public BigDecimal aboveThreshold(int amount) {
        return thresholds.keySet().stream()
                .sorted()
                .takeWhile(k -> k <= amount)
                .max(Integer::compareTo)
                .map(thresholds::get)
                .orElse(BigDecimal.ZERO);
    }

    public static DiscountDefinition notConfigured() {
        return new DiscountDefinition(DiscountPolicy.NOT_DEFINED, Map.of());
    }

}
