package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.desinfection.site.DTO.AccountCreateDTO;
import ru.desinfection.site.DTO.AuthenticationRequest;
import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Order;
import ru.desinfection.site.repository.AccountRepository;
import ru.desinfection.site.repository.OrderRepository;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.utils.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final OrderRepository orderRepository;

    @Transactional
    public boolean register(AccountCreateDTO accountCreateDTO) {
        Account createdAccount = accountService.create(accountCreateDTO);
        Order savedCurrOrder = orderRepository.save(Order.builder().
                account(createdAccount)
                .build()
        );
        createdAccount.setCurrOrder(savedCurrOrder);

        return true;
    }

    @Transactional
    public String authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
        ));

        Account account = accountService.findByEmail(authRequest.getEmail());

        return jwtUtil.generateToken(account);
    }
}
