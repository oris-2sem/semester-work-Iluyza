package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.service.impl.OrderServiceImpl;
import ru.desinfection.site.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ModelSetupService modelSetupService;
    private final OrderServiceImpl orderService;
    private final JwtUtil jwtUtil;

    @Resource(name = "sessionCart")
    LinkedHashMap<Long, SessionOrderedItem> sessionCart;


    @GetMapping("/{id}")
    public String getItemById(@PathVariable Long id, HttpServletRequest request, Model model) {
        modelSetupService.setUpItemPage(model, id, request, sessionCart);
        return "item-details";
    }

    @ResponseBody
    @PutMapping("/{id}")
    @Transactional
    public void addItemToCurrOrder(@PathVariable Long id, HttpServletRequest request) {
        log.info("Getting request for adding item to order");
        orderService.addItemToCurrOrder(jwtUtil.getEmailFromRequest(request), id, sessionCart);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @Transactional
    public void decreaseItemFromCurrOrder(@PathVariable Long id, HttpServletRequest request) {
        log.info("Getting request for decreasing item from order");
        orderService.deleteItemFromCurrOrder(jwtUtil.getEmailFromRequest(request), id, sessionCart);
    }

    @ResponseBody
    @DeleteMapping("/{id}/remove")
    @Transactional
    public void fullDeleteItemFromCurrOrder(@PathVariable Long id, HttpServletRequest request) {
        orderService.deleteFullItemFromCurrOrder(jwtUtil.getEmailFromRequest(request), id, sessionCart);
    }

}

