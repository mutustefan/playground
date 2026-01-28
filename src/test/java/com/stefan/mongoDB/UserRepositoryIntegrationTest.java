package com.stefan.mongoDB;

import com.stefan.mongoDB.entity.User;
import com.stefan.mongoDB.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for UserRepository using real MongoDB via TestContainers.
 * Extends AbstractBaseIntegrationTest to get MongoDB setup automatically.
 */
class UserRepositoryIntegrationTest extends AbstractBaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clean database before each test
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveUserAndGenerateId() {
        // Given - Create a new user (id is null)
        User user = new User(null, "John Doe", "john@example.com", 30);

        // When - Save to MongoDB
        User savedUser = userRepository.save(user);

        // Then - Verify id was generated
        assertNotNull(savedUser.getId(), "MongoDB should generate an ID");
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals(30, savedUser.getAge());
    }

    @Test
    void shouldFindUserById() {
        // Given - Save a user first
        User user = new User(null, "Jane Smith", "jane@example.com", 25);
        User savedUser = userRepository.save(user);
        String userId = savedUser.getId();

        // When - Find by ID
        Optional<User> foundUser = userRepository.findById(userId);

        // Then - Verify user was found
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("Jane Smith", foundUser.get().getName());
        assertEquals("jane@example.com", foundUser.get().getEmail());
    }

    @Test
    void shouldFindUserByEmail() {
        // Given - Add multiple users to database
        userRepository.save(new User(null, "Alice Brown", "alice@example.com", 28));
        userRepository.save(new User(null, "Bob Wilson", "bob@example.com", 35));
        userRepository.save(new User(null, "Charlie Davis", "charlie@example.com", 42));

        // When - Find user by email (custom repository method)
        Optional<User> foundUser = userRepository.findByEmail("bob@example.com");

        // Then - Verify correct user was found
        assertTrue(foundUser.isPresent(), "User with email should be found");
        assertEquals("Bob Wilson", foundUser.get().getName());
        assertEquals(35, foundUser.get().getAge());
    }

    @Test
    void shouldFindUsersByAgeRange() {
        // Given - Add users with different ages
        userRepository.save(new User(null, "Young User", "young@example.com", 18));
        userRepository.save(new User(null, "Adult User 1", "adult1@example.com", 30));
        userRepository.save(new User(null, "Adult User 2", "adult2@example.com", 35));
        userRepository.save(new User(null, "Senior User", "senior@example.com", 65));

        // When - Find users between 25 and 40 years old
        List<User> usersInRange = userRepository.findByAgeBetween(25, 40);

        // Then - Verify only users in range are returned
        assertEquals(2, usersInRange.size(), "Should find 2 users in age range");
        assertTrue(usersInRange.stream().allMatch(u -> u.getAge() >= 25 && u.getAge() <= 40),
                "All users should be in the age range");
    }

    @Test
    void shouldFindUsersByNameContaining() {
        // Given - Add users with similar names
        userRepository.save(new User(null, "John Doe", "john.doe@example.com", 30));
        userRepository.save(new User(null, "Jane Doe", "jane.doe@example.com", 28));
        userRepository.save(new User(null, "John Smith", "john.smith@example.com", 35));
        userRepository.save(new User(null, "Alice Brown", "alice@example.com", 25));

        // When - Find users with "Doe" in their name
        List<User> doeUsers = userRepository.findByNameContaining("Doe");

        // Then - Verify correct users were found
        assertEquals(2, doeUsers.size(), "Should find 2 users with 'Doe' in name");
        assertTrue(doeUsers.stream().allMatch(u -> u.getName().contains("Doe")),
                "All users should have 'Doe' in their name");
    }

    @Test
    void shouldDeleteUserByEmail() {
        // Given - Add a user
        userRepository.save(new User(null, "To Delete", "delete@example.com", 40));

        // Verify user exists
        Optional<User> beforeDelete = userRepository.findByEmail("delete@example.com");
        assertTrue(beforeDelete.isPresent(), "User should exist before deletion");

        // When - Delete by email
        userRepository.deleteByEmail("delete@example.com");

        // Then - Verify user no longer exists
        Optional<User> afterDelete = userRepository.findByEmail("delete@example.com");
        assertFalse(afterDelete.isPresent(), "User should not exist after deletion");
    }

    @Test
    void shouldCountAllUsers() {
        // Given - Add multiple users
        userRepository.save(new User(null, "User 1", "user1@example.com", 20));
        userRepository.save(new User(null, "User 2", "user2@example.com", 25));
        userRepository.save(new User(null, "User 3", "user3@example.com", 30));
        userRepository.save(new User(null, "User 4", "user4@example.com", 35));

        // When - Count all users
        long totalUsers = userRepository.count();

        // Then - Verify count is correct
        assertEquals(4, totalUsers, "Should have 4 users in database");
    }

    @Test
    void shouldFindAllUsers() {
        // Given - Add multiple users
        userRepository.save(new User(null, "Alice", "alice@example.com", 25));
        userRepository.save(new User(null, "Bob", "bob@example.com", 30));
        userRepository.save(new User(null, "Charlie", "charlie@example.com", 35));

        // When - Find all users
        List<User> allUsers = userRepository.findAll();

        // Then - Verify all users are returned
        assertEquals(3, allUsers.size(), "Should return all 3 users");
    }

    @Test
    void shouldUpdateUser() {
        // Given - Save a user
        User user = new User(null, "Original Name", "original@example.com", 25);
        User savedUser = userRepository.save(user);
        String userId = savedUser.getId();

        // When - Update user details
        savedUser.setName("Updated Name");
        savedUser.setEmail("updated@example.com");
        savedUser.setAge(26);
        userRepository.save(savedUser);

        // Then - Verify changes were saved
        Optional<User> updatedUser = userRepository.findById(userId);
        assertTrue(updatedUser.isPresent(), "User should still exist");
        assertEquals("Updated Name", updatedUser.get().getName());
        assertEquals("updated@example.com", updatedUser.get().getEmail());
        assertEquals(26, updatedUser.get().getAge());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Given - No users in database (cleared in @BeforeEach)

        // When - Try to find non-existent user
        Optional<User> notFound = userRepository.findByEmail("nonexistent@example.com");

        // Then - Should return empty
        assertFalse(notFound.isPresent(), "Should return empty for non-existent user");
    }

    @Test
    void shouldHandleMultipleUsersWithDifferentAges() {
        // Given - Add users with various ages
        userRepository.save(new User(null, "Teen", "teen@example.com", 15));
        userRepository.save(new User(null, "Young Adult", "young@example.com", 22));
        userRepository.save(new User(null, "Adult", "adult@example.com", 40));
        userRepository.save(new User(null, "Senior", "senior@example.com", 70));

        // When - Query different age ranges
        List<User> teenagers = userRepository.findByAgeBetween(13, 19);
        List<User> adults = userRepository.findByAgeBetween(20, 50);
        List<User> seniors = userRepository.findByAgeBetween(60, 100);

        // Then - Verify results
        assertEquals(1, teenagers.size(), "Should find 1 teenager");
        assertEquals(2, adults.size(), "Should find 2 adults");
        assertEquals(1, seniors.size(), "Should find 1 senior");
    }
}
