package com.team.prosvita.repository;

import com.team.prosvita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// User DAO
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    // if you need to use other attributes, write custom method here
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
