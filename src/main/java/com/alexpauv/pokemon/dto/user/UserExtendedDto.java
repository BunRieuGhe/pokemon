package com.alexpauv.pokemon.dto.user;

import com.alexpauv.pokemon.dto.role.RoleDto;
import com.alexpauv.pokemon.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserExtendedDto extends UserDto {
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<RoleDto> roles;

    public UserExtendedDto(User user) {
        super(user);
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.roles = user.getRoles().stream().map(RoleDto::new).toList();
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
