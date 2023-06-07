package ru.desinfection.site.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDTO {

    private Long id;

    private String name;

    private BigDecimal cost;

    private Integer amount;

    private String country;

    private String chemicalComposition;

    private String description;

    private MultipartFile itemImage;
}
