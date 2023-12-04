package com.lucas.accesssync.domain.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lucas.accesssync.domain.user.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private UserRole role;
    public UserRole getUserRole(){
        return this.getUserRole();
    }

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;


    public User(UserDTO dto){
        this.name = dto.name();
        this.email = dto.email();
        this.password = dto.password();
        this.role = dto.userRole();
    }

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Verifica as roles do nosso projeto
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    // Verificar se a conta não está expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Verificar se o usuário não está bloqueado
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Verificar se a credencial não está expirada
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Verificar se o usuário está ativo
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
