package com.stefan.mongoDB.repository;

import com.stefan.mongoDB.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find user by email address.
     * Spring Data MongoDB automatically implements this based on method name.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find users within an age range (inclusive).
     */
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    /**
     * Find users whose name contains the given string (case-sensitive).
     */
    List<User> findByNameContaining(String name);

    /**
     * Delete user by email address.
     */
    void deleteByEmail(String email);
}
