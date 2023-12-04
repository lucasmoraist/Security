package com.lucas.accesssync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.security.core.userdetails.UserDetails;

import com.lucas.accesssync.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByEmail(String email);
}
