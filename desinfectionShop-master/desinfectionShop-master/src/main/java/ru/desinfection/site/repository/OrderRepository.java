package ru.desinfection.site.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.desinfection.site.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByAccountId(Long id, Pageable pageable);

    @Query("FROM Order o join fetch o.orderItems where o.id = :orderId")
    Order findOrderByIdFetchItems(Long orderId);

    List<Order> findTop10ByAccountIdOrderByCreationDateDesc(Long id);

}
