package com.jong.firstproject.repository;

import com.jong.firstproject.model.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUser, Integer> {
    Optional<JpaUser> findByUsername(String username);
}
