package ru.desinfection.site.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.desinfection.site.DTO.CurrOrderInfoDTO;
import ru.desinfection.site.DTO.OrderCreateDTO;
import ru.desinfection.site.DTO.OrderUpdateDTO;
import ru.desinfection.site.DTO.SessionOrderedItem;
import ru.desinfection.site.DTO.enums.Status;
import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Order;
import ru.desinfection.site.entity.orderedItem.OrderedItem;
import ru.desinfection.site.entity.orderedItem.OrderedItemKey;
import ru.desinfection.site.exception.MaxAmountOfItemsInOrderException;
import ru.desinfection.site.exception.NoSuchOrderException;
import ru.desinfection.site.repository.OrderRepository;
import ru.desinfection.site.repository.OrderedItemRepository;
import ru.desinfection.site.service.AccountService;
import ru.desinfection.site.service.ItemService;
import ru.desinfection.site.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final AccountService accountService;
    private final ItemService itemService;

    private final CacheManager cacheManager;

    @Value("${ordersOnPage}")
    private Integer ordersOnPage;

    @Override
    @Transactional
    public boolean createOrder(OrderCreateDTO orderCreateDTO, Account account) {
        Order order = Order.builder()
                .account(account)
//                .finalCost(calculateCost)
                .shippingAddress(orderCreateDTO.getShippingAddress())
                .comment(orderCreateDTO.getComment())
                .creationDate(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy"))))
                .build();
        order.setStatus(Status.CREATED);
        return true;
    }

    @Override
    @Transactional
    public boolean updateOrderById(Long id, OrderUpdateDTO orderUpdateDTO) {
        Order order = getOrderById(id);
        order.setStatus(orderUpdateDTO.getStatus());
        orderRepository.save(order);
        return true;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchOrderException(id.toString()));
    }

    @Override
    @Transactional
    public boolean deleteOrderById(Long id) {
        orderRepository.delete(getOrderById(id));
        return true;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllUserOrdersByAccountId(Long accountId) {
        return null;
    }

    @Transactional
    @CacheEvict(value = "currOrderInfo", condition = "#email != null", key = "#email")
    public void addItemToCurrOrder(String email, Long itemId, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        Item itemToAdd = itemService.findById(itemId);
        if (itemToAdd.getAmount() <= 0) {
            throw new MaxAmountOfItemsInOrderException(String.format("Нет товара c id %s на складе", itemId));
        }

        if (email == null) {
            SessionOrderedItem sessionOrderedItem = sessionCart.get(itemId);
            if (sessionOrderedItem != null) {
                if (itemToAdd.getAmount() > sessionOrderedItem.getAmount()) {
                    sessionOrderedItem.setAmount(sessionOrderedItem.getAmount() + 1);
                } else {
                    throw new MaxAmountOfItemsInOrderException(String.format("Максимальное кол-во допустимых предметов для покупки с id: %s", itemId));
                }
            } else {
                sessionCart.put(itemId, new SessionOrderedItem(itemId, 1));
            }

        } else {
            Account account = accountService.findByEmail(email);
            Optional<OrderedItem> orderedItem = orderedItemRepository.findById(new OrderedItemKey(account.getCurrOrder(), itemToAdd));

            if (orderedItem.isEmpty()) {
                orderedItemRepository.save(new OrderedItem(account.getCurrOrder(), itemToAdd, null, 1));
            } else {
                if (itemToAdd.getAmount() > orderedItem.get().getAmount()) {
                    orderedItem.get().setAmount(orderedItem.get().getAmount() + 1);
                } else {
                    throw new MaxAmountOfItemsInOrderException(String.format("Максимальное кол-во допустимых предметов для покупки с id: %s", itemId));
                }
            }
        }
    }
    @Transactional
    public Page<Order> findAllOrdersByPageNumberAndSort(int page, Account account) {
        PageRequest paging = PageRequest.of(page - 1, ordersOnPage, Sort.by("creationDate").descending());
        return orderRepository.findAllByAccountId(account.getId(), paging);
    }

    @Transactional
    public List<Order> findLast10Orders(Long id) {
        return orderRepository.findTop10ByAccountIdOrderByCreationDateDesc(id);
    }

    @Transactional
    @CacheEvict(value = "currOrderInfo", condition = "#email != null", key = "#email")
    public void deleteItemFromCurrOrder(String email, Long itemId, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        Item itemToDelete = itemService.findById(itemId);

        if (email != null) {
            Account account = accountService.findByEmail(email);
            Optional<OrderedItem> orderedItem = orderedItemRepository.findById(new OrderedItemKey(account.getCurrOrder(), itemToDelete));

            if (orderedItem.isPresent()) {
                if (orderedItem.get().getAmount() <= 1) {
                    orderedItemRepository.delete(orderedItem.get());
                } else {
                    orderedItem.get().setAmount(orderedItem.get().getAmount() - 1);
                }
            }
        } else {
            SessionOrderedItem sessionOrderedItem = sessionCart.get(itemId);
            if (sessionOrderedItem != null) {
                if (sessionOrderedItem.getAmount() > 1) {
                    sessionOrderedItem.setAmount(sessionOrderedItem.getAmount() - 1);
                } else {
                    sessionCart.remove(itemId);
                }
            }
        }
    }

    @Transactional
    @CacheEvict(value = "currOrderInfo", condition = "#email != null", key = "#email")
    public void deleteFullItemFromCurrOrder(String email, Long itemId, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        Item itemToDelete = itemService.findById(itemId);

        if (email != null) {
            Account account = accountService.findByEmail(email);
            Optional<OrderedItem> orderedItem = orderedItemRepository.findById(new OrderedItemKey(account.getCurrOrder(), itemToDelete));
            orderedItem.ifPresent(orderedItemRepository::delete);
        } else {
            sessionCart.remove(itemId);
        }
    }

    @Cacheable(value = "currOrderInfo", condition = "#email != null", key = "#email")
    public CurrOrderInfoDTO getInfoAboutCurrOrder(String email, LinkedHashMap<Long, SessionOrderedItem> sessionCart) {
        BigDecimal totalCost = BigDecimal.ZERO;
        Integer amountOfItems = 0;

        if (email != null) {
            Account account = accountService.findByEmail(email);
            List<OrderedItem> itemsInCart = orderedItemRepository.findAllItemsByOrderId(account.getCurrOrder().getId());

            totalCost = getTotalCost(itemsInCart);
            amountOfItems = getTotalAmountOfItems(itemsInCart);
        } else {
            List<Item> itemList = itemService.findAllByListOfId(sessionCart.keySet().stream().toList());
            List<OrderedItem> itemsInCart = itemList.stream()
                    .map(item -> {
                        return new OrderedItem(item, item.getCost(), sessionCart.get(item.getId()).getAmount());
                    })
                    .toList();

            totalCost = getTotalCost(itemsInCart);
            amountOfItems = getTotalAmountOfItems(itemsInCart);
        }
        return new CurrOrderInfoDTO(totalCost, amountOfItems);
    }

    private BigDecimal getTotalCost(List<OrderedItem> itemsInCart) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < itemsInCart.size(); i++) {
            sum = sum.add(itemsInCart.get(i).getTotalPrice());
        }
        return sum;
    }

    private Integer getTotalAmountOfItems(List<OrderedItem> itemsInCart) {
        return itemsInCart.parallelStream()
                .mapToInt(OrderedItem::getAmount)
                .sum();
    }
}
