package org.example.shoppingplatform.service;

import org.example.shoppingplatform.domain.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.example.shoppingplatform.domain.DiscountPolicy.AMOUNT_BASE_POLICY;
import static org.example.shoppingplatform.domain.DiscountPolicy.PERCENTAGE_BASE_POLICY;
import static org.example.shoppingplatform.service.ProductsAndDiscountsSharedDefinitions.*;


class DiscountConfigurationTest {

    DiscountConfiguration discountConfiguration = new DiscountConfiguration(config);

    @Test
    void shouldReturnCorrectDiscountType() {
        // when
        DiscountPolicy discountTypeForProduct1 = discountConfiguration.getDiscountType(productId1);

        // then
        assertThat(discountTypeForProduct1).isEqualTo(AMOUNT_BASE_POLICY);

        // and
        DiscountPolicy discountTypeForProduct12 = discountConfiguration.getDiscountType(productId2);

        // then
        assertThat(discountTypeForProduct12).isEqualTo(PERCENTAGE_BASE_POLICY);
    }

    @ParameterizedTest
    @MethodSource("amountPolicyParameters")
    void shouldReturnCorrectDiscountWhenAmountBasePolicyApplied(int givenAmount, BigDecimal expectedDiscount) {
        // when
        BigDecimal discount = discountConfiguration.getDiscountForAmount(productId1, givenAmount);

        // then
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    private static Stream<Arguments> amountPolicyParameters() {
        return Stream.of(
                Arguments.of(1, new BigDecimal(0)),
                Arguments.of(9, new BigDecimal(0)),
                Arguments.of(10, new BigDecimal(2)),
                Arguments.of(99, new BigDecimal(2)),
                Arguments.of(100, new BigDecimal(5)),
                Arguments.of(101, new BigDecimal(5))
        );
    }

    @ParameterizedTest
    @MethodSource("percentagePolicyParameters")
    void shouldReturnCorrectDiscountWhenPercentageBasePolicyApplied(int givenAmount, BigDecimal expectedDiscount) {
        // when
        BigDecimal discount = discountConfiguration.getDiscountForAmount(productId2, givenAmount);

        // then
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    private static Stream<Arguments> percentagePolicyParameters() {
        return Stream.of(
                Arguments.of(1, new BigDecimal(0)),
                Arguments.of(9, new BigDecimal(0)),
                Arguments.of(10, new BigDecimal(3)),
                Arguments.of(49, new BigDecimal(3)),
                Arguments.of(50, new BigDecimal(5)),
                Arguments.of(51, new BigDecimal(5))
        );
    }
}