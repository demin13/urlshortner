package com.shekhar.urlshortner.Auth.Repository;

import com.shekhar.urlshortner.Auth.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findAllById(Long id);

    Optional<Role> findByName(String roleName);

}
