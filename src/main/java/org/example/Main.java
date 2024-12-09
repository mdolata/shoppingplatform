package org.example;

import org.example.shoppingplatform.domain.DiscountDefinition;
import org.example.shoppingplatform.domain.DiscountPolicy;
import org.example.shoppingplatform.domain.ProductId;
import org.example.shoppingplatform.resource.ProductController;
import org.example.shoppingplatform.service.DiscountCalculatorService;
import org.example.shoppingplatform.service.DiscountConfiguration;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        var config = getConfiguration();
        DiscountConfiguration discountConfiguration = new DiscountConfiguration(config);
        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(discountConfiguration);

        new ProductController(discountCalculatorService);
    }

    private static Map<ProductId, DiscountDefinition> getConfiguration() {
        ProductId productId1 = new ProductId(UUID.fromString("9d78db61-ff28-4884-9097-9b09e13f69b5"));
        DiscountDefinition product1DiscountDefinition = new DiscountDefinition(DiscountPolicy.AMOUNT_BASE_POLICY,
                Map.of(10, new BigDecimal(2),
                        100, new BigDecimal(5)));

        ProductId productId2 = new ProductId(UUID.fromString("2799559e-753d-4a12-a3d7-081335d60f77"));
        DiscountDefinition product2DiscountDefinition = new DiscountDefinition(DiscountPolicy.PERCENTAGE_BASE_POLICY,
                Map.of(10, new BigDecimal(3),
                        50, new BigDecimal(5)));

        return Map.of(productId1, product1DiscountDefinition,
                productId2, product2DiscountDefinition);
    }
}