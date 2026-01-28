package com.stefan.streams;


import com.stefan.streams.exercices.FirstBatch;
import com.stefan.streams.models.Customer;
import com.stefan.streams.models.Order;
import com.stefan.streams.models.OrderItem;
import com.stefan.streams.models.OrderStats;
import com.stefan.streams.models.Product;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Ignore
public class FirstBatchTest {

    private final FirstBatch firstBatch = new FirstBatch();

    @Test
    void shouldFilterProductsAndCollectStrings() {

        List<Product> products = getAllProducts();
        List<String> result = firstBatch.getElectronicsProductNames(products);

        List<String> response = List.of("iPhone 14", "MacBook Pro", "Wireless Earbuds", "Smart Watch");
        Assert.assertEquals(response, result);
    }

    @Test
    void shouldFindFirstSportswearProductUnder20Dollars() {

        List<Product> products = getAllProducts();
        Product result = firstBatch.findAffordableSportswearProduct(products);

        Product expectedProduct = new Product("P6", "Water Bottle", "Sportswear", new BigDecimal("12.99"));
        Assert.assertEquals(expectedProduct, result);
    }

    @Test
    void shouldTransformOrdersToOrderSummaries() {

        List<Order> orders = getAllOrders();
        List<?> result = firstBatch.mapOrdersToSummaries(orders);

        Assert.assertEquals(8, result.size());
        // Further assertions can be added here to validate the content of the order summaries
    }

    @Test
    void shouldSortProductsByPrice() {

        List<Product> products = getAllProducts();
        List<Product> result = firstBatch.sortProductsByPrice(products);

        Assert.assertEquals(10, result.size());
        Assert.assertEquals("Water Bottle", result.get(0).name());
        Assert.assertEquals("MacBook Pro", result.get(9).name());
    }

    @Test
    void shouldLimitAndSkipProductsForPagination() {

        int pageSize = 3;
        int pageNumber = 1; // 0-based index, so this is the second page
        List<Product> products = getAllProducts();

        List<Product> result = firstBatch.getProductsPage(products, pageSize, pageNumber);

        Assert.assertEquals(3, result.size());
        Assert.assertEquals("Running Shoes", result.get(0).name());
        Assert.assertEquals("Yoga Mat", result.get(1).name());
        Assert.assertEquals("Water Bottle", result.get(2).name());
    }

    @Test
    void shouldFlattenNestedCollectionsOfOrderedProducts() {

        List<Order> orders = getAllOrders();
        List<Product> result = firstBatch.extractAllOrderedProducts(orders);

        Assert.assertEquals(10, result.size());
        // Further assertions can be added here to validate the content of the products list
    }

    @Test
    void shouldReduceOrdersToTotalRevenue() {

        List<Order> orders = getAllOrders();
        double result = firstBatch.calculateTotalRevenue(orders);

        double expectedTotalRevenue = 999.99 + 349.99 + 1999.99 + 89.99 + 34.99 * 2 + 129.99 + 25.99 + 12.99 * 2 + 159.99 + 79.99 + 34.99 + 999.99 + 1999.99 + 349.99;
        Assert.assertEquals(expectedTotalRevenue, result, 0.01);
    }

    @Test
    void shouldShortCircuitOperationsOnProducts() {

        List<Product> products = getAllProducts();
        Product result = firstBatch.findExpensiveElectronicsProduct(products);

        Product expectedProduct = new Product("P2", "MacBook Pro", "Electronics", new BigDecimal("1999.99"));
        Assert.assertEquals(expectedProduct, result);
    }

    @Test
    void shouldJoinProductCategoriesIntoString() {

        List<Product> products = getAllProducts();
        String result = firstBatch.joinProductCategories(products);

        List<String> expectedCategoryNames = List.of("Electronics", "Appliances", "Sportswear", "Home");
        expectedCategoryNames.forEach(c ->
                Assert.assertTrue(result.contains(c)));
    }

    @Test
    void shouldCollectProductNamesToUnmodifiableList() {

        List<Product> products = getAllProducts();
        List<String> result = firstBatch.collectProductNamesToUnmodifiableList(products);

        List<String> expectedNames = products.stream().map(Product::name).toList();
        Assert.assertEquals(expectedNames, result);

        // Attempting to modify the list should throw an UnsupportedOperationException
        Assert.assertThrows(UnsupportedOperationException.class, () -> result.add("New Product"));
    }

    @Test
    void shouldCollectProductCategoriesToSet() {
        List<Product> products = getAllProducts();
        Set<String> result = firstBatch.collectCategoriesToSet(products);

        // The expected unique categories from getAllProducts()
        List<String> expectedCategories = List.of("Electronics", "Appliances", "Sportswear", "Home");

        Assert.assertEquals(expectedCategories.size(), result.size());
        Assert.assertTrue(result.containsAll(expectedCategories));
    }

    @Test
    void shouldCollectProductNamesToMap() {
        List<Product> products = getAllProducts();
        var result = firstBatch.mapProductNamesToPrices(products);

        Assert.assertEquals(products.size(), result.size());
        for (Product product : products) {
            Assert.assertEquals(product.price(), result.get(product.name()));
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
        Map<String, List<String>> result = firstBatch.groupExpensiveProductNamesByCategory(products);

        Map<String, List<String>> expected = Map.of(
                "Electronics", List.of("iPhone 14", "MacBook Pro", "Wireless Earbuds", "Smart Watch"),
                "Appliances", List.of(),
                "Sportswear", List.of(),
                "Home", List.of()
        );

        Assert.assertEquals(expected, result);
    }

    @Test
    void shouldHandleDuplicateKeysWhenCollectingToMap() {
        List<Product> products = getAllProducts();
        Map<BigDecimal, String> result = firstBatch.mapPricesToMergedProductNames(products);

        // Since there are no duplicate prices in the provided products, the size should match
        Assert.assertEquals(products.size(), result.size());
        for (Product product : products) {
            Assert.assertTrue(result.get(product.price()).contains(product.name()));
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

        Assert.assertEquals(expected, result);
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

        Assert.assertEquals(expected, result);
    }

    @Test
    void shouldPartitionProductsByPriceThreshold() {
        List<Product> products = getAllProducts();
        Map<Boolean, List<Product>> result = firstBatch.partitionProductsByPrice(products);

        Assert.assertEquals(5, result.get(true).size()); // Products over 100
        Assert.assertEquals(5, result.get(false).size()); // Products 100 or below
    }

    @Test
    void shouldGroupProductsByCategory() {
        List<Product> products = getAllProducts();
        Map<String, List<Product>> result = firstBatch.groupProductsByCategory(products);

        Assert.assertEquals(4, result.size());
        Assert.assertEquals(4, result.get("Electronics").size());
        Assert.assertEquals(2, result.get("Appliances").size());
        Assert.assertEquals(3, result.get("Sportswear").size());
        Assert.assertEquals(1, result.get("Home").size());
    }

    @Test
    void shouldChainCollectorsToGetAveragePriceByCategory() {
        List<Product> products = getAllProducts();
        Map<String, Double> result = firstBatch.calculateAveragePriceByCategory(products);

        Assert.assertEquals(877.49, result.get("Electronics"), 0.000001);
        Assert.assertEquals(84.99, result.get("Appliances"), 0.000001);
        Assert.assertEquals(56.32333333333333, result.get("Sportswear"), 0.000001);
        Assert.assertEquals(34.99, result.get("Home"), 0.000001);
    }

    @Test
    void shouldGroupOrdersByCustomerTierAndStatusWithStats() {
        List<Order> orders = getAllOrders();
        Map<String, Map<String, OrderStats>> result = firstBatch.analyzeOrdersByCustomerTierAndStatus(orders);

        // Verify the structure has 3 tiers: elite, premium, standard
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.containsKey("elite"));
        Assert.assertTrue(result.containsKey("premium"));
        Assert.assertTrue(result.containsKey("standard"));

        // Verify elite tier
        Map<String, OrderStats> eliteStats = result.get("elite");
        Assert.assertEquals(3, eliteStats.size()); // delivered, placed, canceled
        Assert.assertEquals(new OrderStats(1, 2), eliteStats.get("delivered")); // O1: 2 items
        Assert.assertEquals(new OrderStats(2, 5), eliteStats.get("placed")); // O4: 4 items, O8: 1 item
        Assert.assertEquals(new OrderStats(1, 1), eliteStats.get("canceled")); // O5: 1 item

        // Verify premium tier
        Map<String, OrderStats> premiumStats = result.get("premium");
        Assert.assertEquals(2, premiumStats.size()); // delivered, placed
        Assert.assertEquals(new OrderStats(1, 1), premiumStats.get("delivered")); // O2: 1 item
        Assert.assertEquals(new OrderStats(1, 2), premiumStats.get("placed")); // O7: 2 items

        // Verify standard tier
        Map<String, OrderStats> standardStats = result.get("standard");
        Assert.assertEquals(2, standardStats.size()); // shipped, placed
        Assert.assertEquals(new OrderStats(1, 3), standardStats.get("shipped")); // O3: 3 items
        Assert.assertEquals(new OrderStats(1, 2), standardStats.get("placed")); // O6: 2 items
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
