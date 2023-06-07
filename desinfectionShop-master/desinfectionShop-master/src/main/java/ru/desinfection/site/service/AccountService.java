package ru.desinfection.site.service;

import ru.desinfection.site.DTO.AccountCreateDTO;
import ru.desinfection.site.DTO.AddressUpdateDTO;
import ru.desinfection.site.entity.Account;

import java.util.List;

public interface AccountService {
    Account create(AccountCreateDTO accountCreateDTO);

    boolean update(String email, AddressUpdateDTO addressUpdateDTO);

    Account findById(Long id);

    Account findByEmail(String email);

    boolean deactivateById(Long id);

    List<Account> findAll();
}
