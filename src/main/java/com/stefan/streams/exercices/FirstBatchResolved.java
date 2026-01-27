package com.stefan.streams.exercices;

import com.stefan.streams.models.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FirstBatchResolved {

    // Filtering and Collecting
    // Scenario: Find all electronic products and create a list of their names.
    public List<String> getElectronicsProductNames(List<Product> products) {
        List<String> electronicProductNames = products.stream()
                .filter(product -> product.category().equals("Electronics"))
                .map(Product::name)
                .collect(Collectors.toList());

        System.out.println(electronicProductNames);
        // Output: [iPhone 14, MacBook Pro, Wireless Earbuds, Smart Watch]

        return electronicProductNames;
    }

    // Finding Elements
    // Scenario: Find the first product in the "Sportswear" category that costs less than $20.
    public Product findAffordableSportswearProduct(List<Product> products) {
        Optional<Product> affordableSportswearProduct = products.stream()
                .filter(product -> product.category().equals("Sportswear"))
                .filter(product -> product.price().compareTo(new BigDecimal("20")) < 0)
                .findFirst();

        affordableSportswearProduct.ifPresent(product ->
                System.out.println("Affordable sportswear: " + product.name() + " - $" + product.price()));
        // Output: Affordable sportswear: Water Bottle - $12.99

        return affordableSportswearProduct.orElse(null);
    }

    // Transforming Elements in a Stream
    // Scenario: Create a list of order summaries showing order ID and total item count.
    public List<OrderSummary> mapOrdersToSummaries(List<Order> orders) {
        List<OrderSummary> orderSummaries = orders.stream()
                .map(order -> new OrderSummary(
                        order.id(),
                        order.items().stream().mapToInt(OrderItem::quantity).sum()
                ))
                .collect(Collectors.toList());

        orderSummaries.forEach(System.out::println);
        // Output:
        // OrderSummary[orderId=O1, itemCount=2]
        // OrderSummary[orderId=O2, itemCount=1]
        // OrderSummary[orderId=O3, itemCount=3]
        // ... and so on

        return orderSummaries;
    }

    // Sorting Elements
    // Scenario: Show all products sorted by price (lowest to highest).
    public List<Product> sortProductsByPrice(List<Product> products) {
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparing(Product::price))
                .collect(Collectors.toList());

        sortedProducts.forEach(product ->
                System.out.println(product.name() + " - $" + product.price()));
        // Output:
        // Water Bottle - $12.99
        // Yoga Mat - $25.99
        // Desk Lamp - $34.99
        // ... (remaining products in ascending price order)

        return sortedProducts;
    }

    // Limiting and Skipping
    // Scenario: Implement a simple pagination for products, showing the second page with 3 products per page.
    public List<Product> getProductsPage(List<Product> products, int pageSize, int pageNumber) {
        List<Product> paginatedProducts = products.stream()
                .skip(pageSize * pageNumber)
                .limit(pageSize)
                .collect(Collectors.toList());

        paginatedProducts.forEach(product -> System.out.println(product.name()));
        // Output:
        // Running Shoes
        // Yoga Mat
        // Water Bottle

        return paginatedProducts;
    }

    // Flattening Nested Collections
    // Scenario: Create a list of all products that have been ordered.
    public List<Product> extractAllOrderedProducts(List<Order> orders) {
        List<Product> orderedProducts = orders.stream()
                .flatMap(order -> order.items().stream())
                .map(OrderItem::product)
                .distinct()
                .collect(Collectors.toList());

        orderedProducts.forEach(product -> System.out.println(product.name()));
        // Output will show each product that appears in at least one order, without duplicates

        return orderedProducts;
    }

    // Reducing Elements
    // Scenario: Calculate the total revenue from all orders.
    public double calculateTotalRevenue(List<Order> orders) {
        BigDecimal totalRevenue = orders.stream()
                .flatMap(order -> order.items().stream())
                .map(item -> item.product().price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total Revenue: $" + totalRevenue);
        // Output: Total Revenue: $7267.87

        return totalRevenue.doubleValue();
    }

    // Short-circuiting Operations
    // Scenario: Check if any, all, or none of the products are in the "Electronics" category, and find any product over $1000.
    public Product findExpensiveElectronicsProduct(List<Product> products) {
        boolean anyElectronics = products.stream()
                .anyMatch(product -> product.category().equals("Electronics"));

        boolean allExpensive = products.stream()
                .allMatch(product -> product.price().compareTo(new BigDecimal("100")) > 0);

        boolean noneHome = products.stream()
                .noneMatch(product -> product.category().equals("Toys"));

        Optional<Product> anyHighPriced = products.stream()
                .filter(product -> product.price().compareTo(new BigDecimal("1000")) > 0)
                .findAny();

        System.out.println("Any electronics? " + anyElectronics);
        System.out.println("All products expensive? " + allExpensive);
        System.out.println("No toys? " + noneHome);
        anyHighPriced.ifPresent(p -> System.out.println("A high-priced product: " + p.name()));
        // Output:
        // Any electronics? true
        // All products expensive? false
        // No toys? true
        // A high-priced product: MacBook Pro

        return anyHighPriced.orElse(null);
    }

    // Using peek() for Debugging
    // Scenario: Trace the stream pipeline to debug filtering and mapping.
    public void demonstratePeekOperation() {
        List<Product> products = List.of(
                new Product("P1", "iPhone 14", "Electronics", new BigDecimal("999.99")),
                new Product("P2", "MacBook Pro", "Electronics", new BigDecimal("1999.99")),
                new Product("P3", "Coffee Maker", "Appliances", new BigDecimal("89.99"))
        );

        List<String> debuggedNames = products.stream()
                .peek(p -> System.out.println("Before filter: " + p.name()))
                .filter(product -> product.category().equals("Electronics"))
                .peek(p -> System.out.println("After filter: " + p.name()))
                .map(Product::name)
                .peek(name -> System.out.println("Mapped name: " + name))
                .collect(Collectors.toList());
    }

    // Joining Strings
    // Scenario: Create a comma-separated list of all product categories.
    public String joinProductCategories(List<Product> products) {
        String categories = products.stream()
                .map(Product::category)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));

        System.out.println("Available categories: " + categories);
        // Output: Available categories: Appliances, Electronics, Home, Sportswear

        return categories;
    }

    // Calculating Summary Statistics
    // Scenario: Calculate statistics for product prices.
    public void calculateSummaryStatistics(List<Product> products) {
        DoubleSummaryStatistics priceStatistics = products.stream()
                .map(product -> product.price().doubleValue())
                .collect(Collectors.summarizingDouble(price -> price));

        System.out.println("Product price statistics:");
        System.out.println("Count: " + priceStatistics.getCount());
        System.out.println("Average: $" + String.format("%.2f", priceStatistics.getAverage()));
        System.out.println("Min: $" + String.format("%.2f", priceStatistics.getMin()));
        System.out.println("Max: $" + String.format("%.2f", priceStatistics.getMax()));
        System.out.println("Sum: $" + String.format("%.2f", priceStatistics.getSum()));
        // Output:
        // Product price statistics:
        // Count: 10
        // Average: $388.39
        // Min: $12.99
        // Max: $1999.99
        // Sum: $3883.89
    }

    // Collecting to Unmodifiable Collections
    // Scenario: Collect product names into an unmodifiable list (Java 10+).
    public List<String> collectProductNamesToUnmodifiableList(List<Product> products) {
        List<String> unmodifiableNames = products.stream()
                .map(Product::name)
                .collect(Collectors.toUnmodifiableList());

        // unmodifiableNames.add("New Product"); // Throws UnsupportedOperationException

        return unmodifiableNames;
    }

    // Collecting to Set Directly
    // Scenario: Collect all product categories into a Set to remove duplicates.
    public Set<String> collectCategoriesToSet(List<Product> products) {
        Set<String> categorySet = products.stream()
                .map(Product::category)
                .collect(Collectors.toSet());

        System.out.println(categorySet);

        return categorySet;
    }

    // Collecting to Map
    // Scenario: Create a map of product names to their prices.
    public Map<String, BigDecimal> mapProductNamesToPrices(List<Product> products) {
        Map<String, BigDecimal> productPriceMap = products.stream()
                .collect(Collectors.toMap(
                        Product::name,
                        Product::price
                ));

        System.out.println(productPriceMap);
        // Output: {iPhone 14=999.99, MacBook Pro=1999.99, Coffee Maker=89.99, ...}

        return productPriceMap;
    }

    // Filtering and Mapping in Collectors
    // Scenario: Use filtering and mapping as part of a collector.
    public Map<String, List<String>> groupExpensiveProductNamesByCategory(List<Product> products) {
        Map<String, List<String>> electronicsNamesByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.filtering(
                                p -> p.category().equals("Electronics"),
                                Collectors.mapping(Product::name, Collectors.toList())
                        )
                ));

        System.out.println(electronicsNamesByCategory);

        return electronicsNamesByCategory;
    }

    // Handling Duplicate Keys in toMap()
    // Scenario: Safely collect products by price, merging names if prices are the same.
    public Map<BigDecimal, String> mapPricesToMergedProductNames(List<Product> products) {
        Map<BigDecimal, String> priceToNames = products.stream()
                .collect(Collectors.toMap(
                        Product::price,
                        Product::name,
                        (name1, name2) -> name1 + ", " + name2
                ));

        System.out.println(priceToNames);

        return priceToNames;
    }

    // Downstream Collectors with groupingBy
    // Scenario: Count products in each category and collect their names.
    public Map<String, Long> countProductsByCategory(List<Product> products) {
        Map<String, Long> countByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));

        return countByCategory;
    }

    public Map<String, List<String>> collectProductNamesByCategory(List<Product> products) {
        Map<String, List<String>> namesByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.mapping(Product::name, Collectors.toList())));

        return namesByCategory;
    }

    // Partitioning Data
    // Scenario: Divide products into "expensive" (>$100) and "affordable" categories.
    public Map<Boolean, List<Product>> partitionProductsByPrice(List<Product> products) {
        Map<Boolean, List<Product>> pricePartition = products.stream()
                .collect(Collectors.partitioningBy(
                        product -> product.price().compareTo(new BigDecimal("100")) > 0
                ));

        System.out.println("Expensive products:");
        pricePartition.get(true).forEach(p -> System.out.println("- " + p.name() + " ($" + p.price() + ")"));
        System.out.println("\nAffordable products:");
        pricePartition.get(false).forEach(p -> System.out.println("- " + p.name() + " ($" + p.price() + ")"));

        return pricePartition;
    }

    // Grouping Data
    // Scenario: Group all products by their category.
    public Map<String, List<Product>> groupProductsByCategory(List<Product> products) {
        Map<String, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category));

        productsByCategory.forEach((category, prods) -> {
            System.out.println(category + ":");
            prods.forEach(p -> System.out.println("  - " + p.name()));
        });

        return productsByCategory;
    }

    // Chaining Collectors
    // Scenario: Get the average price of products by category.
    public Map<String, Double> calculateAveragePriceByCategory(List<Product> products) {
        Map<String, Double> avgPriceByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.averagingDouble(p -> p.price().doubleValue())
                ));

        avgPriceByCategory.forEach((category, avgPrice) ->
                System.out.println(category + ": $" + String.format("%.2f", avgPrice)));
        // Output:
        // Electronics: $877.49
        // Appliances: $84.99
        // Sportswear: $56.32
        // Home: $34.99

        return avgPriceByCategory;
    }

    // Using collectingAndThen to transform the result after collection
    // Scenario: Analyze orders by customer tier and order status, showing order count and total items.
    public Map<String, Map<String, OrderStats>> analyzeOrdersByCustomerTierAndStatus(List<Order> orders) {
        Map<String, Map<String, OrderStats>> orderAnalysisByTierAndStatus = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.customer().tier(),
                        Collectors.groupingBy(
                                Order::status,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        ordersList -> new OrderStats(
                                                ordersList.size(),
                                                ordersList.stream()
                                                        .flatMap(order -> order.items().stream())
                                                        .mapToInt(OrderItem::quantity)
                                                        .sum()
                                        )
                                )
                        )
                ));

        orderAnalysisByTierAndStatus.forEach((tier, statusMap) -> {
            System.out.println("Customer Tier: " + tier);
            statusMap.forEach((status, stats) -> {
                System.out.println("  Status: " + status);
                System.out.println("    Order Count: " + stats.orderCount());
                System.out.println("    Total Items: " + stats.totalItems());
            });
        });

        return orderAnalysisByTierAndStatus;
    }
}
