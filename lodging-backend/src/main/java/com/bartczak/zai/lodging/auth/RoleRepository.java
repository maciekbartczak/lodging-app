package com.bartczak.zai.lodging.auth;

import com.bartczak.zai.lodging.user.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}
