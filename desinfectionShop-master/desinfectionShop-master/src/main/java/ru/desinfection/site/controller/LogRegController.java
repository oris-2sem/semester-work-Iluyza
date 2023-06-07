package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.desinfection.site.DTO.AccountCreateDTO;
import ru.desinfection.site.DTO.AuthenticationRequest;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.service.impl.AuthService;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.utils.CookieUtil;
import ru.desinfection.site.utils.RequestUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogRegController {

    private final AuthService authService;

    private final CookieUtil cookieUtil;

    private final ModelSetupService modelSetupService;

    @GetMapping("/register")
    public String getRegistrationPage(Model model, HttpServletRequest request) {
        modelSetupService.setUpRegistrationPage(model);
        return "auth/registration";
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestBody AccountCreateDTO accountCreateDTO, HttpServletRequest request) {
        log.info("Getting request for registering user[{}] from ip: {}", accountCreateDTO, RequestUtil.getIp(request));
        authService.register(accountCreateDTO);

        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        modelSetupService.setUpLoginPage(model);
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("Getting request for authenticating user[{}] from ip: {}", authenticationRequest, RequestUtil.getIp(request));

        String token = authService.authenticate(authenticationRequest);
        cookieUtil.setUpAuthCookie(token, response);

        return "redirect:/";
    }
}
