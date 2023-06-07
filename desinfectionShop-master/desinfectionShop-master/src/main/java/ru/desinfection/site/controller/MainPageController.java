package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.desinfection.site.service.impl.ModelSetupService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MainPageController {

    private final ModelSetupService modelSetupService;

    @GetMapping("/")
    public String getMainPage(Model model, HttpServletRequest request) {
        modelSetupService.setUpMainPage(model, request);
        return "index.html";
    }

}
