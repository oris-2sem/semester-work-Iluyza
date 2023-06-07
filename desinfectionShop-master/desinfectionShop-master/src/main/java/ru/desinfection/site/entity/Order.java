package ru.desinfection.site.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.desinfection.site.DTO.enums.Status;
import ru.desinfection.site.entity.orderedItem.OrderedItem;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @SequenceGenerator(
            name = "ORDER_SEQ",
            sequenceName = "ORDER_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ORDER_SEQ"
    )
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "final_cost")
    private BigDecimal finalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order", fetch = FetchType.EAGER)
    @Valid
    private List<OrderedItem> orderItems;

    @Transient
    public int getNumberOfItems() {
        return this.orderItems.size();
    }

    @Transient
    public BigDecimal getTotalCost() {
        BigDecimal sum = new BigDecimal(0);
        getOrderItems()
                .forEach(orderedItem -> {
                    sum.add(orderedItem.getTotalPrice());
                });
        return sum;
    }

    public Order(Long id) {
        this.id = id;
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy", new Locale("ru", "RU"));
        return creationDate.format(formatter);
    }
}