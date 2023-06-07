package ru.desinfection.site.service;

import ru.desinfection.site.DTO.PestCreateDTO;
import ru.desinfection.site.entity.Pest;

import java.util.List;
import java.util.stream.Stream;

public interface PestService {
    boolean createPest(PestCreateDTO pestCreateDTO);

    boolean updatePestById(Long id);

    Pest getPestById(Long id);

    boolean deletePestById(Long id);

    List<Pest> getAllPests();

    Pest getPestByName(String name);
}
