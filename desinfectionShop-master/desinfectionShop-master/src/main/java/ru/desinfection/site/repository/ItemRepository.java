package ru.desinfection.site.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Pest;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByIdIn(List<Long> listOfIds);

    Page<Item> findByNameContainingIgnoreCase(PageRequest pageRequest, String searchValue);

    Page<Item> findByPestsContaining(PageRequest pageRequest, Pest pest);
}
