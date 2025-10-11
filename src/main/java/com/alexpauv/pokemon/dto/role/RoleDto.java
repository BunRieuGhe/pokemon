package com.alexpauv.pokemon.dto.role;

import com.alexpauv.pokemon.model.role.Role;

import java.util.UUID;

public class RoleDto {
    private UUID uuid;

    private String name;

    public RoleDto(Role role) {
        this.uuid = role.getUuid();
        this.name = role.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
