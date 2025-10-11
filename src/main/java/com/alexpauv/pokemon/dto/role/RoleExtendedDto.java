package com.alexpauv.pokemon.dto.role;

import com.alexpauv.pokemon.model.role.Authority;
import com.alexpauv.pokemon.model.role.Role;

import java.time.LocalDateTime;
import java.util.List;

public class RoleExtendedDto extends RoleDto {
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Authority> authorities;

    public RoleExtendedDto(Role role) {
        super(role);
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
        this.authorities = role.getAuthorities().stream().toList();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
