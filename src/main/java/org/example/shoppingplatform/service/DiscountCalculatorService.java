package org.example.shoppingplatform.service;

import org.example.shoppingplatform.domain.Product;
import org.example.shoppingplatform.domain.ProductId;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCalculatorService {

    private final DiscountConfiguration discountConfiguration;

    public DiscountCalculatorService(DiscountConfiguration discountConfiguration) {
        this.discountConfiguration = discountConfiguration;
    }

    public Product calculatePrice(ProductId productId, BigDecimal price, int amount) {
        return switch (discountConfiguration.getDiscountType(productId)) {
            case AMOUNT_BASE_POLICY -> calculatePriceWithAmountDiscount(productId, price, amount);
            case PERCENTAGE_BASE_POLICY -> calculatePriceWithPercentageDiscount(productId, price, amount);
            case NOT_DEFINED -> new Product(productId, price, amount);
        };
    }

    private Product calculatePriceWithPercentageDiscount(ProductId productId, BigDecimal price, int amount) {
        var calculatedDiscount = price.multiply(discountConfiguration.getDiscountForAmount(productId, amount))
                .divide(new BigDecimal(100), RoundingMode.DOWN);
        var discountedPrice = price.subtract(calculatedDiscount);

        return new Product(productId, discountedPrice, amount);
    }

    private Product calculatePriceWithAmountDiscount(ProductId productId, BigDecimal price, int amount) {
        var discountedPrice = price.subtract(discountConfiguration.getDiscountForAmount(productId, amount));

        return new Product(productId, discountedPrice, amount);
    }
}
