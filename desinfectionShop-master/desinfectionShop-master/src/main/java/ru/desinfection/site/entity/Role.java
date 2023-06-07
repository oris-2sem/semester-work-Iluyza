package ru.desinfection.site.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @SequenceGenerator(
            name = "ROLE_SEQ",
            sequenceName = "ROLE_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ROLE_SEQ"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
