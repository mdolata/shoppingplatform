package org.example.shoppingplatform.service;

import org.example.shoppingplatform.domain.Product;
import org.example.shoppingplatform.domain.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.shoppingplatform.service.ProductsAndDiscountsSharedDefinitions.*;

class DiscountCalculatorServiceTest {
    DiscountConfiguration discountConfiguration = new DiscountConfiguration(config);

    DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(discountConfiguration);

    @ParameterizedTest
    @MethodSource("amountPolicyParameters")
    void shouldReturnProductDefinitionWithCorrectDiscountedPriceForAmountBasedPolicy(int givenAmount, BigDecimal expectedDiscountedPrice) {
        // when
        Product product = discountCalculatorService.calculatePrice(productId1, new BigDecimal(100), givenAmount);

        // then
        assertThat(product).isEqualTo(new Product(productId1, expectedDiscountedPrice, givenAmount));
    }

    private static Stream<Arguments> amountPolicyParameters() {
        return Stream.of(
                Arguments.of(1, new BigDecimal(100)),
                Arguments.of(9, new BigDecimal(100)),
                Arguments.of(10, new BigDecimal(98)),
                Arguments.of(99, new BigDecimal(98)),
                Arguments.of(100, new BigDecimal(95)),
                Arguments.of(101, new BigDecimal(95))
        );
    }

    @ParameterizedTest
    @MethodSource("percentagePolicyParameters")
    void shouldReturnProductDefinitionWithCorrectDiscountedPriceWhenPercentageBasePolicy(int givenAmount, BigDecimal expectedDiscountedPrice) {
        // when
        Product product = discountCalculatorService.calculatePrice(productId2, new BigDecimal(100), givenAmount);

        // then
        assertThat(product).isEqualTo(new Product(productId2, expectedDiscountedPrice, givenAmount));
    }

    private static Stream<Arguments> percentagePolicyParameters() {
        return Stream.of(
                Arguments.of(1, new BigDecimal(100)),
                Arguments.of(9, new BigDecimal(100)),
                Arguments.of(10, new BigDecimal(97)),
                Arguments.of(49, new BigDecimal(97)),
                Arguments.of(50, new BigDecimal(95)),
                Arguments.of(51, new BigDecimal(95))
        );
    }

    @Test
    void shouldReturnTheSameDataWhenProductDiscountIsNotConfigured() {
        // given
        ProductId productId = new ProductId(UUID.randomUUID());

        // when
        Product product = discountCalculatorService.calculatePrice(productId, new BigDecimal(10), 15);

        // then
        assertThat(product).isEqualTo(new Product(productId, new BigDecimal(10), 15));
    }
}