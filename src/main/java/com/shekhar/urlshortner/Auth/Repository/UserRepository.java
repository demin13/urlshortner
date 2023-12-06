package com.shekhar.urlshortner.Auth.Repository;

import com.shekhar.urlshortner.Auth.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
