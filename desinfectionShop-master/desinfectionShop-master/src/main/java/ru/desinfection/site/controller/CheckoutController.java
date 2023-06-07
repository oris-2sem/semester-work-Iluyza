package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CheckoutController {

    private final ModelSetupService modelSetupService;

    private final JwtUtil jwtUtil;

    @Resource(name = "sessionCart")
    LinkedHashMap<Long, SessionOrderedItem> sessionCart;

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
      modelSetupService.setUpCheckout(model, request, sessionCart);
      return "checkout";
    }
}
