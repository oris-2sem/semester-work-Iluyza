package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.desinfection.site.DTO.AuthenticationRequest;
import ru.desinfection.site.DTO.ItemCreateDTO;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Order;
import ru.desinfection.site.entity.Pest;
import ru.desinfection.site.entity.orderedItem.OrderedItem;
import ru.desinfection.site.entity.orderedItem.OrderedItemKey;
import ru.desinfection.site.mapper.ItemMapper;
import ru.desinfection.site.repository.OrderedItemRepository;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelSetupService {

    private final ItemService itemService;
    private final AccountService accountService;
    private final OrderServiceImpl orderService;
    private final OrderedItemRepository orderedItemRepository;
    private final ItemMapper itemMapper;
    private final JwtUtil jwtUtil;

    @Resource(name = "sessionCart")
    LinkedHashMap<Long, SessionOrderedItem> sessionCart;

    public void setUpItemPage(Model model, Long itemId, HttpServletRequest request, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        String email = jwtUtil.getEmailFromRequest(request);
        Item item = itemService.findById(itemId);

        if (email != null) {
            Account account = accountService.findByEmail(email);
            Optional<OrderedItem> itemInCurrOrder = orderedItemRepository.findById(new OrderedItemKey(account.getCurrOrder(), item));
            model.addAttribute("account", account);

            if (itemInCurrOrder.isPresent()) {
                model.addAttribute("amountInCurrOrder", itemInCurrOrder.get().getAmount());
            }
        } else {
            SessionOrderedItem sessionOrderedItem = sessionCart.get(itemId);
            if (sessionOrderedItem != null) {
                model.addAttribute("amountInCurrOrder", sessionOrderedItem.getAmount());
            }
        }

        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
        model.addAttribute("item", item);
        model.addAttribute("pestsLine", getPestsLine(item.getPests()));
    }

    public void setUpUpdateItemPage(Model model, Long itemId) {
        Item item = itemService.findById(itemId);
        ItemCreateDTO itemCreateDTO = itemMapper.toItemCreateDTO(item);
        model.addAttribute("itemCreateDTO", itemCreateDTO);
    }

    private String getPestsLine(List<Pest> pestsList) {
        StringBuilder strBuilder = new StringBuilder("");

        for (int i = 0; i < pestsList.size() - 1; i++) {
            strBuilder.append(pestsList.get(i).getName())
                    .append(", ");
        }
        strBuilder.append(pestsList.get(pestsList.size() - 1).getName());

        return strBuilder.toString();
    }

    public void setUpCatalogPage(Model model, int pageNumber, HttpServletRequest request, Optional<String> sortValue, Optional<String> searchValue, Optional<String> pestValue) {
        String email = jwtUtil.getEmailFromRequest(request);

        if (email != null) {
            Account account = accountService.findByEmail(email);
            model.addAttribute("account", account);
        }

        Page<Item> items = itemService.findAllByPageNumberAndSortValues(pageNumber, sortValue, searchValue, pestValue);
        int totalPages = items.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pages", pageNumbers);
        }

        model.addAttribute("items", items.getContent());
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
    }

    public void setUpPersonalAreaPage(Model model, HttpServletRequest request) {
        String email = jwtUtil.getEmailFromRequest(request);
        Account account = accountService.findByEmail(email);
        List<Order> orders = orderService.findLast10Orders(account.getId());
        model.addAttribute("account", account);
        model.addAttribute("orders", orders);
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
    }

    public void setUpMainPage(Model model, HttpServletRequest request) {
        String email = jwtUtil.getEmailFromRequest(request);
        if (email != null) {
            Account account = accountService.findByEmail(email);
            model.addAttribute("account", account);
        }

        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
    }

    public void setUpPersonalOrdersPage(Model model, int page, HttpServletRequest request) {
        String email = jwtUtil.getEmailFromRequest(request);
        Account account = accountService.findByEmail(email);
        Page<Order> orders = orderService.findAllOrdersByPageNumberAndSort(page, account);
        int totalPages = orders.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pages", pageNumbers);
        }
        model.addAttribute("account", account);
        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
    }

    public void setUpCartPage(Model model, HttpServletRequest request) {
        String email = jwtUtil.getEmailFromRequest(request);
        List<OrderedItem> itemsInCart;

        if (email != null) {
            Account account = accountService.findByEmail(email);
            itemsInCart = orderedItemRepository.findAllItemsByOrderId(account.getCurrOrder().getId());

            model.addAttribute("account", account);
            model.addAttribute("totalCost", getTotalCost(itemsInCart));
        } else {
            List<Item> itemList = itemService.findAllByListOfId(sessionCart.keySet().stream().toList());
            itemsInCart = itemList.stream()
                            .map(item -> {
                                return new OrderedItem(item, item.getCost(), sessionCart.get(item.getId()).getAmount());
                            })
                    .toList();
        }

        model.addAttribute("orderedItems", itemsInCart);
        model.addAttribute("totalCost", getTotalCost(itemsInCart));
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
    }


    public void setUpCheckout(Model model, HttpServletRequest request, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        String email = jwtUtil.getEmailFromRequest(request);

        if (email != null) {
            Account account = accountService.findByEmail(email);
            List<OrderedItem> itemsInCart = orderedItemRepository.findAllItemsByOrderId(account.getCurrOrder().getId());

            model.addAttribute("account", account);
            model.addAttribute("orderedItems", itemsInCart);
            model.addAttribute("totalCost", getTotalCost(itemsInCart));
            model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(email, sessionCart));
        }
    }

    public void setUpRegistrationPage(Model model) {
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(null, sessionCart));
    }

    public void setUpLoginPage(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        model.addAttribute("currOrderInfo", orderService.getInfoAboutCurrOrder(null, sessionCart));
    }

    private BigDecimal getTotalCost(List<OrderedItem> itemsInCart) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < itemsInCart.size(); i++) {
            sum = sum.add(itemsInCart.get(i).getTotalPrice());
        }
        return sum;
    }
}
