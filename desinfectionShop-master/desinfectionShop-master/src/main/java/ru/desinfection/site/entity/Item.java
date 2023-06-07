package ru.desinfection.site.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(
            name = "ITEM_SEQ",
            sequenceName = "ITEM_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ITEM_SEQ"
    )
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_pest",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "pest_id")}
    )
    @NotEmpty
    @JsonIgnore
    @ToString.Exclude
    private List<Pest> pests;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "amount_sold", nullable = false)
    private Integer amountSold;

    @Column(name = "country")
    private String country;

    @Column(name = "chemical_composition", nullable = false)
    private String chemicalComposition;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_path")
    private String picturePath;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "capacity")
    private String capacity;
}
