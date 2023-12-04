package com.lucas.accesssync.domain.user.dto;

import com.lucas.accesssync.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
} 
