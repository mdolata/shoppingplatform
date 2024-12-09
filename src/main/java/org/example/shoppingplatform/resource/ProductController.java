package org.example.shoppingplatform.resource;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.shoppingplatform.domain.ProductId;
import org.example.shoppingplatform.service.DiscountCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static spark.Spark.*;

@Slf4j
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final DiscountCalculatorService discountCalculatorService;

    public ProductController(DiscountCalculatorService discountCalculatorService) {
        this.discountCalculatorService = discountCalculatorService;

        paths();
    }

    private void paths() {
        post("products/:id/calculate", (req, res) -> {
            ProductCalculationRequest request = new Gson().fromJson(req.body(), ProductCalculationRequest.class);
            var productId = new ProductId(UUID.fromString(req.params(":id")));
            log.info("Received price calculation request with productId = {}, request = {}", productId, request);
            return new Gson().toJson(ProductCalculationResponse.fromProduct(
                    discountCalculatorService.calculatePrice(productId, request.getPrice(), request.amount())
            ));
        });
    }
}
