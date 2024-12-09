# shoppingplatform

This project is very simple implementation of API that receives `productId`, its `price` and `amount` to calculate
the discounted price for the product based on a configured discount policy.

## API Contract

There is only one endpoint:

POST /products/{id}/calculate

RequestBody

```json
{
  "price": "10",
  "amount": 10
}
```

Variables:

- id: Product ID in UUID format (path parameter).
- price: Base price for the product (in String format).
- amount: Number of products for calculation (in Integer format).

Response:

```json
{
  "productId": "UUID",
  "price": "10",
  "amount": 10
}
```

## How to configure the policies

Currently, policies must be configured manually in the Main class. In future iterations, configuration through
external files (e.g., YAML or .properties) will be implemented.

### Example configuration in `Main`

```java
ProductId productId = new ProductId(UUID.fromString("9d78db61-ff28-4884-9097-9b09e13f69b5"));
DiscountDefinition productDiscountDefinition = new DiscountDefinition(
        DiscountPolicy.AMOUNT_BASE_POLICY, // chosen policy
        Map.of(10, new BigDecimal(2),
                100, new BigDecimal(5) // thresholds, for readability should be sorted and split
        )
);

```

## How to run it

1. Using an IDE:
    - Import the Gradle project into IntelliJ IDEA (or the IDE of your choice).
    - Run the main method in the Main class.
2. Using the Command Line:
    - Build the project using Gradle:
   ```
   ./gradlew build
   ```
    - Run the generated JAR file:
   ```
   java -jar build/libs/gradle-base*.jar
   ```

## Assumptions

### Framework:

I chose SparkJava for exposing the REST API because the project is small and straightforward. A heavier framework like
Spring Boot was unnecessary.

### Configuration:

Policies are currently configured in the Main class. While not ideal, this approach makes the structure explicit in
code. In future iterations, I plan to add YAML-based configuration support.

### Unconfigured Products:

If a product is not pre-configured for discounts, the API returns the input data as-is without applying any discounts.

### Validation:

The API assumes that inputs are valid (e.g., correct UUID format, valid price/quantity). Basic validations are handled
via types and value objects, but no advanced validations (e.g., checking for negative prices) are implemented.

### Security:

The API is not secured. Before deploying to production, a security layer (e.g., API keys or OAuth) must be added.

## Future Improvements

- Add external configuration for discount policies using YAML or .properties.
- or add a database for storing product data and policies dynamically.
- Implement advanced validations for input parameters.
- Improve logging and exception handling for edge cases.
- Add a security layer to protect the API endpoints.
- Add controller-level tests to ensure API functionality.
