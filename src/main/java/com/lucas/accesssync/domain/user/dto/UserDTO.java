package com.lucas.accesssync.domain.user.dto;

import com.lucas.accesssync.domain.user.UserRole;

public record UserDTO(
    String name,
    String email, 
    String password,
    UserRole userRole
) {}
