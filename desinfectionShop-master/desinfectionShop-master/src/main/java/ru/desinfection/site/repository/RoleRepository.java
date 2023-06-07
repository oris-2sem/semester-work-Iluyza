package ru.desinfection.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.desinfection.site.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
