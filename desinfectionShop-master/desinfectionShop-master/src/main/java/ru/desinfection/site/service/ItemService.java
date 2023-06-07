package ru.desinfection.site.service;

import org.springframework.data.domain.Page;
import ru.desinfection.site.DTO.ItemCreateDTO;
import ru.desinfection.site.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item create(ItemCreateDTO itemCreateDTO);

    Item update(Long id, ItemCreateDTO itemCreateDTO);

    Item findById(Long id);

    boolean deleteById(Long id);

    Page<Item> findAllByPageNumberAndSortValues(int page, Optional<String> sortValue, Optional<String> searchValue, Optional<String> pestValue);

    List<Item> findAllByListOfId(List<Long> listOfIds);
}
