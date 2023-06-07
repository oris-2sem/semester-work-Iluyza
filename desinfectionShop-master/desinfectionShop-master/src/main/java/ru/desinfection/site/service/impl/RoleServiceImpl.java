package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.desinfection.site.entity.Role;
import ru.desinfection.site.exception.NoSuchRoleException;
import ru.desinfection.site.repository.RoleRepository;
import ru.desinfection.site.service.RoleService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Boolean create() {
        return null;
    }

    @Override
    public Boolean update(Role roleToUpdate) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public Role findByName(String name) {
        log.info("Trying to find role with name: {}", name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchRoleException(String.format("Role with name %s does not exists", name)));
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public List<Role> findAllByUserId(Long userId) {
        return null;
    }
}
