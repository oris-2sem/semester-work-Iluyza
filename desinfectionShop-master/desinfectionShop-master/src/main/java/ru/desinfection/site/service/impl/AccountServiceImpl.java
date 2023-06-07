package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.desinfection.site.DTO.AccountCreateDTO;
import ru.desinfection.site.DTO.AddressUpdateDTO;
import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Order;
import ru.desinfection.site.exception.AccountAlreadyExistsException;
import ru.desinfection.site.exception.NoSuchAccountException;
import ru.desinfection.site.mapper.AccountMapper;
import ru.desinfection.site.repository.AccountRepository;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.service.RoleService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public Account create(AccountCreateDTO accountCreateDTO) {
        log.info("Creating account: {}", accountCreateDTO);

        if (accountRepository.existsByEmail(accountCreateDTO.getEmail())) {
            throw new AccountAlreadyExistsException(accountCreateDTO.getEmail());
        }
        Account accountToSave = accountMapper.toAccount(accountCreateDTO);
        setUpAdditionalInfo(accountToSave);

        Account savedAccount = accountRepository.save(accountToSave);

        log.info("Saved account in DB: {}", savedAccount);
        return savedAccount;
    }

    private void setUpAdditionalInfo(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setIsActive(false);
        account.setRoles(List.of(
                roleService.findByName("USER")
        ));
    }

    @Override
    public boolean update(String email, AddressUpdateDTO addressUpdateDTO) {
        Account account = findByEmail(email);
        log.info("set addressUpdateDTO to {}", addressUpdateDTO.getAddress());
        account.setAddress(addressUpdateDTO.getAddress());
        return false;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NoSuchAccountException(id.toString()));
    }

    @Override
    public Account findByEmail(String email) {
        log.info("Trying to find account with email: {}", email);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchAccountException(String.format("Аккаунт с email %s не существует", email)));

        log.info("Returning account with email: {}", email);
        return account;
    }

    @Override
    public boolean deactivateById(Long id) {
        accountRepository.delete(findById(id));
        return true;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
