package ru.desinfection.site.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.desinfection.site.DTO.AddressUpdateDTO;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class PersonalAreaController {

    private final ModelSetupService modelSetupService;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    @GetMapping
    public String getPersonalAreaPage(Model model, HttpServletRequest request) {
        modelSetupService.setUpPersonalAreaPage(model, request);
        return "personal-area";
    }

    @GetMapping("/orders/{page}")
    public String getAllOrdersPage(Model model, @PathVariable int page, HttpServletRequest request) {
        modelSetupService.setUpPersonalOrdersPage(model, page, request);
        return "personal-orders";
    }

    @PatchMapping
    @ResponseBody
    public void updateAddress(@RequestBody AddressUpdateDTO addressUpdateDTO, HttpServletRequest request) {
        accountService.update(jwtUtil.getEmailFromRequest(request), addressUpdateDTO);
    }

}
