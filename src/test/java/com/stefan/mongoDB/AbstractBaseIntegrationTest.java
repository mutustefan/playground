package com.stefan.mongoDB;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract base class for MongoDB integration tests.
 * Uses TestContainers to spin up a real MongoDB instance in Docker.
 *
 * Usage: Extend this class in your test classes to get MongoDB support.
 */
@Testcontainers
@SpringBootTest(classes = MongoDBTestConfiguration.class)
@ActiveProfiles("test")
public abstract class AbstractBaseIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        // TestContainers automatically starts the container
        // Use getReplicaSetUrl for full MongoDB URI with authentication support
        registry.add("spring.data.mongodb.uri",
                () -> mongoDBContainer.getReplicaSetUrl("testdb"));
    }
}