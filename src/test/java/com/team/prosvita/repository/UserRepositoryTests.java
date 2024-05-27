package com.team.prosvita.repository;

import com.team.prosvita.entities.Role;
import com.team.prosvita.entities.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    public void setUp() {
        User user = new User("name", "surname","testuser",
                "testuser@example.com","testpassword123", Role.USER);
        userRepository.save(user);
    }
    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testFindUserByEmailAndPassword() {

        Optional<User> foundUser = userRepository.findUserByEmailAndPassword("testuser@example.com", "testpassword123");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    public void testExistsUserByEmailAndPassword() {
        boolean exists = userRepository.existsUserByEmailAndPassword("testuser@example.com", "testpassword123");
        assertTrue(exists);
    }

    @Test
    public void testUserNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(foundUser.isPresent());
    }
}
