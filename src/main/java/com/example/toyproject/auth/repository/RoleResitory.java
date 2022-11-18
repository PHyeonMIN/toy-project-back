package com.example.toyproject.auth.repository;

import com.example.toyproject.auth.model.ERole;
import com.example.toyproject.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleResitory extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
