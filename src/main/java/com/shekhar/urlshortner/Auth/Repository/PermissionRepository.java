package com.shekhar.urlshortner.Auth.Repository;

import com.shekhar.urlshortner.Auth.Models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findAllById(Long id);
    List<Permission> findByNameIn(List<String> names);
}
