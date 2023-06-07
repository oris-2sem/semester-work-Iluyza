package ru.desinfection.site.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateDTO {

    private String name;

    private BigDecimal cost;

    private Integer amount;

    private String country;

    private String chemicalComposition;

    private String description;

    private String capacity;

    private String instruction;

    private MultipartFile itemImage;

    private List<String> pests;

}
