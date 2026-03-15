package com.alexpauv.pokemon.service.role;

import com.alexpauv.pokemon.exception.RoleNotFoundException;
import com.alexpauv.pokemon.model.role.Role;
import com.alexpauv.pokemon.repository.role.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService {
    public static final String DEFAULT_ROLE_NAME = "User";
    public static final String ADMIN_ROLE_NAME = "Admin";

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE_NAME).orElseThrow(() -> new RoleNotFoundException("Default User role not found. Please check database initialization."));
    }

    public Role getAdminRole() {
        return roleRepository.findByName(ADMIN_ROLE_NAME).orElseThrow(() -> new RoleNotFoundException("Default Admin role not found. Please check database initialization."));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role not found: " + name));
    }

    public Role getRoleByUuid(String uuid) {
        return roleRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new RoleNotFoundException("Role not found: " + uuid));
    }
}
