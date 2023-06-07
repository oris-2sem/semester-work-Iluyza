package ru.desinfection.site.service;

import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Role;

import java.util.List;

public interface RoleService {
    Boolean create();
    Boolean update(Role roleToUpdate);
    Role findById(Long id);
    Role findByName(String name);
    Boolean delete(Long id);
    List<Role> findAll();
    List<Role> findAllByUserId(Long userId);
}
