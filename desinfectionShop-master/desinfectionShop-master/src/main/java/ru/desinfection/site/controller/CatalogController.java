package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.service.impl.ModelSetupService;
import ru.desinfection.site.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final ModelSetupService modelSetupService;

    @GetMapping("page/{page}")
    public String getAllItems(Model model, @PathVariable("page") int pageNumber, HttpServletRequest request,
                              @RequestParam Optional<String> sortValue, @RequestParam Optional<String> searchValue, @RequestParam Optional<String> pestValue) {
        modelSetupService.setUpCatalogPage(model, pageNumber, request, sortValue, searchValue, pestValue);
        return "catalog";
    }
}
