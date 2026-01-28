package com.stefan.mongoDB;

import com.stefan.mongoDB.entity.User;
import com.stefan.mongoDB.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple MongoDB test example - Perfect for learning!
 *
 * This test shows the basic pattern:
 * 1. Add data to MongoDB
 * 2. Query the data
 * 3. Verify the results
 */
class SimpleUserTest extends AbstractBaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        // Start with a clean database for each test
        userRepository.deleteAll();
    }

    @Test
    void simpleTest_AddUserAndFindIt() {
        // 1. ADD DATA - Create and save a user to MongoDB
        User user = new User(null, "Stefan", "stefan@example.com", 28);
        userRepository.save(user);

        // 2. QUERY DATA - Find all users
        var allUsers = userRepository.findAll();

        // 3. VERIFY - Check the results
        assertEquals(1, allUsers.size());
        assertEquals("Stefan", allUsers.get(0).getName());
    }

    @Test
    void simpleTest_AddMultipleUsersAndCount() {
        // 1. ADD DATA - Add 3 users to MongoDB
        userRepository.save(new User(null, "Alice", "alice@example.com", 25));
        userRepository.save(new User(null, "Bob", "bob@example.com", 30));
        userRepository.save(new User(null, "Charlie", "charlie@example.com", 35));

        // 2. QUERY DATA - Count how many users are in the database
        long count = userRepository.count();

        // 3. VERIFY - Should have 3 users
        assertEquals(3, count);
    }

    @Test
    void simpleTest_FindUserByEmail() {
        // 1. ADD DATA - Add some users
        userRepository.save(new User(null, "John", "john@example.com", 40));
        userRepository.save(new User(null, "Jane", "jane@example.com", 35));

        // 2. QUERY DATA - Find user by email using repository method
        var foundUser = userRepository.findByEmail("jane@example.com");

        // 3. VERIFY - Should find Jane
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("Jane", foundUser.get().getName());
        assertEquals(35, foundUser.get().getAge());
    }

    @Test
    void simpleTest_UpdateUser() {
        // 1. ADD DATA - Save a user
        User user = new User(null, "Old Name", "old@example.com", 20);
        User saved = userRepository.save(user);

        // 2. UPDATE DATA - Change the name and age
        saved.setName("New Name");
        saved.setAge(21);
        userRepository.save(saved);

        // 3. QUERY DATA - Get the user again
        User updated = userRepository.findById(saved.getId()).orElseThrow();

        // 4. VERIFY - Changes were saved
        assertEquals("New Name", updated.getName());
        assertEquals(21, updated.getAge());
        assertEquals("old@example.com", updated.getEmail()); // Email unchanged
    }

    @Test
    void simpleTest_DeleteUser() {
        // 1. ADD DATA
        User user = new User(null, "To Delete", "delete@example.com", 50);
        User saved = userRepository.save(user);

        // Verify it exists
        assertTrue(userRepository.findById(saved.getId()).isPresent());

        // 2. DELETE DATA
        userRepository.deleteById(saved.getId());

        // 3. VERIFY - User is gone
        assertFalse(userRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void simpleTest_FindUsersByAge() {
        // 1. ADD DATA - Users with different ages
        userRepository.save(new User(null, "Young", "young@example.com", 20));
        userRepository.save(new User(null, "Middle", "middle@example.com", 35));
        userRepository.save(new User(null, "Old", "old@example.com", 60));

        // 2. QUERY DATA - Find users between 30 and 40 years old
        var usersInRange = userRepository.findByAgeBetween(30, 40);

        // 3. VERIFY - Only "Middle" should be found
        assertEquals(1, usersInRange.size());
        assertEquals("Middle", usersInRange.get(0).getName());
    }
}
