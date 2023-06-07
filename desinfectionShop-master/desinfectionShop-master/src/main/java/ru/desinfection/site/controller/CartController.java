package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.service.impl.ModelSetupService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ModelSetupService modelSetupService;

    @Resource(name = "sessionCart")
    LinkedHashMap<Long, SessionOrderedItem> sessionCart;

    @GetMapping
    public String getCartPage(HttpServletRequest request, Model model) {
        modelSetupService.setUpCartPage(model, request);
        return "cart";
    }
}
