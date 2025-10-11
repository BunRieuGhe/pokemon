package com.alexpauv.pokemon.repository.role;

import com.alexpauv.pokemon.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByUuid(UUID uuid);

    Optional<Role> findByName(String name);
}
