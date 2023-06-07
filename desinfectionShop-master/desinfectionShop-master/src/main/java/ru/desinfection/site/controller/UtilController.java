package ru.desinfection.site.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.desinfection.site.DTO.PestCreateDTO;
import ru.desinfection.site.repository.OrderedItemRepository;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.service.PestService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilController {

    private final ItemService itemService;
    private final AccountService accountService;
    private final PestService pestService;

    private final OrderedItemRepository orderedItemRepository;



    @PostMapping("/pest")
    public boolean createPest(@RequestBody PestCreateDTO pestCreateDTO) {
        return pestService.createPest(pestCreateDTO);
    }

}
