package org.example.shoppingplatform.service;

import org.example.shoppingplatform.domain.DiscountDefinition;
import org.example.shoppingplatform.domain.DiscountPolicy;
import org.example.shoppingplatform.domain.ProductId;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.example.shoppingplatform.domain.DiscountPolicy.AMOUNT_BASE_POLICY;

public class ProductsAndDiscountsSharedDefinitions {
    public final static ProductId productId1 = new ProductId(UUID.randomUUID());
    public final static DiscountDefinition product1DiscountDefinition = new DiscountDefinition(AMOUNT_BASE_POLICY,
            Map.of(10, new BigDecimal(2),
                    100, new BigDecimal(5)));

    public final static ProductId productId2 = new ProductId(UUID.randomUUID());
    public final static DiscountDefinition product2DiscountDefinition = new DiscountDefinition(DiscountPolicy.PERCENTAGE_BASE_POLICY,
            Map.of(10, new BigDecimal(3),
                    50, new BigDecimal(5)));
    public final static Map<ProductId, DiscountDefinition> config = Map.of(
            productId1, product1DiscountDefinition,
            productId2, product2DiscountDefinition
    );
}
