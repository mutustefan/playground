package com.stefan.streams;


import com.stefan.streams.exercices.FirstBatch;
import com.stefan.streams.models.Customer;
import com.stefan.streams.models.Order;
import com.stefan.streams.models.OrderItem;
import com.stefan.streams.models.OrderStats;
import com.stefan.streams.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirstBatchTest {

    private final FirstBatch firstBatch = new FirstBatch();

    @Test
    void shouldFilterProductsAndCollectStrings() {

        List<Product> products = getAllProducts();
        List<String> result = firstBatch.filteringAndCollecting(products);

        List<String> response = List.of("iPhone 14", "MacBook Pro", "Wireless Earbuds", "Smart Watch");
        Assertions.assertEquals(response, result);
    }

    @Test
    void shouldFindFirstSportswearProductUnder20Dollars() {

        List<Product> products = getAllProducts();
        Product result = firstBatch.findingElements(products);

        Product expectedProduct = new Product("P6", "Water Bottle", "Sportswear", new BigDecimal("12.99"));
        Assertions.assertEquals(expectedProduct, result);
    }

    @Test
    void shouldTransformOrdersToOrderSummaries() {

        List<Order> orders = getAllOrders();
        List<?> result = firstBatch.transformingElementsInAStream(orders);

        Assertions.assertEquals(8, result.size());
        // Further assertions can be added here to validate the content of the order summaries
    }

    @Test
    void shouldSortProductsByPrice() {

        List<Product> products = getAllProducts();
        List<Product> result = firstBatch.sortingElements(products);

        Assertions.assertEquals(10, result.size());
        Assertions.assertEquals("Water Bottle", result.get(0).name());
        Assertions.assertEquals("MacBook Pro", result.get(9).name());
    }

    @Test
    void shouldLimitAndSkipProductsForPagination() {

        int pageSize = 3;
        int pageNumber = 1; // 0-based index, so this is the second page
        List<Product> products = getAllProducts();

        List<Product> result = firstBatch.limitingAnSkipping(products, pageSize, pageNumber);

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Running Shoes", result.get(0).name());
        Assertions.assertEquals("Yoga Mat", result.get(1).name());
        Assertions.assertEquals("Water Bottle", result.get(2).name());
    }

    @Test
    void shouldFlattenNestedCollectionsOfOrderedProducts() {

        List<Order> orders = getAllOrders();
        List<Product> result = firstBatch.fatteningNestedCollections(orders);

        Assertions.assertEquals(10, result.size());
        // Further assertions can be added here to validate the content of the products list
    }

    @Test
    void shouldReduceOrdersToTotalRevenue() {

        List<Order> orders = getAllOrders();
        double result = firstBatch.reducingElements(orders);

        double expectedTotalRevenue = 999.99 + 349.99 + 1999.99 + 89.99 + 34.99 * 2 + 129.99 + 25.99 + 12.99 * 2 + 159.99 + 79.99 + 34.99 + 999.99 + 1999.99 + 349.99;
        Assertions.assertEquals(expectedTotalRevenue, result, 0.01);
    }

    @Test
    void shouldShortCircuitOperationsOnProducts() {

        List<Product> products = getAllProducts();
        Product result = firstBatch.ShortCircuitingOperations(products);

        Product expectedProduct = new Product("P2", "MacBook Pro", "Electronics", new BigDecimal("1999.99"));
        Assertions.assertEquals(expectedProduct, result);
    }

    @Test
    void shouldJoinProductCategoriesIntoString() {

        List<Product> products = getAllProducts();
        String result = firstBatch.joiningStrings(products);

        List<String> expectedCategoryNames = List.of("Electronics", "Appliances", "Sportswear", "Home");
        expectedCategoryNames.forEach(c ->
                Assertions.assertTrue(result.contains(c)));
    }

    @Test
    void shouldCollectProductNamesToUnmodifiableList() {

        List<Product> products = getAllProducts();
        List<String> result = firstBatch.collectingToUnmodifiableCollection(products);

        List<String> expectedNames = products.stream().map(Product::name).toList();
        Assertions.assertEquals(expectedNames, result);

        // Attempting to modify the list should throw an UnsupportedOperationException
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result.add("New Product"));
    }

    @Test
    void shouldCollectProductCategoriesToSet() {
        List<Product> products = getAllProducts();
        Set<String> result = firstBatch.collectingToASetDirectly(products);

        // The expected unique categories from getAllProducts()
        List<String> expectedCategories = List.of("Electronics", "Appliances", "Sportswear", "Home");

        Assertions.assertEquals(expectedCategories.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedCategories));
    }

    @Test
    void shouldCollectProductNamesToMap() {
        List<Product> products = getAllProducts();
        var result = firstBatch.collectingToMap(products);

        Assertions.assertEquals(products.size(), result.size());
        for (Product product : products) {
            Assertions.assertEquals(product.price(), result.get(product.name()));
        }
    }

    @Test
    void shouldCalculateSummaryStatisticsForProductPrices() {
        List<Product> products = getAllProducts();
        firstBatch.calculateSummaryStatistics(products);
        // Since the method does not return anything, we assume it prints the statistics.
        // Manual verification of printed output may be necessary.
    }

    @Test
    void shouldGroupProductNamesByCategoryWithPriceOver100() {
        List<Product> products = getAllProducts();
        Map<String, List<String>> result = firstBatch.filteringAndMappingInCollectors(products);

        Map<String, List<String>> expected = Map.of(
                "Electronics", List.of("iPhone 14", "MacBook Pro", "Wireless Earbuds", "Smart Watch"),
                "Appliances", List.of(),
                "Sportswear", List.of(),
                "Home", List.of()
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldHandleDuplicateKeysWhenCollectingToMap() {
        List<Product> products = getAllProducts();
        Map<BigDecimal, String> result = firstBatch.handlingDuplicateKeysIntoMap(products);

        // Since there are no duplicate prices in the provided products, the size should match
        Assertions.assertEquals(products.size(), result.size());
        for (Product product : products) {
            Assertions.assertTrue(result.get(product.price()).contains(product.name()));
        }
    }

    @Test
    void shouldCountProductsByCategory() {
        List<Product> products = getAllProducts();
        Map<String, Long> result = firstBatch.countProductsByCategory(products);

        Map<String, Long> expected = Map.of(
                "Electronics", 4L,
                "Appliances", 2L,
                "Sportswear", 3L,
                "Home", 1L
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldCollectProductNamesByCategory() {
        List<Product> products = getAllProducts();
        Map<String, List<String>> result = firstBatch.collectProductNamesByCategory(products);

        Map<String, List<String>> expected = Map.of(
                "Electronics", List.of("iPhone 14", "MacBook Pro", "Wireless Earbuds", "Smart Watch"),
                "Appliances", List.of("Coffee Maker", "Blender"),
                "Sportswear", List.of("Running Shoes", "Yoga Mat", "Water Bottle"),
                "Home", List.of("Desk Lamp")
        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldPartitionProductsByPriceThreshold() {
        List<Product> products = getAllProducts();
        Map<Boolean, List<Product>> result = firstBatch.partitioningData(products);

        Assertions.assertEquals(5, result.get(true).size()); // Products over 100
        Assertions.assertEquals(5, result.get(false).size()); // Products 100 or below
    }

    @Test
    void shouldGroupProductsByCategory() {
        List<Product> products = getAllProducts();
        Map<String, List<Product>> result = firstBatch.groupingData(products);

        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(4, result.get("Electronics").size());
        Assertions.assertEquals(2, result.get("Appliances").size());
        Assertions.assertEquals(3, result.get("Sportswear").size());
        Assertions.assertEquals(1, result.get("Home").size());
    }

    @Test
    void shouldChainCollectorsToGetAveragePriceByCategory() {
        List<Product> products = getAllProducts();
        Map<String, Double> result = firstBatch.chainingCollectors(products);

        Assertions.assertEquals(877.49, result.get("Electronics"), 0.000001);
        Assertions.assertEquals(84.99, result.get("Appliances"), 0.000001);
        Assertions.assertEquals(56.32333333333333, result.get("Sportswear"), 0.000001);
        Assertions.assertEquals(34.99, result.get("Home"), 0.000001);
    }

    @Test
    void shouldGroupOrdersByCustomerTierAndStatusWithStats() {
        List<Order> orders = getAllOrders();
        Map<String, Map<String, OrderStats>> result = firstBatch.usingCollectingAndThen(orders);

        // Verify the structure has 3 tiers: elite, premium, standard
        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.containsKey("elite"));
        Assertions.assertTrue(result.containsKey("premium"));
        Assertions.assertTrue(result.containsKey("standard"));

        // Verify elite tier
        Map<String, OrderStats> eliteStats = result.get("elite");
        Assertions.assertEquals(3, eliteStats.size()); // delivered, placed, canceled
        Assertions.assertEquals(new OrderStats(1, 2), eliteStats.get("delivered")); // O1: 2 items
        Assertions.assertEquals(new OrderStats(2, 5), eliteStats.get("placed")); // O4: 4 items, O8: 1 item
        Assertions.assertEquals(new OrderStats(1, 1), eliteStats.get("canceled")); // O5: 1 item

        // Verify premium tier
        Map<String, OrderStats> premiumStats = result.get("premium");
        Assertions.assertEquals(2, premiumStats.size()); // delivered, placed
        Assertions.assertEquals(new OrderStats(1, 1), premiumStats.get("delivered")); // O2: 1 item
        Assertions.assertEquals(new OrderStats(1, 2), premiumStats.get("placed")); // O7: 2 items

        // Verify standard tier
        Map<String, OrderStats> standardStats = result.get("standard");
        Assertions.assertEquals(2, standardStats.size()); // shipped, placed
        Assertions.assertEquals(new OrderStats(1, 3), standardStats.get("shipped")); // O3: 3 items
        Assertions.assertEquals(new OrderStats(1, 2), standardStats.get("placed")); // O6: 2 items
    }



    private List<Product> getAllProducts() {
        return List.of(
                new Product("P1", "iPhone 14", "Electronics", new BigDecimal("999.99")),
                new Product("P2", "MacBook Pro", "Electronics", new BigDecimal("1999.99")),
                new Product("P3", "Coffee Maker", "Appliances", new BigDecimal("89.99")),
                new Product("P4", "Running Shoes", "Sportswear", new BigDecimal("129.99")),
                new Product("P5", "Yoga Mat", "Sportswear", new BigDecimal("25.99")),
                new Product("P6", "Water Bottle", "Sportswear", new BigDecimal("12.99")),
                new Product("P7", "Wireless Earbuds", "Electronics", new BigDecimal("159.99")),
                new Product("P8", "Smart Watch", "Electronics", new BigDecimal("349.99")),
                new Product("P9", "Blender", "Appliances", new BigDecimal("79.99")),
                new Product("P10", "Desk Lamp", "Home", new BigDecimal("34.99"))
        );
    }

    private List<Customer> getAllCustomers() {
        return List.of(
                new Customer("C1", "John Smith", "john@example.com", LocalDate.of(2020, 1, 15), "elite"),
                new Customer("C2", "Emma Johnson", "emma@example.com", LocalDate.of(2021, 3, 20), "standard"),
                new Customer("C3", "Michael Brown", "michael@example.com", LocalDate.of(2019, 7, 5), "premium"),
                new Customer("C4", "Olivia Wilson", "olivia@example.com", LocalDate.of(2022, 2, 10), "standard"),
                new Customer("C5", "William Davis", "william@example.com", LocalDate.of(2020, 11, 25), "elite")
        );
    }

    private List<Order> getAllOrders() {

        List<Customer> customers = getAllCustomers();
        List<Product> products = getAllProducts();

        return List.of(
                new Order("O1", customers.get(0), LocalDate.of(2023, 3, 15),
                        List.of(
                                new OrderItem(products.get(0), 1),
                                new OrderItem(products.get(7), 1)
                        ),
                        "delivered"),
                new Order("O2", customers.get(2), LocalDate.of(2023, 4, 2),
                        List.of(
                                new OrderItem(products.get(1), 1)
                        ),
                        "delivered"),
                new Order("O3", customers.get(1), LocalDate.of(2023, 4, 15),
                        List.of(
                                new OrderItem(products.get(2), 1),
                                new OrderItem(products.get(9), 2)
                        ),
                        "shipped"),
                new Order("O4", customers.get(0), LocalDate.of(2023, 5, 1),
                        List.of(
                                new OrderItem(products.get(3), 1),
                                new OrderItem(products.get(4), 1),
                                new OrderItem(products.get(5), 2)
                        ),
                        "placed"),
                new Order("O5", customers.get(4), LocalDate.of(2023, 5, 5),
                        List.of(
                                new OrderItem(products.get(6), 1)
                        ),
                        "canceled"),
                new Order("O6", customers.get(3), LocalDate.of(2023, 5, 10),
                        List.of(
                                new OrderItem(products.get(8), 1),
                                new OrderItem(products.get(9), 1)
                        ),
                        "placed"),
                new Order("O7", customers.get(2), LocalDate.of(2023, 5, 15),
                        List.of(
                                new OrderItem(products.get(0), 1),
                                new OrderItem(products.get(1), 1)
                        ),
                        "placed"),
                new Order("O8", customers.get(0), LocalDate.of(2023, 5, 20),
                        List.of(
                                new OrderItem(products.get(7), 1)
                        ),
                        "placed")
        );
    }

}
