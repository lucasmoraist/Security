package com.lucas.accesssync.controller;

import com.lucas.accesssync.domain.authentication.dto.TokenDTO;
import com.lucas.accesssync.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lucas.accesssync.domain.authentication.dto.AuthenticationDTO;
import com.lucas.accesssync.domain.authentication.dto.RegisterDTO;
import com.lucas.accesssync.domain.user.User;
import com.lucas.accesssync.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService service;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = service.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){

        if(this.repository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encrypetdPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.email(), encrypetdPassword, dto.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
}
