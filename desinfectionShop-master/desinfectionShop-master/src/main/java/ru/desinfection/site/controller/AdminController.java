package ru.desinfection.site.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.desinfection.site.DTO.ItemCreateDTO;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.utils.RequestUtil;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ModelSetupService modelSetupService;
    private final ItemService itemService;

    @PostMapping("/item")
    public String addItem(@ModelAttribute ItemCreateDTO itemCreateDTO, HttpServletRequest request) {
        log.info("Getting request from ip {} for creating item: {}", RequestUtil.getIp(request), itemCreateDTO);
        itemService.create(itemCreateDTO);
        return "redirect:/admin/item";
    }

    @PostMapping("/item/{id}/update")
    public String updateItem(@ModelAttribute ItemCreateDTO itemCreateDTO, @PathVariable Long id, HttpServletRequest request) {
        log.info("Update method");
        itemService.update(id, itemCreateDTO);
        return "redirect:/item/" + id;
    }

    @DeleteMapping("/item/{id}")
    public void deleteItem(@PathVariable Long id, HttpServletRequest request) {
        log.info("Delete method");
        itemService.deleteById(id);
    }

    @GetMapping("/item")
    public String getAddItemFormPage() {
        return "addItemForm.html";
    }

    @GetMapping("/item/{id}/update")
    public String getUpdateItemFormPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        modelSetupService.setUpUpdateItemPage(model, id);
        return "updateItem.html";
    }
}
