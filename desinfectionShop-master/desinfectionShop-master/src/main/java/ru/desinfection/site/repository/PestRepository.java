package ru.desinfection.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.desinfection.site.entity.Pest;

import java.util.List;

public interface PestRepository extends JpaRepository<Pest, Long> {

    Pest findPestByName(String name);

}
