package com.team.prosvita.repository;

import com.team.prosvita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// User DAO
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    // if you need to use other attributes, write custom method here
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
    Optional<User> findUserByEmailAndPassword(String email, String password);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = ?1 AND u.password = ?2")
    boolean existsUserByEmailAndPassword(String email, String password);
}
