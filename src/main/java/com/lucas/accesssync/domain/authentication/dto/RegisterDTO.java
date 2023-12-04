package com.lucas.accesssync.domain.authentication.dto;

import com.lucas.accesssync.domain.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) { }
