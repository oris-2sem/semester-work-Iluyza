package ru.desinfection.site.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pest")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pest {

    @Id
    @SequenceGenerator(
            name = "PEST_SEQ",
            sequenceName = "PEST_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PEST_SEQ"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "picture_path", nullable = false)
    private String picturePath;

    @ManyToMany(mappedBy = "pests")
    List<Item> items = new ArrayList<>();

}
