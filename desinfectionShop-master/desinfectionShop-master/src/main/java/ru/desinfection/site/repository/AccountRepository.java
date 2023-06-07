package ru.desinfection.site.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.desinfection.site.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}
