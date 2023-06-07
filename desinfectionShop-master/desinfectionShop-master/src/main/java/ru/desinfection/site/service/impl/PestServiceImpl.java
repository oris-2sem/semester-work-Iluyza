package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.desinfection.site.DTO.PestCreateDTO;
import ru.desinfection.site.entity.Pest;
import ru.desinfection.site.exception.NoSuchPestException;
import ru.desinfection.site.repository.PestRepository;
import ru.desinfection.site.service.PestService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PestServiceImpl implements PestService {

    private final PestRepository pestRepository;

    @Override
    public boolean createPest(PestCreateDTO pestCreateDTO) {
        Pest pest = Pest.builder()
                .name(pestCreateDTO.getName())
                .picturePath(pestCreateDTO.getPicturePath())
                .build();
        pestRepository.save(pest);
        log.info("Pest saved in DB: {}", pest);
        return true;
    }

    @Override
    public boolean updatePestById(Long id) {
        return false;
    }

    @Override
    public Pest getPestById(Long id) {
        return pestRepository.findById(id).orElseThrow(() -> new NoSuchPestException(id.toString()));
    }

    @Override
    public boolean deletePestById(Long id) {
        pestRepository.delete(getPestById(id));
        return true;
    }

    @Override
    public List<Pest> getAllPests() {
        return pestRepository.findAll();
    }

    @Override
    public Pest getPestByName(String name) {
        return pestRepository.findPestByName(name);
    }
}
