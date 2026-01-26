package com.stefan.streams.exercices;

import com.stefan.streams.models.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FirstBatch {

    // Find all electronic products and create a list of their names.
    public List<String> filteringAndCollecting(List<Product> products) {
        return null;
    }

    // Find the first product in the “Sportswear” category that costs less than $20.
    public Product findingElements(List<Product> products) {
        return null;
    }

    // Create a list of order summaries showing order ID and total item count.
    public List<OrderSummary> transformingElementsInAStream(List<Order> orders) {
        return null;
    }

    // Show all products sorted by price (lowest to highest).
    public List<Product> sortingElements(List<Product> products) {
        return null;
    }

    // Implement a simple pagination for products, showing the second page with 3 products per page.
    public List<Product> limitingAnSkipping(List<Product> products, int pageSize, int pageNumber) {
        return null;
    }

    //Create a list of all products that have been ordered.
    public List<Product> fatteningNestedCollections(List<Order> orders) {
        return null;
    }

    // Calculate the total revenue from all orders.
    public double reducingElements(List<Order> orders) {
        return 0;
    }

    // Check if any, all, or none of the products are in the “Electronics” category, and find any product over $1000.
    public Product ShortCircuitingOperations(List<Product> products) {
        return null;
    }

    // Trace the stream pipeline to debug filtering and mapping.
    public void peekForDebugging() {

    }

    // Create a comma-separated list of all product categories.
    public String joiningStrings(List<Product> products) {
        return null;
    }

    // Calculate statistics for product prices.
    public void calculateSummaryStatistics(List<Product> products) {

    }

    // Collect product names into an unmodifiable list (Java 10+).
    public List<String> collectingToUnmodifiableCollection(List<Product> products) {
        return null;
    }

    // Collect all product categories into a Set to remove duplicates.
    public Set<String> collectingToASetDirectly(List<Product> products) {
        return null;
    }

    // Create a map of product names to their prices.
    public Map<String, BigDecimal> collectingToMap(List<Product> products) {
        return null;
    }

    // Use filtering and mapping as part of a collector.
    public Map<String, List<String>> filteringAndMappingInCollectors(List<Product> products) {
        return null;
    }

    // Safely collect products by price, merging names if prices are the same.
    public Map<BigDecimal, String> handlingDuplicateKeysIntoMap(List<Product> products) {
        return null;
    }

    // Count products in each category and collect their names.

    public Map<String, Long> countProductsByCategory(List<Product> products) {
        return null;
    }

    public Map<String, List<String>> collectProductNamesByCategory(List<Product> products) {
        return null;
    }

    // Divide products into “expensive” (>$100) and “affordable” categories.

    public Map<Boolean, List<Product>> partitioningData(List<Product> products) {
        return null;
    }

    // Group all products by their category.

    public Map<String, List<Product>> groupingData(List<Product> products) {
        return null;
    }

    // Get the average price of products by category.

    public Map<String, Double> chainingCollectors(List<Product> products) {
        return null;
    }

    // Analyze orders by customer tier and order status, showing order count and total items.
    // TODO De adaugat test
    public Map<String, Map<String, OrderStats>> usingCollectingAndThen(List<Order> orders) {
        return null;
    }



}
