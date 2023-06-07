package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.desinfection.site.DTO.ItemCreateDTO;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Pest;
import ru.desinfection.site.exception.NoSuchItemException;
import ru.desinfection.site.mapper.ItemMapper;
import ru.desinfection.site.repository.ItemRepository;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.service.PestService;
import ru.desinfection.site.utils.PictureUtil;
import ru.desinfection.site.utils.SortingUtils;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final PestService pestService;
    private final ItemMapper itemMapper;
    private final SortingUtils sortingUtils;
    private final PictureUtil pictureUtil;
    @Value("${itemsOnPage}")
    private Integer itemsOnPage;

    @Override
    @Transactional
    public Item create(ItemCreateDTO itemCreateDTO) {
        log.info("Trying to save item: {}", itemCreateDTO);

        List<Pest> pestList = mapPests(itemCreateDTO);

        Item savedItem = itemRepository.save(
                itemMapper.toItem(itemCreateDTO, pestList)
        );
        savedItem.setPicturePath(pictureUtil.saveItemDetailsImageAndGetPath(itemCreateDTO.getItemImage(), savedItem.getId()));

        log.info("Item saved in DB {}", savedItem);
        return savedItem;
    }



    @Override
    @CachePut("items")
    @Transactional
    public Item update(Long id, ItemCreateDTO itemCreateDTO) {
        List<Pest> pestList = mapPests(itemCreateDTO);
        Item item = itemMapper.toItem(itemCreateDTO, pestList);
        item.setId(id);
        if(itemCreateDTO.getItemImage().isEmpty()) {
            item.setPicturePath(findById(id).getPicturePath());
        }
        else {
            item.setPicturePath(pictureUtil.saveItemDetailsImageAndGetPath(itemCreateDTO.getItemImage(), item.getId()));
        }
        itemRepository.save(item);
        return item;
    }

    private List<Pest> mapPests(ItemCreateDTO itemCreateDTO) {
        return itemCreateDTO.getPests().stream()
                .map(pestService::getPestByName)
                .toList();
    }

    @Override
    @Cacheable("items")
    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new NoSuchItemException(id.toString()));
    }

    @Override
    @CacheEvict(value = "items", key = "#id")
    @Transactional
    public boolean deleteById(Long id) {
        itemRepository.delete(findById(id));
        return true;
    }

    @Override
    public Page<Item> findAllByPageNumberAndSortValues(int page, Optional<String> sortValue, Optional<String> searchValue, Optional<String> pestValue) {
        PageRequest paging = PageRequest.of(page - 1, itemsOnPage, sortingUtils.parseSortValue(sortValue));

        if (searchValue.isPresent()) {
            return itemRepository.findByNameContainingIgnoreCase(paging, searchValue.get());
        }

        if (pestValue.isPresent() && searchValue.isEmpty()) {
            Pest pest = pestService.getPestByName(pestValue.get());
            return itemRepository.findByPestsContaining(paging, pest);
        }

        return itemRepository.findAll(paging);
    }

    @Override
    public List<Item> findAllByListOfId(List<Long> listOfIds) {
        return itemRepository.findAllByIdIn(listOfIds);
    }
}
