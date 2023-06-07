package ru.desinfection.site.entity.orderedItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.desinfection.site.entity.Item;
import ru.desinfection.site.entity.Order;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
public class OrderedItem {

    @EmbeddedId
    @JsonIgnore
    private OrderedItemKey pk;

    public OrderedItem(Order order, Item item, BigDecimal itemCost, Integer amount) {
        pk = new OrderedItemKey();
        pk.setOrder(order);
        pk.setItem(item);
        this.itemCost = itemCost;
        this.amount = amount;
    }

    public OrderedItem(Item item, BigDecimal itemCost, Integer amount) {
        pk = new OrderedItemKey();
        pk.setItem(item);
        this.itemCost = itemCost;
        this.amount = amount;
    }

    @Column(name = "item_cost", nullable = false)
    private BigDecimal itemCost;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Transient
    public Item getItem() {
        return this.pk.getItem();
    }

    @Transient
    public BigDecimal getTotalPrice() {
        return getItem().getCost().multiply(new BigDecimal(getAmount()));
    }

}
