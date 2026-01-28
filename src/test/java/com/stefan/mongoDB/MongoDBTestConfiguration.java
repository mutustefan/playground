package com.stefan.mongoDB;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Test configuration for MongoDB integration tests.
 * This is NOT a real application - it's only used for testing.
 *
 * Provides:
 * - Spring Boot autoconfiguration for MongoDB
 * - Component scanning in com.stefan.mongoDB package
 * - MongoDB repository support
 */
@SpringBootApplication(scanBasePackages = "com.stefan.mongoDB")
@EnableMongoRepositories(basePackages = "com.stefan.mongoDB.repository")
public class MongoDBTestConfiguration {
    // No main method needed - this is only for tests
}
