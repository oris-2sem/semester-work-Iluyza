package ru.desinfection.site.service;

import org.springframework.data.domain.Page;
import ru.desinfection.site.DTO.OrderCreateDTO;
import ru.desinfection.site.DTO.OrderUpdateDTO;
import ru.desinfection.site.entity.Account;
import ru.desinfection.site.entity.Order;

import java.util.List;

public interface OrderService {
    boolean createOrder(OrderCreateDTO orderCreateDTO, Account account);

    boolean updateOrderById(Long id, OrderUpdateDTO orderUpdateDTO);

    Order getOrderById(Long id);

    boolean deleteOrderById(Long id);

    List<Order> getAllOrders();

    List<Order> getAllUserOrdersByAccountId(Long accountId);

    Page<Order> findAllOrdersByPageNumberAndSort(int page, Account account);

    List<Order> findLast10Orders(Long id);
}
