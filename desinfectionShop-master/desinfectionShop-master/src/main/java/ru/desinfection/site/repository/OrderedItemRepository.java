package ru.desinfection.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.desinfection.site.entity.orderedItem.OrderedItem;
import ru.desinfection.site.entity.orderedItem.OrderedItemKey;

import java.util.List;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, OrderedItemKey> {

    @Query("from OrderedItem i " +
            "join fetch i.pk.item " +
            "where i.pk.order.id = :orderId")
    List<OrderedItem> findAllItemsByOrderId(Long orderId);
}
